package com.interax.telephony.service.voicetraffic.agi.von;
import com.interax.telephony.service.log.InteraxTelephonyLogger;

public class VoiceTrafficVonAgiLogger extends InteraxTelephonyLogger{
	
	protected VoiceTrafficVonAgi voiceTrafficVonAgi;

	public VoiceTrafficVonAgiLogger(VoiceTrafficVonAgi voiceTrafficVonAgi, String path) {
		super(VoiceTrafficVonAgi.class, path);
		this.voiceTrafficVonAgi = voiceTrafficVonAgi;
	}
	
	protected String getPreamble(){
		return voiceTrafficVonAgi.getCdrId() + "|" + voiceTrafficVonAgi.getStep().name() + "|";
	}
	
}
