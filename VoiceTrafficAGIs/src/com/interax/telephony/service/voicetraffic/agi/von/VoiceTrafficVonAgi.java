package com.interax.telephony.service.voicetraffic.agi.von;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;

import com.interax.telephony.cdr.InteraxTelephonyCdrFormater;
import com.interax.telephony.server.agi.InteraxTelephonyAgiServer;
import com.interax.telephony.service.data.CdrCallDetailRecord;
import com.interax.telephony.service.data.ServiceDialStatus;
import com.interax.telephony.service.data.ServiceFamily;
import com.interax.telephony.service.data.ServiceReservation;
import com.interax.telephony.service.data.ServiceReservationStatus;
import com.interax.telephony.service.exception.InteraxTelephonyException;
import com.interax.telephony.service.exception.NoOpenReservationException;
import com.interax.telephony.service.exception.ReservationNotFoundException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficCreditLimitExceededException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficGeneralException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficInvalidAccessTypeException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficInvalidCountryCodeException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficInvalidServiceTypeException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficMaxConcurrentCallException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficNotEnoughBalanceException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficOverdueInvoiceException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficRateNotFoundException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficRestrictedCallException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficUnauthorizedCallException;
import com.interax.telephony.service.remote.RaterEJB;
import com.interax.telephony.service.voicetraffic.agi.forward.VoiceTrafficForwardAgiAudio;
import com.interax.telephony.service.voicetraffic.base.agi.VoiceTrafficBaseAgi;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficAccessType;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficCancelData;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficCommitData;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficRequestData;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficReservation;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficServiceType;
import com.interax.telephony.util.GeneralUtils;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;
import com.interax.utils.FileUtils;


public class VoiceTrafficVonAgi extends VoiceTrafficBaseAgi{

	private Thread agiThread;
	private AgiChannel agiChannel;
	private String agiClassName;
	private AgiRequest request;
	private String channelId;

	private VoiceTrafficVonAgiLogger logger;
	private VoiceTrafficVonAgiStep step;
	private static ServiceFamily serviceFamily = ServiceFamily.VOICE_TRAFFIC;
	private static VoiceTrafficAccessType accessType = VoiceTrafficAccessType.VON;
	private VoiceTrafficServiceType serviceType;
	private String ani = null;
	private String dnid = null;
	private long enterpriseId;
	private long customerId;

//	private boolean monitored = false;
	//TEST_MODE:1
	private boolean agiTestMode = false;
	//RATER_TEST_MODE:1
	private boolean raterTestMode = false;
	private int raterTestAvailablesSeconds = -1;
	private String cdrId = null;
	private VoiceTrafficRequestData currentRequestData = null;
	private VoiceTrafficReservation currentReservation = null;
	private VoiceTrafficVonAgiConfig config = null;
	private InteraxTelephonyGenericEjbCaller genericEjbCaller;
	private RaterEJB raterEJB;
	
	private String currentUserField = "";
	private String currentAccountCode = "";
	
	private FileUtils fileManager;
	private File callFile;
	
