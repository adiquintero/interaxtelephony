package com.interax.telephony.service.ami.callingcard;
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
import com.interax.telephony.service.callingcard.data.CallingCardAccessType;
import com.interax.telephony.service.data.CdrCallDetailRecord;
import com.interax.telephony.service.data.ServiceFamily;
import com.interax.telephony.service.data.ServiceReservation;
import com.interax.telephony.util.GeneralUtils;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;
import com.interax.telephony.util.PersistenceManager;
import com.interax.utils.FileUtils;
import java.util.List;

public class CallingCardAmi extends InteraxTelephonyAmiServer{
	


	public static final String BACKUP_FILE_PREFIX = "calling-card-";
	private Thread amiThread;
	private static ServiceFamily serviceFamily = ServiceFamily.CALLING_CARD;
	private CallingCardAmiLogger logger;
	private InteraxTelephonyAmiServerConfig interaxTelephonyAmiConfig;
	private CallingCardAmiConfig callingCardAmiConfig;
	private CallingCardAccessType accessType;
    private String configPath = null;
    private String persistencePath ;
	private String onlineCallPersistenceFolder;


    public CallingCardAmi(){
    	super();
    	
		this.amiThread = Thread.currentThread();
		this.amiThread.setName("CallingCardAmi");
		interaxTelephonyAmiConfig = InteraxTelephonyAmiServerConfigLoader.getConfig();
		this.configPath = interaxTelephonyAmiConfig.CONFIG_PATH + interaxTelephonyAmiConfig.AMIS_CONFIG_PATH + "/callingcard/";
		this.asteriskHost = interaxTelephonyAmiConfig.ASTERISK_HOST;
		this.asteriskUser = interaxTelephonyAmiConfig.ASTERISK_USER;
		this.asteriskPassword = interaxTelephonyAmiConfig.ASTERISK_PASSWORD;
		this.persistencePath = interaxTelephonyAmiConfig.PERSISTENCE_PATH;
		this.onlineCallPersistenceFolder = persistencePath + "agis/callingcard/CallingCardAgi/onlineCall/";
		
		
		this.logger = new CallingCardAmiLogger(this, this.configPath);
	    this.logger.info("Trying to conect to Asterisk.");
		connectToAsterisk();
		this.logger.info("Conected to Asterisk correctly.");
		this.genericEjbCaller = new InteraxTelephonyGenericEjbCaller(logger);
		 
		List<Object> managedCalls = PersistenceManager.loadObjects(BACKUP_FILE_PREFIX);
		this.logger.info("Recovering CallingCard AMI persistent managed calls.");
		for (Object object : managedCalls) {
			CallingCardManagedCall managedCall = (CallingCardManagedCall)object;
			AsteriskChannel incomingChannel = null;
			AsteriskChannel outgoingChannel = null;
			if(managedCall!=null){
				this.logger.debug("Persistent Managed Call:", managedCall);
				this.callingCardAmiConfig = (CallingCardAmiConfig)CallingCardAmiConfigLoader.getConfig(managedCall.getEnterpriseId(), this.configPath);
				try {
					incomingChannel = this.asteriskServer.getChannelById(managedCall.getIncomingChannelId());
					outgoingChannel = this.asteriskServer.getChannelById(managedCall.getOutgoingChannelId());
					if(incomingChannel != null && outgoingChannel != null && outgoingChannel.getState() == ChannelState.UP 
							&& incomingChannel.getVariable("CDR(uniqueId)").equals(managedCall.getIncomingCdrId())
							&& outgoingChannel.getVariable("CDR(uniqueId)").equals(managedCall.getOutgoingCdrId())){
						this.logger.info("Valid persistent call with ANI: " + managedCall.getAni() + " DNI: " + managedCall.getDni());
						this.logger.info("Creating a CallManager to monitor this call.");
						
						CallingCardCallManager callManager = new CallingCardCallManager(this, managedCall, incomingChannel, outgoingChannel);
						callManager.start();
						this.logger.info("CallManager created successfully.");
					}
					else{
						this.logger.info("This call finished before the recovery. Deleting persistent data of this managed call.");
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
				this.logger.error("An error ocurred trying to get recover managed call. ManagedCall is Null");
			}
		}

    }

    public void onManagerEvent(ManagerEvent event){
    	
    	Thread.currentThread().setName("CallingCardAmi");

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

	    			this.callingCardAmiConfig = (CallingCardAmiConfig)CallingCardAmiConfigLoader.getConfig(enterpriseId, this.configPath);
		    		if(this.callingCardAmiConfig == null){
	    				this.logger.info("No config file associated to this DNID.");
	    				return;
	    			}
		    		this.logger.info("CallingCard Ami Config loaded");

		    		ServiceFamily currentServiceFamily = ServiceFamily.valueOf(incomingChannel.getVariable(serviceFamily.name() + "_SERVICE_FAMILY"));	    		
		    		if(currentServiceFamily!=null && currentServiceFamily == ServiceFamily.CALLING_CARD){
		    			
		    			this.logger.info("CallingCard Call detected.");
		    			accessType = CallingCardAccessType.valueOf(incomingChannel.getVariable(serviceFamily.name() + "_ACCESS_TYPE"));
		    			
		    			Calendar answerDate = Calendar.getInstance();
		    			this.logger.info("answerDate: " + answerDate.getTimeInMillis());

		    			String childrenCdrId = incomingChannel.getVariable(serviceFamily.name() + "_CHILDREN_CDR_ID");

		    			FileUtils fileManager = new FileUtils();
						File callFile = new File(this.onlineCallPersistenceFolder + "" + childrenCdrId);
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
						}
						
		    		   
		    			
		    			
		    			switch (accessType) {
		    				case IVR:
		    				case IVR_DIALER:
		    				case IVR_PIN_FREE:
				    			this.logger.info("Calling Card Call detected for incoming channel " + incomingUniqueId);
				    			String unlimitedCall = incomingChannel.getVariable(serviceFamily.name() + "_UNLIMITED_CALL");
				    			String unmonitoredCall = incomingChannel.getVariable(serviceFamily.name() + "_UNMONITORED_CALL");
				    			
								if((unlimitedCall!=null && unlimitedCall.equals("TRUE")) || (unmonitoredCall!=null && unmonitoredCall.equals("TRUE"))){
									this.logger.info("Not monitoring the call " + incomingUniqueId + ".");
									this.logger.info("Unlimited Call: " + unlimitedCall + ".");
									this.logger.info("Unmonitored Call: " + unmonitoredCall + ".");
								}
								else{
				    			
									
									this.logger.info("Monitoring the call " + incomingUniqueId);
					    			
					    			CallingCardManagedCall managedCall = new CallingCardManagedCall();
					    			managedCall.setIncomingChannelId(incomingUniqueId);
					    			managedCall.setIncomingChannelName(incomingChannel.getName());
					    			managedCall.setIncomingCdrId(incomingChannel.getVariable("CDR(uniqueId)"));
				    				managedCall.setOutgoingCdrId(outgoingChannel.getVariable("CDR(uniqueId)"));
					    			managedCall.setOutgoingChannelId(outgoingUniqueId);
					    			managedCall.setServiceFamily(incomingChannel.getVariable(serviceFamily.name() + "_SERVICE_FAMILY"));
					    			managedCall.setAni(incomingChannel.getVariable(serviceFamily.name() + "_ANI"));
					    			managedCall.setDni(incomingChannel.getVariable(serviceFamily.name() + "_DNI"));
					    			managedCall.setDnid(incomingChannel.getVariable(serviceFamily.name() + "_DNID"));
					    			managedCall.setPinSerial(incomingChannel.getVariable(serviceFamily.name() + "_PIN_SERIAL"));
					    			managedCall.setChildrenCdrId(childrenCdrId);
					    			managedCall.setReservationId(Long.parseLong(incomingChannel.getVariable(serviceFamily.name() + "_RESERVATION")));
					    			managedCall.setEnterpriseId(enterpriseId);
					    			managedCall.setStartDate(answerDate);
					    			managedCall.setNextWakeupDate((Calendar)answerDate.clone());
					    			managedCall.setToHangupCall(false);
					    			managedCall.setAccessType(accessType);
					    			
					    			
					    			//RATER_TEST_MODE:1
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
					    			CallingCardCallManager callManager = new CallingCardCallManager(this, managedCall, incomingChannel, outgoingChannel);
					    			callManager.start();
					    			this.logger.info("CallManager created successfully");
					    			
					    		
					    			
					    			
								}
							break;
		    				case WEB_PHONE:	
		    					this.logger.info("AccessType " + accessType.name() + " not monitored .");
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
    			e.printStackTrace();
    		}

    	}
    	
    }
    
    public static void main(String[] args) throws Exception{
        CallingCardAmi callingCardAmi;
        callingCardAmi = new CallingCardAmi();
        callingCardAmi.run();
    }

	public CallingCardAmiLogger getLogger() {
		return this.logger;
	}

	public CallingCardAmiConfig getCallingCardAmiConfig() {
		return this.callingCardAmiConfig;
	}

	public InteraxTelephonyGenericEjbCaller getGenericEjbCaller() {
		return this.genericEjbCaller;
	}
	
	public String getConfigPath() {
		return configPath;
	}
}