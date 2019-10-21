package com.interax.telephony.service.voicetraffic.data;


public interface VoiceTrafficData {
	
	public VoiceTrafficServiceType getServiceType();
	public void setServiceType(VoiceTrafficServiceType serviceType);
	public void setServiceType(String serviceType);

	public VoiceTrafficAccessType getAccessType();
	public void setAccessType(VoiceTrafficAccessType accessType);
	public void setAccessType(String accessType);

}
