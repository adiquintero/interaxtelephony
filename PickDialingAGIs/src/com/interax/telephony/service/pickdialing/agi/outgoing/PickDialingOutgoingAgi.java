package com.interax.telephony.service.pickdialing.agi.outgoing;

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
import com.interax.telephony.service.exception.pickdialing.PickDialingCreditLimitExceededException;
import com.interax.telephony.service.exception.pickdialing.PickDialingGeneralException;
import com.interax.telephony.service.exception.pickdialing.PickDialingInvalidAccessTypeException;
import com.interax.telephony.service.exception.pickdialing.PickDialingInvalidCountryCodeException;
import com.interax.telephony.service.exception.pickdialing.PickDialingInvalidServiceTypeException;
import com.interax.telephony.service.exception.pickdialing.PickDialingMaxConcurrentCallException;
import com.interax.telephony.service.exception.pickdialing.PickDialingNotEnoughBalanceException;
import com.interax.telephony.service.exception.pickdialing.PickDialingOverdueInvoiceException;
import com.interax.telephony.service.exception.pickdialing.PickDialingRateNotFoundException;
import com.interax.telephony.service.exception.pickdialing.PickDialingRestrictedCallException;
import com.interax.telephony.service.exception.pickdialing.PickDialingUnauthorizedCallException;
import com.interax.telephony.service.pickdialing.base.agi.PickDialingBaseAgi;
import com.interax.telephony.service.pickdialing.data.PickDialingAccessType;
import com.interax.telephony.service.pickdialing.data.PickDialingCancelData;
import com.interax.telephony.service.pickdialing.data.PickDialingCommitData;
import com.interax.telephony.service.pickdialing.data.PickDialingRequestData;
import com.interax.telephony.service.pickdialing.data.PickDialingReservation;
import com.interax.telephony.service.pickdialing.data.PickDialingServiceType;
import com.interax.telephony.service.remote.RaterEJB;
import com.interax.telephony.util.GeneralUtils;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;
import com.interax.utils.FileUtils;


public class PickDialingOutgoingAgi extends PickDialingBaseAgi{

	private Thread agiThread;
	private AgiChannel agiChannel;
	private AgiRequest request;
	private String channelId;

	private PickDialingOutgoingAgiLogger logger;
	private PickDialingOutgoingAgiStep step;
	private static ServiceFamily serviceFamily = ServiceFamily.PICK_DIALING;
	private static PickDialingAccessType accessType = PickDialingAccessType.OUTGOING;
	private PickDialingServiceType serviceType;
	private String ani = null;
	private String dni = null;
	private String dnid = null;
	private long enterpriseId;
	private long customerId;

	private String cdrId = null;
	private String parentCdrId = null;
	//	private boolean cdrForked = false;

	//TEST_MODE:1
	private boolean agiTestMode = false;
	//RATER_TEST_MODE:1
	private boolean raterTestMode = false;
	private int raterTestAvailablesSeconds = -1;

	private PickDialingRequestData currentRequestData = null;
	private PickDialingReservation currentReservation = null;
	private PickDialingOutgoingAgiConfig config = null;
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

		this.step = PickDialingOutgoingAgiStep.INIT;
		this.agiThread = Thread.currentThread();
		this.agiThread.setName("PickDialingOutgoingAgiThread-" + agiThread.getId());
			//this.cdrId = getVariable("CDR(uniqueId)");
		this.parentCdrId = getVariable("PARENT_CDR_UNIQUE_ID");
		
		if(this.parentCdrId == null){
			this.parentCdrId = getVariable("CDR(uniqueid)");
		}
		
		exec("NoOp","parentCdrId: " + parentCdrId);
		String childrenCdrId = getVariable("CHILDREN_CDR_UNIQUE_ID");
		
		if(childrenCdrId == null){
			childrenCdrId = "0";
		}
		
		exec("NoOp","childrenCdrId: " + childrenCdrId);
		int nextChildrenCdrId;
		
		if(childrenCdrId==null){
			nextChildrenCdrId = 0;
		}else{
			nextChildrenCdrId = Integer.valueOf(childrenCdrId);
		}
		
		this.setVariable("CHILDREN_CDR_UNIQUE_ID", ""+ (++nextChildrenCdrId));
		this.cdrId = this.parentCdrId + "-" + nextChildrenCdrId;

