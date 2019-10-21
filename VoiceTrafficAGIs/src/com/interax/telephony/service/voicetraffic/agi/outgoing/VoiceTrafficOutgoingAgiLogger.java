package com.interax.telephony.service.voicetraffic.agi.outgoing;
import com.interax.telephony.service.log.InteraxTelephonyLogger;


public class VoiceTrafficOutgoingAgiLogger extends InteraxTelephonyLogger{
	
	protected VoiceTrafficOutgoingAgi voiceTrafficAgi;

	public VoiceTrafficOutgoingAgiLogger(VoiceTrafficOutgoingAgi voiceTrafficAgi, String path) {
		super(VoiceTrafficOutgoingAgi.class, path);
		this.voiceTrafficAgi = voiceTrafficAgi;
	}
	
	protected String getPreamble(){
		return voiceTrafficAgi.getCdrId() + "|" + voiceTrafficAgi.getStep().name() + "|";
	}
	
}
