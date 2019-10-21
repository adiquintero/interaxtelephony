package com.interax.telephony.service.interactivevoiceresponse.agi.incoming;

//import java.util.Hashtable;
import java.util.List;
import java.util.Random;
//import java.util.Vector;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;

import com.interax.telephony.cdr.InteraxTelephonyCdrFormater;
import com.interax.telephony.server.agi.InteraxTelephonyAgiServer;
import com.interax.telephony.service.data.ServiceFamily;
import com.interax.telephony.service.interactivevoiceresponse.base.agi.InteractiveVoiceResponseBaseAgi;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseAccessType;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseAction;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseCallStatus;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseDialog;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseOptionAnswer;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseRequestData;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseScript;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseScriptAnswer;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseServiceType;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseTransactionalRequest;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseTransactionalResponse;
import com.interax.telephony.service.interactivevoiceresponse.dialog.DialogProcessor;
import com.interax.telephony.service.remote.interactivevoiceresponse.InteractiveVoiceResponseScriptEJB;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;
import java.util.ArrayList;
import java.util.HashMap;


public class InteractiveVoiceResponseIncomingAgi extends InteractiveVoiceResponseBaseAgi{

	private Thread agiThread;
	private AgiChannel agiChannel;
	private AgiRequest request;
	private String channelId;
	private String cdrId = null;
	private InteractiveVoiceResponseIncomingAgiLogger logger;
	private InteractiveVoiceResponseIncomingAgiStep step;
	private InteractiveVoiceResponseAction actionStep;
	private static ServiceFamily serviceFamily = ServiceFamily.IVR;
	private InteractiveVoiceResponseAccessType accessType = InteractiveVoiceResponseAccessType.INCOMING;
	private InteractiveVoiceResponseServiceType serviceType;
	private String ani = null;
	private String dni = null;
	private long enterpriseId;
	private long ivrId;
	private long nextScriptAction;
	private long nextChildrenCdrId;
	private boolean startIvr = true;
	private InteractiveVoiceResponseScript ivrScript;
	private InteractiveVoiceResponseAction ivrAction;
	private InteractiveVoiceResponseRequestData currentRequestData = null;
	private InteractiveVoiceResponseIncomingAgiConfig config = null;
	private InteraxTelephonyGenericEjbCaller genericEjbCaller;
	private String currentUserField = "";
	private String currentAccountCode = "";
	private InteractiveVoiceResponseScriptEJB ivrScriptEJB;

	private static final Boolean UNIQUE_ID_IN_DNI = false;
	private HashMap<String, Object> action;
	private String dnid;
	private String language;
	//FIXME  Test    
	//private Vector<CallCenterOption> options;
	//FIXME  End Test  
	private int cdrCallAttempt = 0;
	private String parentCdrId;
	private static HashMap<Long, Integer> callCounter = new HashMap<Long, Integer>();
	//FINCallCenterAgi

