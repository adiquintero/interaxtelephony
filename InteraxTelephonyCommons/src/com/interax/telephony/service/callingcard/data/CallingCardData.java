package com.interax.telephony.service.callingcard.data;


public interface CallingCardData {

	public CallingCardServiceType getServiceType();
	public void setServiceType(CallingCardServiceType serviceType);
	public void setServiceType(String serviceType);

	public CallingCardAccessType getAccessType();
	public void setAccessType(CallingCardAccessType accessType);
	public void setAccessType(String accessType);
}
