package com.interax.telephony.service.ami.pickdialing;

import com.interax.telephony.service.ami.InteraxTelephonyManagedCall;
import com.interax.telephony.service.pickdialing.data.PickDialingAccessType;

public class PickDialingManagedCall extends InteraxTelephonyManagedCall {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accessType;
	
	public PickDialingAccessType getAccessType() {
		return PickDialingAccessType.values()[this.accessType];
	}
	public void setAccessType(PickDialingAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = PickDialingAccessType.valueOf(accessType).ordinal();
	}
	
}
