package com.interax.telephony.service.ami.ippbx;

import com.interax.telephony.service.ami.InteraxTelephonyManagedCall;
import com.interax.telephony.service.ippbx.data.IpPbxAccessType;

public class IpPbxManagedCall extends InteraxTelephonyManagedCall {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accessType;
	
	public IpPbxAccessType getAccessType() {
		return IpPbxAccessType.values()[this.accessType];
	}
	public void setAccessType(IpPbxAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = IpPbxAccessType.valueOf(accessType).ordinal();
	}
	
}