		this.request = arg0;
		this.agiChannel = arg1;
		this.logger = new PickDialingOutgoingAgiLogger(this, InteraxTelephonyAgiServer.getConfig().CONFIG_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/pickdialing/outgoing/");
		this.channelId = agiChannel.getVariable("CHANNEL");
		this.logger.info("CHANNEL ID:" + this.channelId);
		this.logger.info("CDR ID:" + this.cdrId);

		String persistenceFolder = InteraxTelephonyAgiServer.getConfig().PERSISTENCE_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/" + serviceFamily.name().toLowerCase().replace("_", "") + "/" + PickDialingOutgoingAgi.class.getSimpleName(); 
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
		this.logger.info("PickDialingOutgoingAgi started");
		this.step = PickDialingOutgoingAgiStep.INIT_RESOURCES;

		//		 Máquina de estados
		while(this.step != PickDialingOutgoingAgiStep.STOP){

			switch(step){

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
		hangup();
		// Finalización del AGI
		this.logger.info("PickDialingOutgoingAgi stopped");
	}



	// FUNCIONES DE LA MÁQUINA DE ESTADOS

	private void initResources(){
		//		try{
		this.logger.info("Trying to initialize resources");

		this.genericEjbCaller = new InteraxTelephonyGenericEjbCaller(logger);

		this.logger.info("Resources initialized successfully");
		this.step = PickDialingOutgoingAgiStep.PROCESS_CALL_DATA;
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
			this.serviceType = PickDialingServiceType.valueOf(this.getVariable(serviceFamily.name() + "_SERVICE_TYPE"));
			this.currentAccountCode = serviceFamily + "|" + this.enterpriseId;
			this.config = PickDialingOutgoingAgiConfigLoader.getConfig(this.enterpriseId ,InteraxTelephonyAgiServer.getConfig().CONFIG_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/pickdialing/outgoing/");
			
			if(this.config==null){
				this.logger.info("No config file found.");
				this.step = PickDialingOutgoingAgiStep.STOP;
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
				this.dni = generateTestDni();
			else
				this.dni = this.request.getParameter("dni");

			this.logger.info("DNI: " + this.dni);

			this.dnid = this.getVariable("MACRO_EXTEN");
			this.logger.info("DNID: " + this.dnid);

			this.setVariable("CDR(userfield)", InteraxTelephonyCdrFormater.formatCdrUserField(accessType, this.serviceType, this.customerId, null, null));

			this.logger.info("Enterprise: " + this.config.ENTERPRISE_NAME);
			this.setVariable(serviceFamily.name() + "_ENTERPRISE_ID", this.enterpriseId + "");
			this.setVariable(serviceFamily.name() + "_SERVICE_FAMILY", serviceFamily.name());
			this.setVariable(serviceFamily.name() + "_ACCESS_TYPE", accessType.name());
			this.setVariable(serviceFamily.name() + "_DNI", this.dni);
			this.setVariable(serviceFamily.name() + "_DNID", this.dnid);
			this.setVariable(serviceFamily.name() + "_ANI", this.ani);

			this.step = PickDialingOutgoingAgiStep.VALIDATE_DESTINY;
			this.logger.info("Call data processed successfully");
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to process call data", ae);
			this.logger.debug("Detail: ", ae.getMessage());
			this.step = PickDialingOutgoingAgiStep.STOP;
		}
	}

	private void validateDestiny(){

		try{

			this.currentRequestData = new PickDialingRequestData();
			this.currentRequestData.setAni(this.ani);
			this.currentRequestData.setDni(this.dni);
			this.currentRequestData.setDnid(this.dnid);
			this.currentRequestData.setCdrId(this.cdrId);
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
				else	
					this.setVariable(serviceFamily.name() + "_UNLIMITED_CALL", "FALSE");

				
				if(this.currentReservation.getAvailableSeconds() == 0)
				{
					this.logger.info("No available seconds");
					this.step = PickDialingOutgoingAgiStep.STOP;
					return;
				}
				this.step = PickDialingOutgoingAgiStep.MAKE_CALL;
			}

			catch(PickDialingInvalidCountryCodeException dnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Destination not found");
				streamFile(PickDialingOutgoingAgiAudio.CALL_INVALID_DESTINY);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingRestrictedCallException dnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Restricted call");
				streamFile(PickDialingOutgoingAgiAudio.CALL_RESTRICTED);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingCreditLimitExceededException rnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Credit limit exceeded");
				streamFile(PickDialingOutgoingAgiAudio.CREDIT_LIMIT_EXCEEDED);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}		
			catch(PickDialingRateNotFoundException rnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Rate not found");
				streamFile(PickDialingOutgoingAgiAudio.CALL_RATE_NOT_FOUND);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingNotEnoughBalanceException nebe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: No enough balance");
				streamFile(PickDialingOutgoingAgiAudio.CALL_NO_ENOUGH_BALANCE);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingInvalidServiceTypeException pnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Invalid service type");
				//streamFile(PickDialingOutgoingAgiAudio.CALL_TECH_PROBLEM);
				streamFile(PickDialingOutgoingAgiAudio.REORDER);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingInvalidAccessTypeException pnfe){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: Invalid access type");
				streamFile(PickDialingOutgoingAgiAudio.CALL_TECH_PROBLEM);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingOverdueInvoiceException pnfe){
				this.logger.info("Reservation for the call couldn't be created successfully. Reason: Max overdued invoices limit reached.");
				this.streamFile(PickDialingOutgoingAgiAudio.OVERDUE_INVOICE_LIMIT_EXCEEDED );
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingUnauthorizedCallException pnfe){
				this.logger.info("Reservation for the call couldn't be created successfully. Reason: Requested call not authorized.");
				this.streamFile(PickDialingOutgoingAgiAudio.CALL_UNATHORIZED);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingMaxConcurrentCallException pnfe){
				this.logger.info("Reservation for the call couldn't be created successfully. Reason: Max concurrent call limit reached.");
				this.streamFile(PickDialingOutgoingAgiAudio.MAX_CONCURRENT_CALL_LIMIT_EXCEEDED);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(PickDialingGeneralException ge){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: General Remote Exception");
				this.logger.error("Detail: " + ge.getMessage());
				streamFile(PickDialingOutgoingAgiAudio.CALL_TECH_PROBLEM);
				this.step = PickDialingOutgoingAgiStep.STOP;
				return;
			}
			catch(Exception e){
				this.logger.error("Reservation for the call couldn't be created successfully. Reason: General Local Exception");
				this.logger.error("Detail: " + e.getMessage());
				streamFile(PickDialingOutgoingAgiAudio.CALL_TECH_PROBLEM);
				this.step = PickDialingOutgoingAgiStep.STOP;	
				return;
			}


		}
		catch(Exception ae){
			this.logger.error("An error ocurred while trying to validate destiny", ae);
			this.logger.debug("Detail: " + ae.getMessage());
			this.step = PickDialingOutgoingAgiStep.STOP;
		}
	}

	private void makeCall() throws AgiException{
		try{
			this.logger.info("Trying to call");

			this.setVariable(serviceFamily.name() + "_DNI", this.dni);
			this.setVariable(serviceFamily.name() + "_RESERVATION", "" + this.currentReservation.getId());
			this.setVariable("LIMIT_WARNING_FILE", "beep");

			String asteriskId = this.getVariable("ASTERISK_SERVER");
			this.currentUserField = InteraxTelephonyCdrFormater.formatCdrUserField(accessType, this.serviceType, this.customerId, this.ani, null);

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
			cdr.setDst(this.dni);
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

			String dialOptions = "";
			//String dialOptions = this.config.DIAL_PROTOCOL + "/";

			if(this.agiTestMode){
				//dialOptions += this.config.DIAL_TEST_PREFIX + this.dni + "@" + this.config.DIAL_TEST_GATEWAY;
				dialOptions += this.createDialOption(serviceFamily.name(), this.serviceType.name(), accessType.name(), this.config.ENTERPRISE_ID, this.dni);
		
			}
			else if(this.currentReservation.isInternCall()){ 
				dialOptions = "Local/" + this.dni + "@" + InteraxTelephonyAgiServer.getConfig().DID_INCOMING_CONTEXT;
			}
			else{
				//dialOptions += this.config.DIAL_PREFIX + this.dni +"@" + this.config.DIAL_GATEWAY;
				dialOptions += this.createDialOption(serviceFamily.name(), this.serviceType.name(), accessType.name(), this.config.ENTERPRISE_ID, this.dni);
				
			}

			dialOptions += "|" + this.config.DIAL_TIMEOUT;
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
			exec("ResetCdr", "");

			String lastCdrUniqueId = "";
			String lastCdrAnswer = "";
			String lastCdrStart = "";
			String lastCdrEnd = "";
			String lastCdrDuration = "";
			String lastCdrBillsec = "";
			String dialStatusStr = "";
			int lastCdrHangupCause = 0;
			this.setVariable("CDR(amaflags)","DOCUMENTATION");
			dialOptions += "|D(:w#w#)";
			try{
				exec("Dial", dialOptions);
				this.logger.info("Dial options: " + dialOptions);
				String callerIdName = this.getVariable("CALLERID(name)");
				this.setVariable("CDR(clid)","\"" + callerIdName + "\" <" + this.ani + ">");
				this.setVariable("CDR(src)", this.ani);
				this.setVariable("CDR(dst)", this.dni);
				this.setVariable("CDR(uniqueid)", this.cdrId);
				this.setVariable("CDR(userfield)",this.currentUserField);
				this.setVariable("CDR(accountCode)",this.currentAccountCode);

				exec("ResetCdr","w");

				this.setVariable("CDR(amaflags)","IGNORE");

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
					hangup();
					break;

				case BUSY:
					this.logger.info("Call couldn't be completed: Destiny is busy");
					break;

				case NOANSWER:
					this.logger.info("Call couldn't be completed: Destiny doesn't answer");
					break;

				case CHANUNAVAIL:
					this.logger.info("Call couldn't be completed: Destiny is not available");
					break;

				case CANCEL:
					this.logger.info("Call couldn't be completed: Canceled by the user");
					break;

				case UNKNOWN:
					this.logger.info("Crash");
					break;

				default:
					this.logger.info("Call couldn't be completed: Tech problem");
				streamFile(PickDialingOutgoingAgiAudio.CALL_TECH_PROBLEM);
				break;
				}

			}catch(Exception e){
				this.logger.info("A exception when try to play a file on channel");
			}

			if(dialStatus == ServiceDialStatus.ANSWER){
				PickDialingCommitData pickDialingCommitData = new PickDialingCommitData();

				try{
					this.logger.info("Trying to collect commit data");

					Calendar startTime = GeneralUtils.dateString2Calendar(lastCdrStart);
					Calendar answerTime = GeneralUtils.dateString2Calendar(lastCdrAnswer);
					Calendar endTime = GeneralUtils.dateString2Calendar(lastCdrEnd);
					Integer callDuration = Integer.parseInt(lastCdrDuration);
					Integer billSeconds = Integer.parseInt(lastCdrBillsec);

					pickDialingCommitData.setStartTime(startTime);
					pickDialingCommitData.setAnswerTime(answerTime);
					pickDialingCommitData.setStopTime(endTime);
					pickDialingCommitData.setCallDuration(callDuration);
					pickDialingCommitData.setBillSeconds(billSeconds);
					pickDialingCommitData.setDialStatus(dialStatus);
					pickDialingCommitData.setCdrId(lastCdrUniqueId);
					pickDialingCommitData.setHangupCause(lastCdrHangupCause);
					pickDialingCommitData.setReservationId(this.currentReservation.getId());
					pickDialingCommitData.setServiceFamily(serviceFamily);
					pickDialingCommitData.setServiceType(this.serviceType);
					pickDialingCommitData.setAccessType(accessType);

					this.logger.info("Commit data collected successfully");

					this.logger.debug("Commit data detail: ", pickDialingCommitData);

					this.logger.info("Trying to commit reservation " + this.currentReservation.getId());
					if (this.commitReservation(this.currentReservation.getId(), pickDialingCommitData))
						this.logger.info("Reservation " + this.currentReservation.getId() + " commited successfully.");
					else
						this.logger.info("Reservation " + this.currentReservation.getId() + " couldn't be commited.");

				}
				catch(Exception e){
					this.logger.info("An error ocurred while trying to commit reservation");
					this.logger.debug("Detail: " + e.getMessage());

					File newFilePickDialingCommitData = new File(this.commitPersistenceFolder + pickDialingCommitData.getCdrId());
					newFilePickDialingCommitData.getParentFile().mkdirs();

					FileUtils fileManager = new FileUtils();
					byte fileData[] = GeneralUtils.toText(pickDialingCommitData).getBytes();

					this.logger.info("Trying to create commit persistence file: " + newFilePickDialingCommitData.getName());
					try{
						fileManager.upload(newFilePickDialingCommitData, fileData, false);
						this.logger.info("Commit persistence file created successfully");
					}
					catch(Exception ex){
						this.logger.error("An error ocurred while trying to create commit persistence file");
					}
				}
			}else{
				PickDialingCancelData pickDialingCancelData = new PickDialingCancelData();

				try{
					this.logger.info("Trying to collect cancel data");
					boolean dirty = (dialStatus == ServiceDialStatus.UNKNOWN);

					Calendar startTime = GeneralUtils.dateString2Calendar(lastCdrStart);
					Calendar endTime = GeneralUtils.dateString2Calendar(lastCdrEnd);
					Integer callDuration = Integer.parseInt(lastCdrDuration);

					pickDialingCancelData.setStartTime(startTime);
					pickDialingCancelData.setStopTime(endTime);
					pickDialingCancelData.setCallDuration(callDuration);
					pickDialingCancelData.setDialStatus(dialStatus);
					pickDialingCancelData.setCdrId(lastCdrUniqueId);
					pickDialingCancelData.setHangupCause(lastCdrHangupCause);
					pickDialingCancelData.setServiceFamily(serviceFamily);
					pickDialingCancelData.setServiceType(this.serviceType);
					pickDialingCancelData.setAccessType(accessType);
					pickDialingCancelData.setDirty(dirty);

					this.logger.info("Cancel data collected successfully");
					this.logger.debug("Cancel data detail: ", pickDialingCancelData);
					this.logger.info("Trying to cancel reservation");
					this.cancelReservation(this.currentReservation.getId(),pickDialingCancelData);
					this.logger.info("Reservation canceled successfully");

				}catch(Exception e){
					this.logger.info("An error ocurred while trying to cancel reservation");
					this.logger.debug("Detail: " + e.getMessage());
					
					File newFilePickDialingCancelData = new File(this.cancelPersistenceFolder + pickDialingCancelData.getCdrId());
					newFilePickDialingCancelData.getParentFile().mkdirs();

					FileUtils fileManager = new FileUtils();
					byte fileData[] = GeneralUtils.toText(pickDialingCancelData).getBytes();

					this.logger.info("Trying to create cancel persistence file: " + newFilePickDialingCancelData.getName());
					try{
						fileManager.upload(newFilePickDialingCancelData, fileData, false);
						this.logger.info("Cancel persistence file created successfully");
					}
					catch(Exception ex){
						this.logger.error("An error ocurred while trying to create cancel persistence file");
					}
				}
			}
			this.step = PickDialingOutgoingAgiStep.HANGUP;

		}catch(AgiException ae){
			this.logger.error("An error ocurred while trying to call", ae);
			this.step = PickDialingOutgoingAgiStep.STOP;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void hangupCall() throws AgiException{
		try{
			this.logger.info("Trying to hangup the channel");
			hangup();
			this.logger.info("Channel hanged up successfully");
			this.step = PickDialingOutgoingAgiStep.STOP;
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to hangup the channel", ae);
			this.step = PickDialingOutgoingAgiStep.STOP;
		}
	}

	
	
	// LLAMADAS REMOTAS

	private PickDialingReservation createReservation(PickDialingRequestData pickDialingRequestData) throws Exception {

		//RATER_TEST_MODE:3
		if(this.raterTestMode){
			PickDialingReservation res = new PickDialingReservation();
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
			return (PickDialingReservation)raterEJB.createReservation(pickDialingRequestData);
		else
			return null;
	}

	private boolean cancelReservation(Long reservationId, PickDialingCancelData pickDialingCancelData){

		//RATER_TEST_MODE:4
		if(this.raterTestMode){
			return true;
		}

		raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", this.config.RATER_EJB_SERVER, this.config.RATER_EJB_PORT);
		try {
			return raterEJB.cancelReservation(reservationId, pickDialingCancelData);
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

	private boolean	commitReservation(Long reservationId, PickDialingCommitData pickDialingCommitData){

		//RATER_TEST_MODE:5
		if(this.raterTestMode){
			return true;
		}

		raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", this.config.RATER_EJB_SERVER, this.config.RATER_EJB_PORT);
		try {
			return raterEJB.commitReservation(reservationId, pickDialingCommitData);
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

	public PickDialingOutgoingAgiStep getStep() {
		return step;
	}

	public String getCdrId(){
		return this.cdrId;
	}

	// HELPERS FOR TEST

	private String generateTestDni(){
		int index = new Random().nextInt(10);
		String[] dni = {"19737741150","12025963525","12034133238","12126019940","13474225352","15186593069","15858151152","16463487843","17169869089","17183039001"};
		return dni[index];
	}

}
