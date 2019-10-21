package com.interax.telephony.service.did.data;

import com.interax.telephony.service.did.data.DidAccessType;
import com.interax.telephony.service.did.data.DidServiceType;

public interface DidData {

	public DidServiceType getServiceType();
	public void setServiceType(DidServiceType serviceType);
	public void setServiceType(String serviceType);

	public DidAccessType getAccessType();
	public void setAccessType(DidAccessType accessType);
	public void setAccessType(String accessType);
}
