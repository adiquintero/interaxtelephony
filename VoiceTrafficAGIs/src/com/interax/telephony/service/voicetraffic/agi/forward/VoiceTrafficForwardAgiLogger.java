package com.interax.telephony.service.voicetraffic.agi.forward;
import com.interax.telephony.service.log.InteraxTelephonyLogger;


public class VoiceTrafficForwardAgiLogger extends InteraxTelephonyLogger{
	
	protected VoiceTrafficForwardAgi voiceTrafficForwardAgi;

	public VoiceTrafficForwardAgiLogger(VoiceTrafficForwardAgi voiceTrafficForwardAgi, String path) {
		super(VoiceTrafficForwardAgi.class, path);
		this.voiceTrafficForwardAgi = voiceTrafficForwardAgi;
	}
	
	protected String getPreamble(){
		return voiceTrafficForwardAgi.getCdrId() + "|" + voiceTrafficForwardAgi.getStep().name() + "|";
	}
	
}
