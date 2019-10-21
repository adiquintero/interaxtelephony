package com.interax.telephony.service.interactivevoiceresponse.agi.incoming;
import com.interax.telephony.service.log.InteraxTelephonyLogger;


public class InteractiveVoiceResponseIncomingAgiLogger extends InteraxTelephonyLogger{
	
	protected InteractiveVoiceResponseIncomingAgi ivrIncomingIvrAgi;

	public InteractiveVoiceResponseIncomingAgiLogger(InteractiveVoiceResponseIncomingAgi ivrIncomingIvrAgi, String path) {
		super(InteractiveVoiceResponseIncomingAgi.class, path);
		this.ivrIncomingIvrAgi = ivrIncomingIvrAgi;
	}
	
	protected String getPreamble(){
		return ivrIncomingIvrAgi.getCdrId() + "|" + ivrIncomingIvrAgi.getStep().name() + "|";
	}
	
}