	public void service(AgiRequest arg0, AgiChannel arg1) throws AgiException {

		this.step = InteractiveVoiceResponseIncomingAgiStep.INIT;
		this.agiThread = Thread.currentThread();
		this.agiThread.setName("IvrIncomingAgiThread-" + agiThread.getId());

		this.request = arg0;
		this.agiChannel = arg1;
		this.logger = new InteractiveVoiceResponseIncomingAgiLogger(this, InteraxTelephonyAgiServer.getConfig().CONFIG_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/ivr/incoming/");
		this.channelId = agiChannel.getVariable("CHANNEL");
		this.dnid = getVariable("CALLERID(dnid)");
		this.cdrId = getVariable("CDR(uniqueId)");
		this.logger.info("CHANNEL ID:" + this.channelId);
		this.logger.info("CDR ID:" + this.cdrId);
		
		String childrenCdrId = getVariable("CHILDREN_CDR_UNIQUE_ID");
		if(childrenCdrId == null){
			childrenCdrId = "0";
		}
		this.logger.info("CDR ID:" + childrenCdrId);

		if(childrenCdrId==null){
			this.nextChildrenCdrId = 0;
		}else{
			this.nextChildrenCdrId = Integer.valueOf(childrenCdrId);
		}
		this.setVariable("CHILDREN_CDR_UNIQUE_ID", "" + nextChildrenCdrId);



		//String agiMode = getVariable("AGI_MODE");
		

		// Inicialización del AGI 
		this.logger.info("IvrIncomingIvrAgi started");
		this.logger.info("Skipping Answer");
		this.step = InteractiveVoiceResponseIncomingAgiStep.INIT_RESOURCES;

		//		 Máquina de estados
		while(this.step != InteractiveVoiceResponseIncomingAgiStep.STOP){

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

			case PROCESS_SCRIPT_DATA:
				processScriptData();
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
		this.logger.info("IvrIncomingIvrAgi stopped");
	}



	// FUNCIONES DE LA MÁQUINA DE ESTADOS

	private void answerCall(){
		try{
			this.logger.info("Trying to answer the channel");
			answer();
			this.logger.info("Channel answered successfully");
			this.step = InteractiveVoiceResponseIncomingAgiStep.INIT_RESOURCES;
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to answer the channel");
			this.step = InteractiveVoiceResponseIncomingAgiStep.HANGUP;
		}
	}

	private void initResources(){
		//		try{
		this.logger.info("Trying to initialize resources");

		this.genericEjbCaller = new InteraxTelephonyGenericEjbCaller(logger);

		this.logger.info("Resources initialized successfully");
		this.step = InteractiveVoiceResponseIncomingAgiStep.PROCESS_CALL_DATA;
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
			this.serviceType = InteractiveVoiceResponseServiceType.valueOf(this.getVariable(serviceFamily.name() + "_SERVICE_TYPE"));
			this.accessType = InteractiveVoiceResponseAccessType.valueOf(this.getVariable(serviceFamily.name() + "_ACCESS_TYPE"));
			this.currentAccountCode = serviceFamily + "|" + this.enterpriseId;
			this.config = InteractiveVoiceResponseIncomingAgiConfigLoader.getConfig(this.enterpriseId ,InteraxTelephonyAgiServer.getConfig().CONFIG_PATH + InteraxTelephonyAgiServer.getConfig().AGIS_CONFIG_PATH + "/ivr/incoming");

			if(this.config==null){
				this.logger.info("No config file found.");
				this.step = InteractiveVoiceResponseIncomingAgiStep.HANGUP;
				return;
			}

			this.logger.info("Configuration loaded successfully.");

			this.ivrId = Long.parseLong(this.getVariable(serviceFamily.name() + "_IVR_ID"));
			this.logger.info("IVR-ID: " + this.ivrId);
			//this.callCenterId = 1L; //Long.parseLong(this.getVariable(serviceFamily.name() + "_CALL_CENTER_ID"));
			//this.logger.info("CALLCENTER-ID: " + this.callCenterId);
			this.ani = this.getVariable("CALLERID(num)");
			this.logger.info("ANI: " + this.ani);

			//TEST_MODE:3
//			if(this.agiTestMode)
//				this.dni = generateTestDni();
//			else
				this.dni = this.request.getParameter("dni");

			this.logger.info("DNI: " + this.dni);
			this.logger.info("Enterprise: " + this.config.ENTERPRISE_NAME);

			this.setVariable(serviceFamily.name() + "_ENTERPRISE_ID", this.enterpriseId + "");
			this.setVariable(serviceFamily.name() + "_SERVICE_FAMILY", serviceFamily.name());
			this.setVariable(serviceFamily.name() + "_ACCESS_TYPE", accessType.name());
			this.setVariable(serviceFamily.name() + "_DNI", this.dni);
			this.setVariable(serviceFamily.name() + "_ANI", this.ani);

			this.step = InteractiveVoiceResponseIncomingAgiStep.PROCESS_SCRIPT_DATA;
			this.logger.info("Call data processed successfully");
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to process call data", ae);
			this.step = InteractiveVoiceResponseIncomingAgiStep.HANGUP;
		}
	}

	private void processScriptData(){

		try{

			this.currentRequestData = new InteractiveVoiceResponseRequestData();
			this.currentRequestData.setAni(this.ani);
			this.currentRequestData.setDni(this.dni);
			this.currentRequestData.setCdrId(this.cdrId);
			this.currentRequestData.setIvrId(this.ivrId);
			this.currentRequestData.setServiceFamily(serviceFamily);
			this.currentRequestData.setServiceType(this.serviceType);
			this.currentRequestData.setAccessType(accessType);

			if(this.startIvr){
				this.ivrScriptEJB = (InteractiveVoiceResponseScriptEJB)this.genericEjbCaller.getGenericController("ejb/InteractiveVoiceResponseScriptEJB", this.config.EJB_SERVER,this.config.EJB_PORT);			
				this.ivrScript  =  this.getScript(this.dni, this.ani);
				this.nextScriptAction = ivrScript.getInitialDialogId();
			}
			
			
			//FIXME TEST
			//CallCenterScript script  =  getScript(this.dnid, "es",this.ani);
			//FIXME End TEST
			this.logger.info("InteractiveVoiceResponseScript ACTIVE");
			List<InteractiveVoiceResponseOptionAnswer> ivrOptionAnswers = new ArrayList<InteractiveVoiceResponseOptionAnswer>();
			InteractiveVoiceResponseScriptAnswer ivrScriptAnswer = new InteractiveVoiceResponseScriptAnswer();

			ivrScriptAnswer.setOptionAnswers(ivrOptionAnswers);
			ivrScriptAnswer.setCallId(this.ivrScript.getCallId());

			HashMap<String,DialogProcessor> instances = new HashMap<String,DialogProcessor>();
			//InteractiveVoiceResponseAction callCenterAction;
			String nameTypeDialog;
			
			
			
//			Long scriptId = null;
			this.logger.info("DialogId RECEIVED");
			
			InteractiveVoiceResponseDialog dialog = ivrScript.getDialog(this.nextScriptAction);

//			if(scriptId==null)
//				scriptId = dialog.getScriptId();

			try
			{
				nameTypeDialog = getStringCamelCase(dialog.getType().name());
				DialogProcessor processor = (DialogProcessor)instances.get(dialog.getType().name());
				if (processor == null){
					Class classobject = Class.forName("com.interax.telephony.service.interactivevoiceresponse.dialog.Dialog" + nameTypeDialog + "Processor");
					processor = (DialogProcessor)classobject.newInstance();
					this.logger.info("DialogProcessor obtained : Dialog" + nameTypeDialog + "Procesor");
					processor.setActualScript(ivrScript);
					processor.setAudioFolder(this.config.AUDIO_FOLDER);
					processor.setActualOptionAnswers(ivrOptionAnswers);
					instances.put(dialog.getType().name(), processor);
				}

				this.logger.info("Preparing to process InteractiveVoiceResponseDialog");
				processor.setLogger(this.logger);
				this.action = processor.processDialog(dialog);
				this.logger.info("InteractiveVoiceResponseDialog processed");

				this.ivrAction = (InteractiveVoiceResponseAction)action.get(InteractiveVoiceResponseAction.class.getSimpleName());
				this.logger.info("InteractiveVoiceResponseAction obtained:"  + this.ivrAction);
				this.actionStep = dialog.getAction();

				while(this.actionStep != InteractiveVoiceResponseAction.STOP){

					switch(actionStep){
					case NEXT_DIALOG:
						nextDialogAction();
						break;

					case NONE:
						noneAction();
						break;
						
					case SET:
						setAction();
						break;

					case DEAD_AGI:
						callAgi();
						break;
						
					case DIAL:
						makeCall();
						break;

					case HANGUP:
						hangupCall();
						break;
					}

				}
				
			}
			catch(Exception e){
				this.logger.error("An error ocurred while trying to process actionStep", e);
				this.logger.debug("Detail: " + e.getMessage());
				this.step = InteractiveVoiceResponseIncomingAgiStep.HANGUP;
			}


		}
		catch(Exception ae){
			this.logger.error("An error ocurred while trying to process script data", ae);
			this.logger.debug("Detail: " + ae.getMessage());
			this.step = InteractiveVoiceResponseIncomingAgiStep.HANGUP;
		}
	}

	private void makeCall() throws AgiException{

		try{
			this.logger.info("Trying to call");

			this.setVariable(serviceFamily.name() + "_DNI", this.dni);
			this.setVariable("LIMIT_WARNING_FILE", "beep");

			this.parentCdrId = this.cdrId; 
			this.setVariable(serviceFamily.name() + "_CHILDREN_CDR_ID", this.getCdrId());

			try{
				String dialData = (String)action.get(String.class.getSimpleName());
				
				Long ivrScriptId = ivrScript.getId();
				
				this.logger.info("DialData is " + dialData);
				this.logger.info("ScriptId is " + ivrScriptId);
				this.logger.info("Current callCount for this script is " + callCounter.get(ivrScriptId));
				String dataAction =  this.dialParser(dialData, this.dnid, ivrScript.getCallId(), ivrScriptId);
				this.logger.info("Calling to :" + dataAction);
				if (!UNIQUE_ID_IN_DNI){
					this.ani += "#" + ivrScript.getCallId();
					this.setVariable("CALLERID(num)", this.ani);
				}
				this.logger.info("Ani is :" + this.ani);

     			exec("ResetCdr","w"); //tiempo del ivr
     			exec("ResetCdr","");

			
				exec("Dial",dataAction);
				this.logger.info("Dial options: " + dataAction);
				this.currentUserField =  InteraxTelephonyCdrFormater.formatCdrUserField(this.accessType, this.serviceType, this.ivrId, this.ivrScript.getCallId(), this.dni);

				this.setVariable("CDR(dst)", dataAction);
				this.setVariable("CDR(uniqueid)", this.parentCdrId + "-" + ++nextChildrenCdrId);
				this.setVariable("CDR(amaflags)", "DOCUMENTATION");
				this.currentAccountCode = serviceFamily + "|" + this.enterpriseId;
				this.setVariable("CDR(accountCode)",this.currentAccountCode);
				this.setVariable("CDR(userfield)", this.currentUserField);
				
				exec("ResetCdr","w");
				exec("ResetCdr","");
				
				this.setVariable("CDR(amaflags)", "IGNORE");
				
			}
			catch(AgiException agiException){
				this.logger.warn("!Asterisk is down");
			}

			this.step = InteractiveVoiceResponseIncomingAgiStep.HANGUP;
			this.actionStep = actionStep.HANGUP;


		}catch(AgiException ae){
			this.logger.error("An error ocurred while trying to call", ae);
			this.step = InteractiveVoiceResponseIncomingAgiStep.HANGUP;

		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.logger.error("An error ocurred while trying to create persistent commit data file.");
		}
	}

	private void hangupCall() throws AgiException{
		try{
			this.logger.info("Trying to hangup the channel");
			hangup();
			this.logger.info("Channel hanged up successfully");
			this.actionStep = actionStep.STOP;
			this.step = InteractiveVoiceResponseIncomingAgiStep.STOP;
		}
		catch(AgiException ae){
			this.logger.error("An error ocurred while trying to hangup the channel", ae);
			this.step = InteractiveVoiceResponseIncomingAgiStep.STOP;
		}
	}

	public synchronized String dialParser(String dialData, String dnid, Long callId, Long scriptId){
		
		    if(!callCounter.containsKey(scriptId))
		    	callCounter.put(scriptId, 0);
			int currentCallCount = callCounter.get(scriptId);
				callCounter.put(scriptId, ++currentCallCount);
			
			String[] s = dialData.split("@");
			StringBuffer dialBuffer = new StringBuffer();
			
			// PROTOCOLO
			dialBuffer.append(s[0]);
			// DNID
			dialBuffer.append(dnid);
			// IDENTIFICADOR
			if (UNIQUE_ID_IN_DNI){
				dialBuffer.append("#");
				dialBuffer.append(callId);
			}
			// PEER
			String peersData[] = s[1].split(",");
			String peer = null;
			String firstPeer = null;
			if(peersData.length>1){
				int totalRoundRobinWeight = 0;
				for(int i=0; i<peersData.length; i++){
					String[] peerData = peersData[i].split(":");
					if(i==0) firstPeer = peerData[0];
					int peerRoundRobinWeight = Integer.parseInt(peerData[1]);
					totalRoundRobinWeight += peerRoundRobinWeight;
					if(currentCallCount<=totalRoundRobinWeight){
						peer = peerData[0];
						break;
					}
				}
			}
			else{
				peer = peersData[0];
			}
			
			if(peer==null){
				callCounter.put(scriptId, 1);
				peer = firstPeer;
			}
			
			dialBuffer.append("@");
			dialBuffer.append(peer);
			
			// DIAL STRING
			return dialBuffer.toString();
	}
	
	//StepAction Funtions

	private void callAgi(){
		String data = (String)action.get(String.class.getSimpleName());	    
		try {
			this.exec("DeadAgi", data);
			
			this.actionStep = InteractiveVoiceResponseAction.STOP;
			this.step = InteractiveVoiceResponseIncomingAgiStep.HANGUP;
		} catch (AgiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void nextDialogAction(){
		this.nextScriptAction = Long.parseLong((String)action.get(String.class.getSimpleName()));
		this.actionStep = InteractiveVoiceResponseAction.STOP;
		this.step = InteractiveVoiceResponseIncomingAgiStep.PROCESS_SCRIPT_DATA;
		this.startIvr = false;
		
	}

	private void noneAction(){
		String data = (String)action.get(String.class.getSimpleName());	    
		String[] actionData = data.split("\\|");
//		String setData = data.substring(0, data.indexOf('|'));
		String setData = actionData[0];
		
//		action.put(String.class.getSimpleName(), data.substring(data.indexOf('|') + 1));
		action.put(String.class.getSimpleName(), actionData[1]);
		try {
			this.exec("Set", setData);
		} catch (AgiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.actionStep = actionStep.NEXT_DIALOG;

		if(setData.contains("LANGUAGE()=")){
			this.language = data.substring(data.indexOf('=') + 1);
		}

//		if(setData.contains("VENTAS")){
//			this.dnid = "836827";
//		}
//		
//		if(setData.contains("ATC")){
//			this.dnid = "282";
//		}
//		
//		if(setData.contains("RECARGA")){
//			this.dnid = "7322742";
//		}
//		if(setData.contains("DISTRIBUTION")){
//			this.dnid = "7322742";
//		}
//		
	
		if(actionData.length == 3)
		{
			this.dnid = actionData[2];
		}
		
	}
	
	

	private void setAction(){
		try {
			
			
			String data = (String)action.get(String.class.getSimpleName());	        		
			String setData = data.substring(0, data.indexOf('|'));
			action.put(String.class.getSimpleName(), data.substring(data.indexOf('|') + 1));
			this.exec("Set", setData);
		
			if(setData.contains("LANGUAGE()=")){
				this.language = data.substring(data.indexOf('=') + 1);
			}
			
			this.ivrAction = InteractiveVoiceResponseAction.NEXT_DIALOG;

		}catch (AgiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private  InteractiveVoiceResponseScript getScript(String dni, String ani)
	{
		try
		{
			List<String> aniList = new ArrayList<String>();
			aniList.add(ani);

			HashMap<String, List> relatedEntities = new HashMap<String, List>();
			String requestData = dni; 
			relatedEntities.put("ani",aniList);

			InteractiveVoiceResponseTransactionalRequest ivrTransactionalRequest = new InteractiveVoiceResponseTransactionalRequest();
			ivrTransactionalRequest.setRelatedEntities(relatedEntities);
			ivrTransactionalRequest.setRequestData(requestData);

			return ((InteractiveVoiceResponseScript)(this.ivrScriptEJB.getCompleteScript(ivrTransactionalRequest)).getResponseData());
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getStringCamelCase(String transform){
		String aux = transform.substring(1).toLowerCase();
		char firstCapital = transform.charAt(0);
		return firstCapital + aux;

	}

	public void changeCallLanguage(Long calleId, String language)  {
		try{
			HashMap<String,List> relatedEntities  = new HashMap<String, List>();
			List<Long> callId = new ArrayList<Long>();
			callId.add(calleId);
			relatedEntities.put("callId",callId);
			InteractiveVoiceResponseTransactionalRequest ivrTransactionalRequest = new InteractiveVoiceResponseTransactionalRequest();
			ivrTransactionalRequest.setRequestData(String.valueOf(language));
			ivrTransactionalRequest.setRelatedEntities(relatedEntities);
			InteractiveVoiceResponseTransactionalResponse ivrTransactionalResponse = ivrScriptEJB.updateCallLanguage(ivrTransactionalRequest);
			logger.info("The response of changeCallStatus: " + ivrTransactionalResponse.getResponseData().toString());
		}catch(Exception e){
			e.printStackTrace();
			return ;
		}

	}
	
	public void sendAnswers(InteractiveVoiceResponseScriptAnswer scriptAnswer) {
		try{	 

			InteractiveVoiceResponseTransactionalRequest ivrTransactionalRequest = new InteractiveVoiceResponseTransactionalRequest();
			ivrTransactionalRequest.setRequestData(scriptAnswer);
			ivrTransactionalRequest.setRequestType(InteractiveVoiceResponseScriptAnswer.class);
			InteractiveVoiceResponseTransactionalResponse ivrTransactionalResponse = ivrScriptEJB.sendAnswer(ivrTransactionalRequest);
			logger.info("The response of sendAnswers: " + ivrTransactionalResponse.getResponseData().toString());
		}catch(Exception e){
			e.printStackTrace();
			return ;
		}
	}

	public void changeCallStatus(Long calleId, InteractiveVoiceResponseCallStatus status)  {
		try{
			HashMap<String,List> relatedEntities  = new HashMap<String, List>();
			List<Long> callId = new ArrayList<Long>();
			callId.add(calleId);
			relatedEntities.put("callId",callId);
			InteractiveVoiceResponseTransactionalRequest ivrTransactionalRequest = new InteractiveVoiceResponseTransactionalRequest();
			ivrTransactionalRequest.setRequestData(String.valueOf(status));
			ivrTransactionalRequest.setRelatedEntities(relatedEntities);
			InteractiveVoiceResponseTransactionalResponse ivrTransactionalResponse = ivrScriptEJB.updateCallStatus(ivrTransactionalRequest);
			logger.info("The response of changeCallStatus: " + ivrTransactionalResponse.getResponseData().toString());
		}catch(Exception e){
			e.printStackTrace();
			return ;
		}

	}
	
	// GETTERS

	public String getChannelId() {
		return channelId;
	}

	public InteractiveVoiceResponseIncomingAgiStep getStep() {
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
