package com.interax.telephony.service.ami.callingcard;

import java.util.Calendar;

import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.ChannelState;
import org.asteriskjava.live.ManagerCommunicationException;
import org.asteriskjava.live.NoSuchChannelException;
import org.asteriskjava.manager.action.StreamAction;

import com.interax.telephony.service.ami.InteraxTelephonyCallManager;
import com.interax.telephony.service.data.ServiceReservation;
import com.interax.telephony.service.exception.InteraxTelephonyException;
import com.interax.telephony.service.exception.callingcard.CallingCardNotEnoughBalanceException;
import com.interax.telephony.util.PersistenceManager;
import com.interax.telephony.util.StackTrace;


public class CallingCardCallManager extends InteraxTelephonyCallManager {
	
	private CallingCardAmi callingCardAmi;
	private CallingCardCallManagerLogger logger;
	private int currentSleepTime;
	private Calendar nextWakeUpDate;
	private String backupFileName;
	protected CallingCardManagedCall managedCall;
	private String childrenCdrId;

	
	public CallingCardCallManager(CallingCardAmi callingCardAmi, CallingCardManagedCall managedCall, AsteriskChannel incomingChannel, AsteriskChannel outgoingChannel){
		super(incomingChannel, outgoingChannel, callingCardAmi.getCallingCardAmiConfig().SLEEP_TIME, callingCardAmi.getCallingCardAmiConfig().SAFETY_SECONDS_TO_HANGUP, callingCardAmi.getGenericEjbCaller());
		this.managedCall = managedCall;
		this.callingCardAmi = callingCardAmi;
		this.logger = new CallingCardCallManagerLogger(this, callingCardAmi.getConfigPath());
	}
	
	
    public void run(){
    	this.childrenCdrId = this.managedCall.getChildrenCdrId();
    	this.logger.info("Monitoring Call");
    	this.logger.info("/******/Call data/******/");
    	this.logger.info("ANI:" + this.managedCall.getAni());
    	this.logger.info("DNI:" + this.managedCall.getDni());
    	this.logger.info("CDR ID:" + this.childrenCdrId);
    	this.logger.info("Service Family:" + this.managedCall.getServiceFamily());
    	this.logger.info("AccessType:" + this.managedCall.getAccessType());
    	this.logger.info("/******/Call data/******/");
    	
    	this.backupFileName = CallingCardAmi.BACKUP_FILE_PREFIX + this.managedCall.getIncomingCdrId(); 
		this.nextWakeUpDate = this.managedCall.getNextWakeupDate();
		this.currentSleepTime = this.sleepTime/2;
		this.toHangup = this.managedCall.isToHangupCall();	
		
		if(this.nextWakeUpDate.compareTo(this.managedCall.getStartDate())==0){
			this.logger.info("New Call. Sleeping:" + this.currentSleepTime);
			this.nextWakeUpDate.add(Calendar.SECOND, this.currentSleepTime);
		}
		else
		{
			
			this.logger.info("Recovering from persistent data.");
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
				this.logger.info("Make a new reservation that should be made in the past.");
				getNewBlock(false);
			}
			
			this.logger.info("To charge: " + minutes + " minutes.");
			try {
				if(getBlock(this.managedCall.getReservationId(), minutes)){
					this.logger.info(minutes + " minutes charged successfully.");
				}
				else{
					this.logger.info(minutes + " couldn't be charged.");
				}
			//TODO: Decidir si se tranca la llamada, o que medida se toma.
			} catch (InteraxTelephonyException e) {
				this.logger.warn("There was a problem reservating a minute for this call.");
				this.logger.warn("Detail: " + e.getMessage());
			}
		}
	
			
    	while(this.alive){
    		
    		this.managedCall.setNextWakeupDate(this.nextWakeUpDate);
    		this.managedCall.setToHangupCall(this.toHangup);
    		PersistenceManager.saveObject(this.managedCall, this.backupFileName);
    		
    		Calendar now = Calendar.getInstance();
    		long realSleepTime = this.nextWakeUpDate.getTimeInMillis() - now.getTimeInMillis();
    		    		
    		try{
    			this.logger.debug("Is alive!");
    			this.logger.debug("To sleep: " + realSleepTime);
    			
    			if(realSleepTime > 0){
    				this.logger.debug("Sleeping " + realSleepTime / 1000 + " seconds.");
					Thread.sleep(realSleepTime);
    			}
    			else{
    				this.logger.error("FATAL ERROR. THIS SHOULD NEVER HAPPEN!!! SLEEPTIME NEGATIVE.");
    				this.logger.debug("Error sleepTime is negative. Sleep 5 seg");
    				Thread.sleep(5000);
    			}
				
    			ChannelState channelState = outgoingChannel.getState();    			
    			this.logger.debug("Channels: (IN/OUT) " + this.incomingChannel.getName() + " -- " + this.outgoingChannel.getName());
    			this.logger.debug("OutgoingChannel state: " + channelState.name());

				switch (channelState) {
					case UP:

		    			if(this.toHangup){
		    				forceHangup();
		    			}else{
		    				this.logger.info("Make a new reservation.");
		    				getNewBlock();
		    			}
						
					break;
						
					case HUNGUP:
						this.logger.info("The channel hungup.");
						this.alive = false;
					break;
										
					default:
						this.logger.info("The channel is not up.");
						this.alive = false;
					break;
				}
    			
    		}
    		catch(InterruptedException ie){
    			
    		}
    	}
    	PersistenceManager.deleteObject(this.backupFileName);
    }

    private void getNewBlock(){
    	getNewBlock(true);
    }
    
    private void getNewBlock(boolean calculateNextWakeUpDate){
		this.logger.info("Channel still up, trying to reserve 1 more minute.");
    	try {
    		int secondsLeft = getBlock(this.managedCall.getReservationId());
			if(calculateNextWakeUpDate){
				this.logger.info("Calculating nextWakeUpDate.");
				
				if(secondsLeft < ServiceReservation.BASE_TIME_IN_SECONDS){
					// No more money for this call
					this.logger.warn("There is no more money available for this call.");
					this.logger.info("There is no more minutes available, only " + secondsLeft + " seconds, sending a warning beep.");
					this.toHangup = true;
					try {
						StreamAction streamAction = new StreamAction();
						streamAction.setChannel(incomingChannel.getName());
						streamAction.setFile("beep");
						this.callingCardAmi.managerConnection.sendAction(streamAction,0);
					} catch (Exception e1) {
						this.logger.error("Problem Streaming file.");
					}
					secondsLeft = this.sleepTime/2 + secondsLeft - this.safetySecondsToHangup;
				}
				else{
					this.logger.info("There is one more minute available.");
					secondsLeft = ServiceReservation.BASE_TIME_IN_SECONDS;// Se pone 60 seg por seguridad.
				}
				
				this.currentSleepTime = secondsLeft;
				this.nextWakeUpDate.add(Calendar.SECOND, this.currentSleepTime);
			}
			
    	} catch (CallingCardNotEnoughBalanceException e) {
    		//TODO: This should die....
			// No more money for this call
			this.toHangup = true;
			this.logger.warn("There is no more money available for this call.");
			this.logger.warn("Detail: " + e.getMessage());
			this.logger.warn("This is the last minute, send a warning.");
			StreamAction streamAction = new StreamAction();
			streamAction.setChannel(incomingChannel.getName());
			streamAction.setFile("beep");
			try {
				this.callingCardAmi.managerConnection.sendAction(streamAction,0);
			} catch (Exception e1) {
				this.logger.error("Problem Streaming file.");
			}
			
			if(calculateNextWakeUpDate){
				this.logger.info("Calculating nextWakeUpDate.");
				this.currentSleepTime = this.sleepTime/2 - this.safetySecondsToHangup;
				this.nextWakeUpDate.add(Calendar.SECOND, this.currentSleepTime);
			}
		} catch (InteraxTelephonyException e) {
			// Other error
			this.logger.warn("There was a problem reservating a minute for this call.");
			this.logger.warn("Detail: " + e.getMessage());
			this.logger.warn("Forcing Hangup.");
			this.toHangup = true;
		}
    }
    
    private void forceHangup(){
    	this.logger.info("Going to force Hangup.");
    	this.logger.debug("Channels: (IN/OUT) " + this.incomingChannel.getName() + " -- " + this.outgoingChannel.getName());
		try{
			this.outgoingChannel.hangup();
			this.logger.info("Hangup successfull.");
			this.alive = false;
		}
		catch(NoSuchChannelException nsce){
			this.logger.error("Problem with the Hangup. No such channel");
			this.logger.debug("Deatil: " + nsce.getMessage());
			this.logger.debug("Trace: " + StackTrace.ToString(nsce));
			this.alive = false;
		}
		catch(ManagerCommunicationException mce){
			this.logger.error("Problem with the Hangup. Error in comunication.");
			this.logger.debug("Deatil: " + mce.getMessage());
			this.logger.debug("Trace: " + StackTrace.ToString(mce));
			this.alive = false;
		}
    }
    
	private Integer getBlock(long reservationId) throws InteraxTelephonyException {
		if (this.managedCall.isRaterTestMode()){
			this.logger.info("In test mode.");
			int availableSeconds = this.managedCall.getRaterTestAvailablesSeconds();
			this.logger.debug(availableSeconds + " available test seconds.");
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
		return getBlock(reservationId, this.callingCardAmi.getCallingCardAmiConfig().RATER_EJB_SERVER, this.callingCardAmi.getCallingCardAmiConfig().RATER_EJB_PORT);
	}
	
	private boolean getBlock(long reservationId, long blocks) throws InteraxTelephonyException {
		if (this.managedCall.isRaterTestMode()){
			this.logger.info("In test mode.");
			this.logger.debug("Consuming minutes in system.");
			return true;
		}
			
		return getBlock(reservationId, blocks, this.callingCardAmi.getCallingCardAmiConfig().RATER_EJB_SERVER, this.callingCardAmi.getCallingCardAmiConfig().RATER_EJB_PORT);
	}


	public String getChildrenCdrId() {
		return childrenCdrId;
	}

}