package com.interax.telephony.service.ippbx.data;


public interface IpPbxData {
	
	public IpPbxServiceType getServiceType();
	public void setServiceType(IpPbxServiceType serviceType);
	public void setServiceType(String serviceType);

	public IpPbxAccessType getAccessType();
	public void setAccessType(IpPbxAccessType accessType);
	public void setAccessType(String accessType);

}
