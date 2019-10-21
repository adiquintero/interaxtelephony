package com.interax.telephony.service.pickdialing.agi.outgoing;
import com.interax.telephony.service.log.InteraxTelephonyLogger;


public class PickDialingOutgoingAgiLogger extends InteraxTelephonyLogger{
	
	protected PickDialingOutgoingAgi pickDialingOutgoingAgi;

	public PickDialingOutgoingAgiLogger(PickDialingOutgoingAgi pickDialingOutgoingAgi, String path) {
		super(PickDialingOutgoingAgi.class, path);
		this.pickDialingOutgoingAgi = pickDialingOutgoingAgi;
	}
	
	protected String getPreamble(){
		return pickDialingOutgoingAgi.getCdrId() + "|" + pickDialingOutgoingAgi.getStep().name() + "|";
	}
	
}
