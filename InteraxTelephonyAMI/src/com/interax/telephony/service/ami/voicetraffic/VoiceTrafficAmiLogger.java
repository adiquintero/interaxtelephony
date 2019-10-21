package com.interax.telephony.service.ami.voicetraffic;
import com.interax.telephony.service.ami.InteraxTelephonyAmiServerLogger;


public class VoiceTrafficAmiLogger extends InteraxTelephonyAmiServerLogger {
	
	VoiceTrafficAmi voiceTrafficAmi;
	
	public VoiceTrafficAmiLogger(VoiceTrafficAmi voiceTrafficAmi, String path) {
		super(VoiceTrafficAmi.class, path);
		this.voiceTrafficAmi = voiceTrafficAmi;
	}
	
	protected String getPreamble(){
		//return this.voiceTrafficAmi.getChannelId() == null ? "" : this.voiceTrafficAmi.getChannelId() + "|";
		return "";
	}
	
}
