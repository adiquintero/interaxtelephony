package com.interax.telephony.service.ami.pickdialing;

import java.io.File;
import java.util.Calendar;
//import java.util.Vector;

import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.ChannelState;
import org.asteriskjava.live.ManagerCommunicationException;
import org.asteriskjava.live.NoSuchChannelException;
import org.asteriskjava.manager.event.ConnectEvent;
import org.asteriskjava.manager.event.LinkEvent;
import org.asteriskjava.manager.event.ManagerEvent;

import com.interax.telephony.service.ami.InteraxTelephonyAmiServer;
import com.interax.telephony.service.ami.InteraxTelephonyAmiServerConfig;
import com.interax.telephony.service.ami.InteraxTelephonyAmiServerConfigLoader;
import com.interax.telephony.service.data.CdrCallDetailRecord;
import com.interax.telephony.service.data.ServiceFamily;
import com.interax.telephony.service.data.ServiceReservation;
import com.interax.telephony.service.pickdialing.data.PickDialingAccessType;
import com.interax.telephony.util.GeneralUtils;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;
import com.interax.telephony.util.PersistenceManager;
import com.interax.utils.FileUtils;
import com.interax.utils.StringUtils;
import java.util.List;

public class PickDialingAmi extends InteraxTelephonyAmiServer{
    
	public static final String BACKUP_FILE_PREFIX = "pick-dialing-";
	private Thread amiThread;
	private static ServiceFamily serviceFamily = ServiceFamily.PICK_DIALING;
	private PickDialingAmiLogger logger;
	private InteraxTelephonyAmiServerConfig interaxTelephonyAmiConfig;
	private PickDialingAmiConfig pickDialingAmiConfig;
    private PickDialingAccessType accessType;
	private String configPath = null;
    private String persistencePath ;
	private String onlineCallPersistenceFolder;

    public PickDialingAmi(){
    	super();
    	
    	this.amiThread = Thread.currentThread();
		this.amiThread.setName("PickDialingAmi");
		interaxTelephonyAmiConfig = InteraxTelephonyAmiServerConfigLoader.getConfig();
		this.configPath = interaxTelephonyAmiConfig.CONFIG_PATH + interaxTelephonyAmiConfig.AMIS_CONFIG_PATH + "/pickdialing/";

		this.asteriskHost = interaxTelephonyAmiConfig.ASTERISK_HOST;
		this.asteriskUser = interaxTelephonyAmiConfig.ASTERISK_USER;
		this.asteriskPassword = interaxTelephonyAmiConfig.ASTERISK_PASSWORD;
		this.persistencePath = interaxTelephonyAmiConfig.PERSISTENCE_PATH;

		
		//this.logger.info("****this.persistencePath :" + interaxTelephonyAmiConfig.PERSISTENCE_PATH );
		
	    this.logger = new PickDialingAmiLogger(this, this.configPath);
	    this.logger.info("Trying to conect to Asterisk.");
	    connectToAsterisk();
	    this.logger.info("Conected to Asterisk correctly.");
		this.genericEjbCaller = new InteraxTelephonyGenericEjbCaller(logger);
 
		List<Object> managedCalls = PersistenceManager.loadObjects(BACKUP_FILE_PREFIX);
		this.logger.info("Recovering PickDialing persistent calls.");
		for (Object object : managedCalls) {
			PickDialingManagedCall managedCall = (PickDialingManagedCall)object;
			AsteriskChannel incomingChannel = null;
			AsteriskChannel outgoingChannel = null;
			if(managedCall!=null){
				this.logger.debug("Persistent Call:", managedCall);
				pickDialingAmiConfig = (PickDialingAmiConfig)PickDialingAmiConfigLoader.getConfig(managedCall.getEnterpriseId(), this.configPath);
				try {
					incomingChannel = this.asteriskServer.getChannelById(managedCall.getIncomingChannelId());
					outgoingChannel = this.asteriskServer.getChannelById(managedCall.getOutgoingChannelId());
					if(incomingChannel != null && outgoingChannel != null && outgoingChannel.getState() == ChannelState.UP 
							&& incomingChannel.getVariable("CDR(uniqueId)").equals(managedCall.getIncomingCdrId())
							&& outgoingChannel.getVariable("CDR(uniqueId)").equals(managedCall.getOutgoingCdrId())){
						this.logger.info("Valid persistent call with ANI: " + managedCall.getAni() + " DNI: " + managedCall.getDni());
						this.logger.info("Creating a CallManager to monitor this call.");
					
						
						PickDialingCallManager callManager = new PickDialingCallManager(this, managedCall, incomingChannel, outgoingChannel);
						callManager.start();
						this.logger.info("CallManager created successfully.");
					}
					else{
						this.logger.info("This call finished before the recovery. Deleting peristent data of this call.");
						PersistenceManager.deleteObject(BACKUP_FILE_PREFIX + managedCall.getIncomingCdrId());
					}
				} catch (ManagerCommunicationException e) {
					this.logger.info("An error ocurred trying to get the incoming/outgoing channel.");
					PersistenceManager.deleteObject(BACKUP_FILE_PREFIX + managedCall.getIncomingCdrId());
				} catch (NoSuchChannelException e) {
					this.logger.info("An error ocurred trying to get the incoming/outgoing channel.");
					PersistenceManager.deleteObject(BACKUP_FILE_PREFIX + managedCall.getIncomingCdrId());
				}
			}
			else{
				this.logger.error("An error ocurred trying to get recover call. ManagedCall is Null");
			}
		}
        
    }

