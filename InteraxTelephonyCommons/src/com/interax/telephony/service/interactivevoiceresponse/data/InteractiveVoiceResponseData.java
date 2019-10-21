package com.interax.telephony.service.interactivevoiceresponse.data;


public interface InteractiveVoiceResponseData {
	
	public InteractiveVoiceResponseServiceType getServiceType();
	public void setServiceType(InteractiveVoiceResponseServiceType serviceType);
	public void setServiceType(String serviceType);

	public InteractiveVoiceResponseAccessType getAccessType();
	public void setAccessType(InteractiveVoiceResponseAccessType accessType);
	public void setAccessType(String accessType);

}
