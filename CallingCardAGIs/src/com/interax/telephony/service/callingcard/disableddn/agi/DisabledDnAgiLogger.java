package com.interax.telephony.service.callingcard.disableddn.agi;
import com.interax.telephony.service.log.InteraxTelephonyLogger;


public class DisabledDnAgiLogger extends InteraxTelephonyLogger{
	
	protected DisabledDnAgi promiscuousDnAgi;

	public DisabledDnAgiLogger(DisabledDnAgi promiscuousDnAgi, String path) {
		super(DisabledDnAgi.class, path);
		this.promiscuousDnAgi = promiscuousDnAgi;
	}
	
	protected String getPreamble(){
		return promiscuousDnAgi.getCdrId() + "|" + promiscuousDnAgi.getStep().name() + "|";
	}
	
}
