package com.interax.telephony.service.pickdialing.data;


public interface PickDialingData {
	
	public PickDialingServiceType getServiceType();
	public void setServiceType(PickDialingServiceType serviceType);
	public void setServiceType(String serviceType);

	public PickDialingAccessType getAccessType();
	public void setAccessType(PickDialingAccessType accessType);
	public void setAccessType(String accessType);

}
