package com.interax.telephony.service.ami.did;

import com.interax.telephony.service.ami.InteraxTelephonyManagedCall;
import com.interax.telephony.service.did.data.DidAccessType;

public class DidManagedCall extends InteraxTelephonyManagedCall {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accessType;
	
	public DidAccessType getAccessType() {
		return DidAccessType.values()[this.accessType];
	}
	public void setAccessType(DidAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = DidAccessType.valueOf(accessType).ordinal();
	}

}