    public void onManagerEvent(ManagerEvent event){
    	Thread.currentThread().setName("PickDialingAmi");
     	if(event instanceof ConnectEvent){
     		this.logger.info("Conected to Asterisk.");
     	}
    	
    	if(event instanceof LinkEvent){
    		LinkEvent linkEvent = (LinkEvent) event;
    		
    		try{
    			String incomingUniqueId  = linkEvent.getUniqueId1();
	    		AsteriskChannel incomingChannel = this.asteriskServer.getChannelById(incomingUniqueId);	
	    		String outgoingUniqueId = linkEvent.getUniqueId2();
    			AsteriskChannel outgoingChannel = this.asteriskServer.getChannelById(outgoingUniqueId);
	    		String enterprise = incomingChannel.getVariable(serviceFamily.name() + "_ENTERPRISE_ID");
	    		long enterpriseId = 0;
	    		if (enterprise != null && !enterprise.equals("(null)")){
	    			enterpriseId = Long.parseLong(enterprise);
	    			
	    			this.logger.info("Enterprise number " + enterpriseId);
	    			pickDialingAmiConfig = (PickDialingAmiConfig)PickDialingAmiConfigLoader.getConfig(enterpriseId,this.configPath);
	    			
		    		if(this.pickDialingAmiConfig == null){
	    				this.logger.info("No config file associated to this enterprise.");
	    				return;
	    			}
		    		this.logger.info("PickDialing Ami Config loaded");
	    			ServiceFamily currentServiceFamily = ServiceFamily.valueOf(incomingChannel.getVariable(serviceFamily.name() + "_SERVICE_FAMILY"));
		    		if(currentServiceFamily!=null && currentServiceFamily == ServiceFamily.PICK_DIALING){
		    			this.logger.info("PickDialing Call detected.");
		    			accessType = PickDialingAccessType.valueOf(incomingChannel.getVariable(serviceFamily.name() + "_ACCESS_TYPE"));
		    			
		    			Calendar answerDate = Calendar.getInstance();
		    			this.logger.info("answerDate: " + answerDate.getTimeInMillis());
		    			
		    			//String childrenCdrId = incomingChannel.getVariable(serviceFamily.name() + "_CHILDREN_CDR_ID");
		    					    
		    			this.onlineCallPersistenceFolder = persistencePath + "agis/pickdialing/PickDialing" + StringUtils.toCamelCase(accessType.name().toLowerCase()) + "Agi/onlineCall/";
		    			this.logger.info("onlineCallPersistenceFolder: " + 	this.onlineCallPersistenceFolder);
		    			FileUtils fileManager = new FileUtils();
		    			
						File callFile = new File(this.onlineCallPersistenceFolder + "" + incomingUniqueId);
						this.logger.info("****" + this.onlineCallPersistenceFolder + "" + incomingUniqueId);
						this.logger.info("Trying to update onlinecall persistence file: " + callFile.getName());
						try{
			    		    String stringObject = new String(fileManager.download(callFile.getParent(),"/" + callFile.getName()));
			    		    CdrCallDetailRecord cdr = (CdrCallDetailRecord) GeneralUtils.fromText(stringObject);
			    		    cdr.setAnswerDate(answerDate.getTimeInMillis());
			    		    cdr.setDisposition("ANSWERED");
			    		    cdr.setDstchannel(outgoingChannel.getName());
			    			fileManager.upload(callFile, GeneralUtils.toText(cdr).getBytes(),false);
							this.logger.info("Onlinecall persistence file updated successfully");
						}
						catch(Exception ex){
							this.logger.error("An error ocurred while trying to update onlinecall persistence file");
						    this.logger.info(ex.getMessage());
						}
		    			
		    			
		    			
		    			
		    			
		    			
		    			
		    			switch (accessType) {

							case OUTGOING:
							
								this.logger.info("PickDialing outgoing Call detected for incoming channel " + incomingUniqueId);
								String unlimitedCall = incomingChannel.getVariable(serviceFamily.name() + "_UNLIMITED_CALL");
				    			String unmonitoredCall = incomingChannel.getVariable(serviceFamily.name() + "_UNMONITORED_CALL");
								if((unlimitedCall!=null && unlimitedCall.equals("TRUE")) || (unmonitoredCall!=null && unmonitoredCall.equals("TRUE"))){
									this.logger.info("Not monitoring the call " + incomingUniqueId + ".");
									this.logger.info("Unlimited Call: " + unlimitedCall + ".");
									this.logger.info("Monitored Call: " + unmonitoredCall + ".");
								}
								else{
									
									Calendar startDate = Calendar.getInstance();
									this.logger.info("Monitoring the call " + incomingUniqueId);
					    			
					    			PickDialingManagedCall managedCall = new PickDialingManagedCall();
					    			managedCall.setIncomingChannelId(incomingUniqueId);
					    			managedCall.setIncomingChannelName(incomingChannel.getName());
					    			managedCall.setIncomingCdrId(incomingChannel.getVariable("CDR(uniqueId)"));
					    			managedCall.setOutgoingChannelId(outgoingUniqueId);
					    			managedCall.setOutgoingChannelName(outgoingChannel.getName());
					    			managedCall.setOutgoingCdrId(outgoingChannel.getVariable("CDR(uniqueId)"));
					    			managedCall.setServiceFamily(incomingChannel.getVariable(serviceFamily.name() + "_SERVICE_FAMILY"));
					    			managedCall.setAni(incomingChannel.getVariable(serviceFamily.name() + "_ANI"));
					    			managedCall.setDni(incomingChannel.getVariable(serviceFamily.name() + "_DNI"));
					    			managedCall.setChildrenCdrId(incomingChannel.getVariable(serviceFamily.name() + "_CHILDREN_CDR_ID"));
					    			managedCall.setReservationId(Long.parseLong(incomingChannel.getVariable(serviceFamily.name() + "_RESERVATION")));
					    			managedCall.setEnterpriseId(enterpriseId);
					    			managedCall.setStartDate(startDate);
					    			managedCall.setNextWakeupDate((Calendar)startDate.clone());
					    			managedCall.setToHangupCall(false);
					    			managedCall.setAccessType(accessType);
					    			
					    			
					    			
					    			
//					    			RATER_TEST_MODE:1
					    			String raterMode = incomingChannel.getVariable("RATER_MODE");
					    			if(raterMode!=null && raterMode.equalsIgnoreCase("test")){
					    				managedCall.setRaterTestMode(true);
					    				this.logger.info("RATER MODE TEST ACTIVATED.");
					    				String raterSeconds = incomingChannel.getVariable("RATER_SECONDS");
					    				this.logger.info("RATER TEST AVAILABLE SECONDS:" + raterSeconds);
					    				if(raterSeconds!= null && !raterSeconds.equals("(null)")){
					    					try{
					    						int raterTestAvailablesSeconds = Integer.parseInt(raterSeconds);
					    						managedCall.setRaterTestAvailablesSeconds(raterTestAvailablesSeconds - ServiceReservation.BASE_TIME_IN_SECONDS);
					    					}
					    					catch(Exception e){
					    						this.logger.error("Error parsing raterTestAvailablesSeconds.");
					    						this.logger.debug("Detail: " + e.getMessage());
					    					}
					    				}
					    			}
					    			
									this.logger.debug("Managed call detail: ", managedCall);									

					    			this.logger.info("Creating a CallManager to monitor this call");
					    			PickDialingCallManager callManager = new PickDialingCallManager(this, managedCall, incomingChannel, outgoingChannel);
					    			callManager.start();
					    			this.logger.info("CallManager created successfully");
					    			
								}
							break;
							
							case DENIED:
								this.logger.info("AccessType " + accessType.name() + " not monitored.");
							break;
								
							default:
								this.logger.info("AccessType " + accessType.name() + " not recognized.");
							break;
						}
		    		}
	    		}	    		
    		}
    		catch(Exception e){
    			this.logger.error("An error ocurred processing event. Detail: " + e.getMessage());
    		}

    	}
    	
    }
    

    public static void main(String[] args) throws Exception{
    	PickDialingAmi pickDialingAmi;
    	pickDialingAmi = new PickDialingAmi();
    	pickDialingAmi.run();
    }

	public PickDialingAmiLogger getLogger() {
		return logger;
	}

	public InteraxTelephonyGenericEjbCaller getGenericEjbCaller() {
		return genericEjbCaller;
	}

	public PickDialingAmiConfig getPickDialingAmiConfig() {
		return pickDialingAmiConfig;
	}
	

	
	public String getConfigPath() {
		return configPath;
	}

}