	private String commitPersistenceFolder;
	private String cancelPersistenceFolder;
	private String crashPersistenceFolder;
	private String onlineCallPersistenceFolder;

	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {

		this.step = VoiceTrafficVonAgiStep.INIT;
		this.agiThread = Thread.currentThread();
		this.agiClassName = this.getClass().getSimpleName();
		this.agiThread.setName(this.agiClassName + "Thread-" + agiThread.getId());
		
		this.currentUserField = this.getVariable("CDR(userfield)");
		this.currentAccountCode = serviceFamily + "|" + this.enterpriseId;
		
		
		this.request = arg0;
		this.agiChannel = arg1;
		this.logger = new VoiceTrafficVonAgiLogger(this, InteraxTelephonyAgiServer.getConfig().CONFIG_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/voicetraffic/von/");
		this.channelId = agiChannel.getVariable("CHANNEL");
		this.cdrId = getVariable("CDR(uniqueId)");
		this.logger.info("CHANNEL ID:" + this.channelId);
		this.logger.info("CDR ID:" + this.cdrId);
		
		String persistenceFolder = InteraxTelephonyAgiServer.getConfig().PERSISTENCE_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/" + serviceFamily.name().toLowerCase().replace("_", "") + "/" + VoiceTrafficVonAgi.class.getSimpleName(); 
		this.commitPersistenceFolder = persistenceFolder + "/commit/";
		this.cancelPersistenceFolder = persistenceFolder + "/cancel/";
		this.crashPersistenceFolder = persistenceFolder + "/crash/";
		this.onlineCallPersistenceFolder = persistenceFolder + "/onlineCall/";
		
		// TEST_MODE:2
		String agiMode = getVariable("AGI_MODE");
		if(agiMode!=null && agiMode.equalsIgnoreCase("test")){
			this.agiTestMode = true;
			this.logger.info("AGI_MODE TEST ACTIVATED.");
		}
		//RATER_TEST_MODE:2
		String raterMode = getVariable("RATER_MODE");
		if(raterMode!=null && raterMode.equalsIgnoreCase("test")){
			this.raterTestMode = true;
			this.logger.info("RATER MODE TEST ACTIVATED.");
			String raterSeconds = getVariable("RATER_SECONDS");
			this.logger.info("RATER TEST AVAILABLE SECONDS:" + raterSeconds);
			if(raterSeconds!= null){
				try{
					this.raterTestAvailablesSeconds = Integer.parseInt(raterSeconds);
				}
				catch(Exception e){
					this.logger.error("Error parsing raterTestAvailablesSeconds.");
					this.logger.debug("Detail: " + e.getMessage());
				}
			}
		}
		
		// Inicialización del AGI 
		this.logger.info(this.agiClassName + " started");
		this.step = VoiceTrafficVonAgiStep.ANSWER;
	
//		 Máquina de estados
		while(this.step != VoiceTrafficVonAgiStep.STOP){
		
			switch(step){
				case ANSWER:
					answerCall();
				break;
				
				case INIT_RESOURCES:
					initResources();
				break;
				
				case PROCESS_CALL_DATA:
					processCallData();
				break;
					
				case VALIDATE_DESTINY:
					validateDestiny();
				break;
					
				case MAKE_CALL:
					makeCall();
				break;
					
				case HANGUP:
					hangupCall();
				break;
			}
		}
		
		// IVR CDR... There can be only one!
		this.setVariable("CDR(amaflags)", "IGNORE");
		
		// Finalización del AGI
		this.logger.info(this.agiClassName + " stopped");
	}
	
	
	
	// FUNCIONES DE LA MÁQUINA DE ESTADOS
	
	private void answerCall(){
		try{
			this.logger.info("Trying to answer the channel");
			answer();
			this.logger.info("Channel answered successfully");
			this.step = VoiceTrafficVonAgiStep.INIT_RESOURCES;
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to answer the channel");
			this.step = VoiceTrafficVonAgiStep.HANGUP;
		}
	}
	
	private void initResources(){
//		try{
			this.logger.info("Trying to initialize resources");
			
			this.genericEjbCaller = new InteraxTelephonyGenericEjbCaller(logger);
			
			this.logger.info("Resources initialized successfully");
			this.step = VoiceTrafficVonAgiStep.PROCESS_CALL_DATA;
//		}
//		catch(AgiException ae){
//			this.logger.error("An error ocurred while trying to initialize resources", ae);
//			this.step = CallingCardAgiStep.HANGUP;
//		}
	}
	
	private void processCallData(){
		try{
			
			this.logger.info("Trying to process call data");
			
			this.logger.info("Loading configuration.");
			this.enterpriseId = Long.parseLong(this.getVariable(serviceFamily.name() + "_ENTERPRISE_ID"));
			this.serviceType = VoiceTrafficServiceType.valueOf(this.getVariable(serviceFamily.name() + "_SERVICE_TYPE"));
			this.currentAccountCode = serviceFamily + "|" + this.enterpriseId;
			this.config = VoiceTrafficVonAgiConfigLoader.getConfig(this.enterpriseId ,InteraxTelephonyAgiServer.getConfig().CONFIG_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/voicetraffic/von/");
			
			if(this.config==null){
				this.logger.info("No config file found.");
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			
			this.logger.info("Configuration loaded successfully.");
						
			this.customerId = Long.parseLong(this.getVariable(serviceFamily.name() + "_CUSTOMER_ID"));
			this.logger.info("CUSTOMER-ID: " + this.customerId);
//			this.ani = this.getVariable("CALLERID(num)");
                        this.ani = this.getVariable("ani");
			this.logger.info("ANI: " + this.ani);
			
			//TEST_MODE:3
			if(this.agiTestMode)
				this.dnid = generateTestDid();
			else
				this.dnid = this.request.getParameter("dnid");
			
			this.logger.info("DNID: " + this.dnid);
			this.logger.info("Enterprise: " + this.config.ENTERPRISE_NAME);
			
//			this.setVariable(serviceFamily.name() + "_ENTERPRISE_ID", this.enterpriseId + "");
//			this.setVariable(serviceFamily.name() + "_SERVICE_FAMILY", serviceFamily.name());
//			this.setVariable(serviceFamily.name() + "_ACCESS_TYPE", accessType.name());
//			this.setVariable(serviceFamily.name() + "_DNI", this.did);
//			this.setVariable(serviceFamily.name() + "_ANI", this.ani);
			
			this.step = VoiceTrafficVonAgiStep.VALIDATE_DESTINY;
			this.logger.info("Call data processed successfully");
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to process call data", ae);
			this.step = VoiceTrafficVonAgiStep.HANGUP;
		}
	}
	
	private void validateDestiny(){

		try{
			
			this.currentRequestData = new VoiceTrafficRequestData();
			this.currentRequestData.setAni(this.ani);
			this.currentRequestData.setDnid(this.dnid);
			this.currentRequestData.setCdrId(this.cdrId);
			this.currentRequestData.setPeerId(0);
			this.currentRequestData.setCustomerId(this.customerId);
			this.currentRequestData.setServiceFamily(serviceFamily);
			this.currentRequestData.setServiceType(this.serviceType);
			this.currentRequestData.setAccessType(accessType);
			
			try
			 {
				this.logger.info("Trying to create a reservation for the call");
				this.logger.debug("Request data detail: ", this.currentRequestData);

				this.currentReservation = this.createReservation(this.currentRequestData);
				this.logger.debug("Reservation detail: ", this.currentReservation);
				this.logger.info("Reservation for the call created successfully");
				this.logger.debug("Reservation detail: ", this.currentReservation);
				this.logger.info("Destiny validated successfully");
				
				if(this.currentReservation.getAvailableSeconds() == ServiceReservation.UNLIMITED_CALL_TIME)
					this.setVariable(serviceFamily.name() + "_UNLIMITED_CALL", "TRUE");
				else{	
					this.setVariable(serviceFamily.name() + "_UNLIMITED_CALL", "FALSE");
					
					if(this.currentReservation.getAvailableSeconds() < ServiceReservation.BASE_TIME_IN_SECONDS)
						this.setVariable(serviceFamily.name() + "_UNMONITORED_CALL", "TRUE");
				}
				
				this.step = VoiceTrafficVonAgiStep.MAKE_CALL;
			 }
			
			catch(VoiceTrafficInvalidCountryCodeException dnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Destination not found");
				streamFile(VoiceTrafficVonAgiAudio.CALL_INVALID_DESTINY);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficRestrictedCallException dnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Restricted call");
				streamFile(VoiceTrafficVonAgiAudio.CALL_RESTRICTED);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficCreditLimitExceededException rnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Credit limit exceeded");
				streamFile(VoiceTrafficVonAgiAudio.CREDIT_LIMIT_EXCEEDED);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}		
			catch(VoiceTrafficRateNotFoundException rnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Rate not found");
				streamFile(VoiceTrafficVonAgiAudio.CALL_RATE_NOT_FOUND);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficNotEnoughBalanceException nebe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: No enough balance");
				streamFile(VoiceTrafficVonAgiAudio.CALL_NO_ENOUGH_BALANCE);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficInvalidServiceTypeException pnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Invalid service type");
				streamFile(VoiceTrafficVonAgiAudio.CALL_TECH_PROBLEM);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficInvalidAccessTypeException pnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Invalid access type");
				streamFile(VoiceTrafficVonAgiAudio.CALL_TECH_PROBLEM);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficOverdueInvoiceException pnfe){
				this.logger.info("Reservation for the call couldn't be created successfully. Reason: Max overdued invoices limit reached.");
				this.streamFile(VoiceTrafficVonAgiAudio.OVERDUE_INVOICE_LIMIT_EXCEEDED );
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficUnauthorizedCallException pnfe){
				this.logger.info("Reservation for the call couldn't be created successfully. Reason: Requested call not authorized.");
				this.streamFile(VoiceTrafficVonAgiAudio.CALL_UNATHORIZED);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficMaxConcurrentCallException pnfe){
				this.logger.info("Reservation for the call couldn't be created successfully. Reason: Max concurrent call limit reached.");
				this.streamFile(VoiceTrafficVonAgiAudio.MAX_CONCURRENT_CALL_LIMIT_EXCEEDED);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(VoiceTrafficGeneralException ge){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: General Remote Exception");
				this.logger.error("Detail: " + ge.getMessage());
				streamFile(VoiceTrafficVonAgiAudio.CALL_TECH_PROBLEM);
				this.step = VoiceTrafficVonAgiStep.HANGUP;
				return;
			}
			catch(Exception e){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: General Local Exception");
				this.logger.error("Detail: " + e.getMessage());
				streamFile(VoiceTrafficVonAgiAudio.CALL_TECH_PROBLEM);
				this.step = VoiceTrafficVonAgiStep.HANGUP;	
				return;
			}
			
			
		}
		catch(Exception ae){
			this.logger.error("An error ocurred while trying to validate destiny", ae);
			this.logger.debug("Detail: " + ae.getMessage());
			this.step = VoiceTrafficVonAgiStep.HANGUP;
		}
	}

	private void makeCall() throws AgiException{
		try{
			this.logger.info("Trying to call");
			this.setVariable(serviceFamily.name() + "_ANI", this.ani);
			this.setVariable(serviceFamily.name() + "_DNID", this.dnid);
			this.setVariable(serviceFamily.name() + "_RESERVATION", "" + this.currentReservation.getId());
			this.setVariable("LIMIT_WARNING_FILE", VoiceTrafficVonAgiAudio.BEEP);
			
			String asteriskId = this.getVariable("ASTERISK_SERVER");
			this.currentUserField = InteraxTelephonyCdrFormater.formatCdrUserField(accessType, this.serviceType, this.customerId, null, null);
			
			this.callFile = new File(this.onlineCallPersistenceFolder + this.cdrId);
			this.callFile.getParentFile().mkdirs();
			Calendar startDate = Calendar.getInstance();
			CdrCallDetailRecord cdr = new CdrCallDetailRecord();
			cdr.setUniqueid(this.cdrId);
			cdr.setStartdate(startDate.getTime().getTime());
			cdr.setAccountcode(this.currentAccountCode);
			cdr.setAmaflags(3); //DOCUMENTATION
			cdr.setAnswerDate(0); //AMI
			cdr.setAsteriskId(asteriskId);
			cdr.setBillsec(0);
			cdr.setChannel(this.getVariable("CDR(channel)"));
			cdr.setClid(this.getVariable("CDR(clid)"));
			cdr.setDcontext(this.getVariable("CDR(dcontext)"));
			cdr.setDisposition("UNKNOWN");
			//cdr.setDst(this.dni);
			cdr.setDstchannel(null); //AMI
			cdr.setDuration(0);
			cdr.setHangupCause(0);
			cdr.setId(0);
			cdr.setLastapp("ResetCdr");
			cdr.setLastdata("w");
			cdr.setSrc(this.ani);
			cdr.setUserfield(currentUserField);
			
			byte fileDataCrash[] = (GeneralUtils.toText(cdr)).getBytes();
			fileManager = new FileUtils();		
			
			this.logger.info("Trying to create onlinecall persistence file: " + callFile.getName());
			try{
				fileManager.upload(callFile,fileDataCrash, false);
				this.logger.info("Onlinecall persistence file created successfully");
			}
			catch(Exception ex){
				this.logger.error("An error ocurred while trying to create onlinecall persistence file");
			}

			this.setVariable(serviceFamily.name() + "_CHILDREN_CDR_ID", this.getCdrId());
			
			String dialOptions = "Local/" + this.dnid + "@rt_vt_incoming/n";

			dialOptions += "|";
			if(this.currentReservation.getAvailableSeconds() != ServiceReservation.UNLIMITED_CALL_TIME){
				this.logger.info("Call limited to: " + this.currentReservation.getAvailableSeconds() + " seconds by Rater");
				dialOptions += "|L(" + this.currentReservation.getAvailableSeconds() * 1000 + ":30000:30000)";
			}else{
				if(this.config.UNLIMITED_CALLS_MAX_MINUTES > 0){
					int limitSeconds = (int) ((this.config.UNLIMITED_CALLS_MAX_MINUTES + (this.config.UNLIMITED_CALLS_RANDOM_MINUTES * Math.random())) * 60);
					this.logger.info("Call limited to: " + limitSeconds + " seconds by Agi");
					dialOptions += "|L(" + (int) ((this.config.UNLIMITED_CALLS_MAX_MINUTES + (this.config.UNLIMITED_CALLS_RANDOM_MINUTES * Math.random())) * 60 * 1000) + ":30000:30000)";
				}else{
					// UNLIMITED
					this.logger.info("Unlimited call");
				}
			}
			
			this.logger.info("Dial options: " + dialOptions);

			//exec("ForkCdr", "R");
			
			String lastCdrUniqueId = "";
			String lastCdrAnswer = "";
			String lastCdrStart = "";
			String lastCdrEnd = "";
			String lastCdrDuration = "";
			String lastCdrBillsec = "";
			String dialStatusStr = "";
			int lastCdrHangupCause = 0;
			this.currentUserField = InteraxTelephonyCdrFormater.formatCdrUserField(accessType, this.serviceType, this.customerId, null, null);
			this.setVariable("CDR(amaflags)","DOCUMENTATION");
			this.setVariable("CDR(accountCode)", this.currentAccountCode);
			this.setVariable("CDR(userfield)", this.currentUserField);
		try{
				
				exec("Dial", dialOptions);
				this.setVariable("CDR(src)", this.ani);
				this.setVariable("CDR(dst)", this.dnid);
				this.setVariable("CDR(uniqueid)", this.cdrId);
				this.setVariable("CDR(userfield)", currentUserField);
				exec("ResetCdr","w");
		
			//	fileManager.upload(callFile,("endDate=" + GeneralUtils.dateString2Calendar(getVariable("LAST_CDR_END")).getTime().getTime()).getBytes(),true);
				
				lastCdrUniqueId = getVariable("LAST_CDR_UNIQUEID");
				lastCdrAnswer = getVariable("LAST_CDR_ANSWER");
				lastCdrStart = getVariable("LAST_CDR_START");
				lastCdrEnd = getVariable("LAST_CDR_END");
				lastCdrDuration = getVariable("LAST_CDR_DURATION");
				lastCdrBillsec = getVariable("LAST_CDR_BILLSEC");
				dialStatusStr = getVariable("DIALSTATUS").replace(" ", "");
			
				try{
					lastCdrHangupCause = Integer.parseInt(getVariable("LAST_CDR_HANGUP_CAUSE"));
				}
				catch(Exception e){
					 lastCdrHangupCause = -1;
				}
			
			}
		catch(AgiException agiException){
				
			this.logger.warn("!Asterisk is down");
			String stringObject = new String(fileManager.download(callFile.getParent(),"/" + callFile.getName()));
			CdrCallDetailRecord cdrFromFile = (CdrCallDetailRecord) GeneralUtils.fromText(stringObject);

			Long stopDate = Calendar.getInstance().getTimeInMillis();
			Long duration =  Long.valueOf(Math.round((stopDate - cdrFromFile.getStartdate())/1000));
			lastCdrDuration = "" + duration; 
			lastCdrEnd = GeneralUtils.dateCalendar2String(GeneralUtils.dateLong2Calendar(Long.valueOf(cdrFromFile.getStopDate())));
			lastCdrUniqueId = this.cdrId;
			lastCdrStart =  GeneralUtils.dateCalendar2String(GeneralUtils.dateLong2Calendar(Long.valueOf(cdrFromFile.getStartdate())));

			if(cdrFromFile.getAnswerDate() > 0){
				Long billSeconds =  Long.valueOf(Math.round((stopDate - cdrFromFile.getAnswerDate())/1000));
				lastCdrAnswer = GeneralUtils.dateCalendar2String(GeneralUtils.dateLong2Calendar(Long.valueOf(cdrFromFile.getAnswerDate())));
				lastCdrBillsec = "" +billSeconds; 
				lastCdrHangupCause = 16;
				dialStatusStr = "ANSWER";

				cdrFromFile.setBillsec(billSeconds);
				cdrFromFile.setDisposition("ANSWERED");
			}else{
				lastCdrAnswer = "NULL";
				lastCdrBillsec = "0"; 
				lastCdrHangupCause = 0;
				dialStatusStr = "UNKNOWN";
			}	

			cdrFromFile.setDuration(duration);
			cdrFromFile.setHangupCause(lastCdrHangupCause);
			cdrFromFile.setStopDate(stopDate);

			fileManager.upload(callFile, GeneralUtils.toText(cdrFromFile).getBytes(), false);

			this.logger.info("Calculing rater data");
			this.logger.info("uniqueId:" + lastCdrUniqueId);
			this.logger.info("lastCdrStart:" + lastCdrStart);
			this.logger.info("lastCdrAnswer:" + lastCdrAnswer);
			this.logger.info("lastCdrEnd:" + lastCdrEnd);
			this.logger.info("lastCdrDuration:" + lastCdrDuration);
			this.logger.info("lastCdrBillsec:" + lastCdrBillsec);


			File crashFile = new File(crashPersistenceFolder + callFile.getName());
			crashFile.getParentFile().mkdirs();
			this.logger.info("Moving file from: " + callFile.getAbsolutePath() + " to :" + crashFile.getAbsolutePath());

			if(callFile.renameTo(crashFile))
				this.logger.info("File moved successfully");
			else
				this.logger.info("Error moving file");
				
		}

		this.logger.info("Trying to delete onlinecall persistence file: " + callFile.getName());
		try{
			callFile.delete();
			this.logger.info("Onlinecall persistence file deleted successfully");
		}
		catch(Exception e){
			this.logger.error("An error ocurred while trying to delete onlinecall persistence file");
		}

		ServiceDialStatus dialStatus = ServiceDialStatus.valueOf(dialStatusStr);

		try{

			switch(dialStatus){
				case ANSWER:
					this.logger.info("Call completed successfully");
				break;
				
				case BUSY:
					this.logger.info("Call couldn't be completed: Destiny is busy");
//					streamFile(VoiceTrafficVonAgiAudio.CALL_BUSY);
				break;

				case NOANSWER:
					this.logger.info("Call couldn't be completed: Destiny doesn't answer");
//					streamFile(VoiceTrafficVonAgiAudio.CALL_NOANSWER);
				break;
				
				case CHANUNAVAIL:
					this.logger.info("Call couldn't be completed: Destiny is not available");
//					streamFile(VoiceTrafficVonAgiAudio.CALL_INVALID_DESTINY);
				break;
				
				case CANCEL:
					this.logger.info("Call couldn't be completed: Canceled by the user");
				break;
				
				case UNKNOWN:
					this.logger.info("Crash");
				break;
				
				default:
					this.logger.info("Call couldn't be completed: Tech problem");
					streamFile(VoiceTrafficForwardAgiAudio.CALL_TECH_PROBLEM);
					break;
				}
				
			}catch(Exception e){
				this.logger.info("A exception when try to play a file on channel");
			}
			
			if(dialStatus == ServiceDialStatus.ANSWER){
				VoiceTrafficCommitData voiceTrafficCommitData = new VoiceTrafficCommitData();
			
				try{
					this.logger.info("Trying to collect commit data");
					
					Calendar startTime = GeneralUtils.dateString2Calendar(lastCdrStart);
					Calendar answerTime = GeneralUtils.dateString2Calendar(lastCdrAnswer);
					Calendar endTime = GeneralUtils.dateString2Calendar(lastCdrEnd);
					Integer callDuration = Integer.parseInt(lastCdrDuration);
					Integer billSeconds = Integer.parseInt(lastCdrBillsec);
					
					voiceTrafficCommitData.setStartTime(startTime);
					voiceTrafficCommitData.setAnswerTime(answerTime);
					voiceTrafficCommitData.setStopTime(endTime);
					voiceTrafficCommitData.setCallDuration(callDuration);
					voiceTrafficCommitData.setBillSeconds(billSeconds);
					voiceTrafficCommitData.setDialStatus(dialStatus);
					voiceTrafficCommitData.setCdrId(lastCdrUniqueId);
					voiceTrafficCommitData.setHangupCause(lastCdrHangupCause);
					voiceTrafficCommitData.setReservationId(this.currentReservation.getId());
					voiceTrafficCommitData.setServiceFamily(serviceFamily);
					voiceTrafficCommitData.setServiceType(this.serviceType);
					voiceTrafficCommitData.setAccessType(accessType);
					
					this.logger.info("Commit data collected successfully");
					
					this.logger.debug("Commit data detail: ", voiceTrafficCommitData);
					
					this.logger.info("Trying to commit reservation " + this.currentReservation.getId());
					if (this.commitReservation(this.currentReservation.getId(), voiceTrafficCommitData))
						this.logger.info("Reservation " + this.currentReservation.getId() + " commited successfully.");
					else
						this.logger.info("Reservation " + this.currentReservation.getId() + " couldn't be commited.");
						
				}
				catch(Exception e){
					this.logger.info("An error ocurred while trying to commit reservation");
					this.logger.debug("Detail: " + e.getMessage());
					
					File newFileVoiceTrafficCommitData = new File(this.commitPersistenceFolder + voiceTrafficCommitData.getCdrId());
					newFileVoiceTrafficCommitData.getParentFile().mkdirs();
					
					FileUtils fileManager = new FileUtils();
					byte fileData[] = GeneralUtils.toText(voiceTrafficCommitData).getBytes();

					this.logger.info("Trying to create commit persistence file: " + newFileVoiceTrafficCommitData.getName());
					try{
						fileManager.upload(newFileVoiceTrafficCommitData, fileData, false);
						this.logger.info("Commit persistence file created successfully");
					}
					catch(Exception ex){
						this.logger.error("An error ocurred while trying to create commit persistence file");
					}
				}
			}
			else{
				VoiceTrafficCancelData voiceTrafficCancelData = new VoiceTrafficCancelData();
				
				try{
					this.logger.info("Trying to collect cancel data");
					boolean dirty = (dialStatus == ServiceDialStatus.UNKNOWN);
					
					Calendar startTime = GeneralUtils.dateString2Calendar(lastCdrStart);
					Calendar endTime = GeneralUtils.dateString2Calendar(lastCdrEnd);
					Integer callDuration = Integer.parseInt(lastCdrDuration);
					
					voiceTrafficCancelData.setStartTime(startTime);
					voiceTrafficCancelData.setStopTime(endTime);
					voiceTrafficCancelData.setCallDuration(callDuration);
					voiceTrafficCancelData.setDialStatus(dialStatus);
					voiceTrafficCancelData.setCdrId(lastCdrUniqueId);
					voiceTrafficCancelData.setHangupCause(lastCdrHangupCause);
					voiceTrafficCancelData.setReservationId(this.currentReservation.getId());
					voiceTrafficCancelData.setServiceFamily(serviceFamily);
					voiceTrafficCancelData.setServiceType(this.serviceType);
					voiceTrafficCancelData.setAccessType(accessType);
					voiceTrafficCancelData.setDirty(dirty);
					
					this.logger.info("Cancel data collected successfully");
					this.logger.debug("Cancel data detail: ", voiceTrafficCancelData);

					this.logger.info("Trying to cancel reservation");
					this.cancelReservation(this.currentReservation.getId(),voiceTrafficCancelData);
					this.logger.info("Reservation canceled successfully");
				}
				catch(Exception e){
					this.logger.info("An error ocurred while trying to cancel reservation");
					this.logger.debug("Detail: " + e.getMessage());
					
					File newFileVoiceTrafficCancelData = new File(this.cancelPersistenceFolder + voiceTrafficCancelData.getCdrId());
					newFileVoiceTrafficCancelData.getParentFile().mkdirs();

					FileUtils fileManager = new FileUtils();
					byte fileData[] = GeneralUtils.toText(voiceTrafficCancelData).getBytes();

					this.logger.info("Trying to create cancel persistence file: " + newFileVoiceTrafficCancelData.getName());
					try{
						fileManager.upload(newFileVoiceTrafficCancelData, fileData, false);
						this.logger.info("Cancel persistence file created successfully");
					}
					catch(Exception ex){
						this.logger.error("An error ocurred while trying to create cancel persistence file");
					}
				}
			}
			this.step = VoiceTrafficVonAgiStep.HANGUP;
			
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to call", ae);
			this.step = VoiceTrafficVonAgiStep.HANGUP;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void hangupCall() throws AgiException{
		try{
			this.logger.info("Trying to hangup the channel");
			hangup();
			this.logger.info("Channel hanged up successfully");
			this.step = VoiceTrafficVonAgiStep.STOP;
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to hangup the channel", ae);
			this.step = VoiceTrafficVonAgiStep.STOP;
		}
	}

	// LLAMADAS REMOTAS
	
	private VoiceTrafficReservation createReservation(VoiceTrafficRequestData voiceTrafficRequestData) throws Exception {
		
		//RATER_TEST_MODE:3
		if(this.raterTestMode){
			VoiceTrafficReservation res = new VoiceTrafficReservation();
			if(this.raterTestAvailablesSeconds != -1)
				res.setAvailableSeconds(this.raterTestAvailablesSeconds);
			else
				res.setAvailableSeconds(ServiceReservation.BASE_TIME_IN_SECONDS);
			res.setId(1000);
			res.setReservationStatus(ServiceReservationStatus.STARTED);
			return res;
		}
		
		raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", this.config.RATER_EJB_SERVER, this.config.RATER_EJB_PORT);
		if(raterEJB != null)
			return (VoiceTrafficReservation)raterEJB.createReservation(voiceTrafficRequestData);
		else
			return null;
	}
	
	private boolean cancelReservation(Long reservationId, VoiceTrafficCancelData voiceTrafficCancelData){

		//RATER_TEST_MODE:4
		if(this.raterTestMode){
			return true;
		}
		
		raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", this.config.RATER_EJB_SERVER, this.config.RATER_EJB_PORT);
		try {
			return raterEJB.cancelReservation(reservationId, voiceTrafficCancelData);
		} catch (NoOpenReservationException e) {
			this.logger.info("NoOpenReservationException, couldn't cancelReservation " + reservationId);
			return false;
		} catch (ReservationNotFoundException e) {
			this.logger.info("ReservationNotFoundException, couldn't cancelReservation " + reservationId);
			return false;
		} catch (InteraxTelephonyException e) {
			this.logger.info("CallingCardGeneralException, couldn't cancelReservation " + reservationId);
			return false;
		}
	}
	
	private boolean	commitReservation(Long reservationId, VoiceTrafficCommitData voiceTrafficCommitData){
		
		//RATER_TEST_MODE:5
		if(this.raterTestMode){
			return true;
		}
		
		raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", this.config.RATER_EJB_SERVER, this.config.RATER_EJB_PORT);
		try {
			return raterEJB.commitReservation(reservationId, voiceTrafficCommitData);
		} catch (NoOpenReservationException e) {
			this.logger.info("NoOpenReservationException, couldn't commitReservation " + reservationId);
			return false;
		} catch (ReservationNotFoundException e) {
			this.logger.info("ReservationNotFoundException, couldn't commitReservation " + reservationId);
			return false;
		} catch (InteraxTelephonyException e) {
			this.logger.info("InteraxTelephonyException, couldn't commitReservation " + reservationId);
			return false;
		}
	}

	
	// GETTERS
	
	public String getChannelId() {
		return channelId;
	}
	
	public VoiceTrafficVonAgiStep getStep() {
		return step;
	}
	
	public String getCdrId(){
		return this.cdrId;
	}
	

	// HELPERS FOR TEST
	
	private String generateTestDid(){
		int index = new Random().nextInt(10);
		String[] did = {"19737741150","12025963525","12034133238","12126019940","13474225352","15186593069","15858151152","16463487843","17169869089","17183039001"};
		return did[index];
	}
	
}