package com.interax.telephony.service.callingcard.agi;
import com.interax.telephony.service.log.InteraxTelephonyLogger;


public class CallingCardAgiLogger extends InteraxTelephonyLogger{
	
	protected CallingCardAgi callingCardAgi;

	public CallingCardAgiLogger(CallingCardAgi callingCardAgi, String path) {
		super(CallingCardAgi.class, path);
		this.callingCardAgi = callingCardAgi;
	}
	
	protected String getPreamble(){
		return callingCardAgi.getCdrId() + "|" + callingCardAgi.getStep().name() + "|";
	}
	
}
