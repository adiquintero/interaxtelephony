package com.interax.telephony.service.voicetraffic.agi.voipforward;
import com.interax.telephony.service.log.InteraxTelephonyLogger;


public class VoiceTrafficVoipForwardAgiLogger extends InteraxTelephonyLogger{
	
	protected VoiceTrafficVoipForwardAgi voiceTrafficForwardAgi;

	public VoiceTrafficVoipForwardAgiLogger(VoiceTrafficVoipForwardAgi voiceTrafficForwardAgi, String path) {
		super(VoiceTrafficVoipForwardAgi.class, path);
		this.voiceTrafficForwardAgi = voiceTrafficForwardAgi;
	}
	
	protected String getPreamble(){
		return voiceTrafficForwardAgi.getCdrId() + "|" + voiceTrafficForwardAgi.getStep().name() + "|";
	}
	
}
