package com.interax.telephony.service.ami.pickdialing;

import java.util.Calendar;

import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.ChannelState;
import org.asteriskjava.live.ManagerCommunicationException;
import org.asteriskjava.live.NoSuchChannelException;
import org.asteriskjava.manager.action.StreamAction;

import com.interax.telephony.service.ami.InteraxTelephonyCallManager;
import com.interax.telephony.service.data.ServiceReservation;
import com.interax.telephony.service.exception.InteraxTelephonyException;
import com.interax.telephony.service.exception.pickdialing.PickDialingNotEnoughBalanceException;
import com.interax.telephony.util.PersistenceManager;
import com.interax.telephony.util.StackTrace;


public class PickDialingCallManager extends InteraxTelephonyCallManager {
	
	private PickDialingAmi pickDialingAmi;
	private PickDialingCallManagerLogger managerLogger;
	private int currentSleepTime;
	private Calendar nextWakeUpDate;
	private String backupFileName;
	protected PickDialingManagedCall managedCall;
	private String childrenCdrId;
		
	public PickDialingCallManager(PickDialingAmi pickDialingAmi, PickDialingManagedCall managedCall, AsteriskChannel incomingChannel, AsteriskChannel outgoingChannel){
		super(incomingChannel, outgoingChannel, pickDialingAmi.getPickDialingAmiConfig().SLEEP_TIME, pickDialingAmi.getPickDialingAmiConfig().SAFETY_SECONDS_TO_HANGUP, pickDialingAmi.getGenericEjbCaller());
		this.managedCall = managedCall;
		this.pickDialingAmi = pickDialingAmi;
		this.managerLogger = new PickDialingCallManagerLogger(this,this.pickDialingAmi.getConfigPath());
	}
	
	
    public void run(){
    	this.childrenCdrId = this.managedCall.getChildrenCdrId();
    	this.managerLogger.info("Monitoring Call");
    	this.managerLogger.info("/******/Call data/******/");
    	this.managerLogger.info("ANI:" + this.managedCall.getAni());
    	this.managerLogger.info("DNI:" + this.managedCall.getDni());
    	this.managerLogger.info("Service Family:" + this.managedCall.getServiceFamily());
    	this.managerLogger.info("AccessType:" + this.managedCall.getAccessType());
    	this.managerLogger.info("/******/Call data/******/");
    	
      	this.backupFileName =PickDialingAmi.BACKUP_FILE_PREFIX + this.managedCall.getIncomingCdrId(); 
		this.nextWakeUpDate = this.managedCall.getNextWakeupDate();
		this.currentSleepTime = this.sleepTime/2;
		this.toHangup = this.managedCall.isToHangupCall();	
		
		if(this.nextWakeUpDate.compareTo(this.managedCall.getStartDate())==0){
			this.managerLogger.info("New Call. Sleeping:" + this.currentSleepTime);
			this.nextWakeUpDate.add(Calendar.SECOND, this.currentSleepTime);
		}
		else
		{
			
			this.managerLogger.info("Recovering from persistent data.");
			if(this.toHangup){
				forceHangup();
			}
			int minutes = 0;
			Calendar now = Calendar.getInstance();
			while(this.nextWakeUpDate.before(now)){
				minutes++;
				this.nextWakeUpDate.add(Calendar.SECOND, this.sleepTime);
			}
			
			if(minutes != 0){
				minutes--;
				this.managerLogger.info("Make a new reservation that should be made in the past.");
				getNewBlock(false);
			}
			
			this.managerLogger.info("To charge: " + minutes + " minutes.");
			try {
				if(getBlock(this.managedCall.getReservationId(), minutes)){
					this.managerLogger.info(minutes + " minutes charged successfully.");
				}
				else{
					this.managerLogger.info(minutes + " couldn't be charged.");
				}
			//TODO: Decidir si se tranca la llamada, o que medida se toma.
			} catch (InteraxTelephonyException e) {
				this.managerLogger.warn("There was a problem reservating a minute for this call.");
				this.managerLogger.warn("Detail: " + e.getMessage());
			}
		}
    	
    	    	
    	while(this.alive){
    	
     		this.managedCall.setNextWakeupDate(this.nextWakeUpDate);
    		this.managedCall.setToHangupCall(this.toHangup);
    		PersistenceManager.saveObject(this.managedCall, this.backupFileName);
	
    		Calendar now = Calendar.getInstance();
    		long realSleepTime = this.nextWakeUpDate.getTimeInMillis() - now.getTimeInMillis();
    		    		
    		try{

     			this.managerLogger.debug("Is alive!");
     			this.managerLogger.debug("To sleep: " + realSleepTime);
    			
     			if(realSleepTime > 0){
    				this.managerLogger.debug("Sleeping " + realSleepTime / 1000 + " seconds.");
					Thread.sleep(realSleepTime);
    			}
    			else{
    				this.managerLogger.error("FATAL ERROR. THIS SHOULD NEVER HAPPEN!!! SLEEPTIME NEGATIVE.");
    				this.managerLogger.debug("Error sleepTime is negative. Sleep 5 seg");
    				Thread.sleep(5000);
    			}
				
				ChannelState channelState = outgoingChannel.getState();
    			this.managerLogger.debug("Channels: (IN/OUT) " + this.incomingChannel.getName() + " -- " + this.outgoingChannel.getName());
    			this.managerLogger.debug("OutgoingChannel state: " + channelState.name());
    			
				switch (channelState) {
					case UP:

						if(this.toHangup){
		    				forceHangup();
		    			}else{
		    				this.managerLogger.info("Make a new reservation.");
		    				getNewBlock();
		    				
		    			}
						
					break;
						
					case HUNGUP:
						managerLogger.info("The channel hungup.");
						this.alive = false;
					break;
					
					default:
						this.managerLogger.info("The channel is not up.");
						this.alive = false;
					break;
				}
    			
    		}
    		catch(InterruptedException ie){
    			
    		}
    	}
    }

