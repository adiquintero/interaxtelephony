package com.interax.telephony.service.ami.voicetraffic;

import com.interax.telephony.service.log.InteraxTelephonyLogger;

public class VoiceTrafficCallManagerLogger extends InteraxTelephonyLogger{

	protected VoiceTrafficCallManager voiceTrafficCallManager;

	public VoiceTrafficCallManagerLogger(VoiceTrafficCallManager voiceTrafficCallManager , String path) {
		super(VoiceTrafficCallManager.class, path);
		this.voiceTrafficCallManager = voiceTrafficCallManager;
	}

	protected String getPreamble() {
		return voiceTrafficCallManager.getChildrenCdrId() + "|";
	}
	
}
