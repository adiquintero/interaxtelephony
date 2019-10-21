package com.interax.telephony.service.ami.voicetraffic;

import com.interax.telephony.service.ami.InteraxTelephonyManagedCall;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficAccessType;

public class VoiceTrafficManagedCall extends InteraxTelephonyManagedCall {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accessType;
	
	public VoiceTrafficAccessType getAccessType() {
		return VoiceTrafficAccessType.values()[this.accessType];
	}
	public void setAccessType(VoiceTrafficAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = VoiceTrafficAccessType.valueOf(accessType).ordinal();
	}
	
}