    private void getNewBlock(){
    	getNewBlock(true);
    }
    
    private void getNewBlock(boolean calculateNextWakeUpDate){
		this.managerLogger.info("Channel still up, trying to reserve 1 more minute.");
		try {
    		int secondsLeft = getBlock(this.managedCall.getReservationId());
			if(calculateNextWakeUpDate){
				this.managerLogger.info("Calculating nextWakeUpDate.");
				
				if(secondsLeft < ServiceReservation.BASE_TIME_IN_SECONDS){
					// No more money for this call
					this.managerLogger.warn("There is no more money available for this call.");
					this.managerLogger.info("There is no more minutes available, only " + secondsLeft + " seconds, sending a warning beep.");
					this.toHangup = true;
					try {
						StreamAction streamAction = new StreamAction();
						streamAction.setChannel(incomingChannel.getName());
						streamAction.setFile("beep");
						this.pickDialingAmi.managerConnection.sendAction(streamAction,0);
					} catch (Exception e1) {
						this.managerLogger.error("Problem Streaming file.");
					}
					secondsLeft = this.sleepTime/2 + secondsLeft - this.safetySecondsToHangup;
				}
				else{
					this.managerLogger.info("There is one more minute available.");
					secondsLeft = ServiceReservation.BASE_TIME_IN_SECONDS;// Se pone 60 seg por seguridad.
				}
				
				this.currentSleepTime = secondsLeft;
				this.nextWakeUpDate.add(Calendar.SECOND, this.currentSleepTime);
			}
			
    	} catch (PickDialingNotEnoughBalanceException e) {
			// No more money for this call
			this.toHangup = true;
			this.managerLogger.warn("There is no more money available for this call.");
			this.managerLogger.warn("Detail: " + e.getMessage());
			this.managerLogger.warn("This is the last minute, send a warning.");
			StreamAction streamAction = new StreamAction();
			streamAction.setChannel(incomingChannel.getName());
			streamAction.setFile("beep");
			try {
				this.pickDialingAmi.managerConnection.sendAction(streamAction,0);
			} catch (Exception e1) {
				this.managerLogger.error("Problem Streaming file.");
			}
			
			if(calculateNextWakeUpDate){
				this.managerLogger.info("Calculating nextWakeUpDate.");
				this.currentSleepTime = this.sleepTime/2 - this.safetySecondsToHangup;
				this.nextWakeUpDate.add(Calendar.SECOND, this.currentSleepTime);
			}	
		} catch (InteraxTelephonyException e) {
			// Other error
			this.managerLogger.warn("There was a problem reservating a minute for this call.");
			this.managerLogger.warn("Detail: " + e.getMessage());
			this.managerLogger.warn("Forcing Hangup.");
			this.toHangup = true;
		}
    }

    private void forceHangup(){
    	this.managerLogger.info("Going to force Hangup.");
    	this.managerLogger.debug("Channels: (IN/OUT) " + this.incomingChannel.getName() + " -- " + this.outgoingChannel.getName());
		try{
			this.outgoingChannel.hangup();
			this.managerLogger.info("Hangup successfull.");
			this.alive = false;
		}
		catch(NoSuchChannelException nsce){
			this.managerLogger.error("Problem with the Hangup. No such channel");
			this.managerLogger.debug("Deatil: " + nsce.getMessage());
			this.managerLogger.debug("Trace: " + StackTrace.ToString(nsce));
			this.alive = false;
		}
		catch(ManagerCommunicationException mce){
			this.managerLogger.error("Problem with the Hangup. Error in comunication.");
			this.managerLogger.debug("Deatil: " + mce.getMessage());
			this.managerLogger.debug("Trace: " + StackTrace.ToString(mce));
			this.alive = false;
		}
    }
    
	private Integer getBlock(long reservationId) throws InteraxTelephonyException {
		if (this.managedCall.isRaterTestMode()){
			this.managerLogger.info("In test mode.");
			int availableSeconds = this.managedCall.getRaterTestAvailablesSeconds();
			this.managerLogger.debug(availableSeconds + " available test seconds.");
			if(availableSeconds < ServiceReservation.BASE_TIME_IN_SECONDS){
				this.managedCall.setRaterTestAvailablesSeconds(0);
				return availableSeconds;
			}
			else
			{
				this.managedCall.setRaterTestAvailablesSeconds(availableSeconds - ServiceReservation.BASE_TIME_IN_SECONDS);
				return ServiceReservation.BASE_TIME_IN_SECONDS;
			}
		}
		return getBlock(reservationId, this.pickDialingAmi.getPickDialingAmiConfig().RATER_EJB_SERVER, this.pickDialingAmi.getPickDialingAmiConfig().RATER_EJB_PORT);
	}
	
	private boolean getBlock(long reservationId, long blocks) throws InteraxTelephonyException {
		if (this.managedCall.isRaterTestMode()){
			this.managerLogger.info("In test mode.");
			this.managerLogger.debug("Consuming minutes in system.");
			return true;
		}
			
		return getBlock(reservationId, blocks, this.pickDialingAmi.getPickDialingAmiConfig().RATER_EJB_SERVER, this.pickDialingAmi.getPickDialingAmiConfig().RATER_EJB_PORT);
	}
	public String getChildrenCdrId() {
		return childrenCdrId;
	}
	
}