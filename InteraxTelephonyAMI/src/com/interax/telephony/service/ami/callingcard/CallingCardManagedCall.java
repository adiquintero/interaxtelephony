package com.interax.telephony.service.ami.callingcard;

import com.interax.telephony.service.ami.InteraxTelephonyManagedCall;
import com.interax.telephony.service.callingcard.data.CallingCardAccessType;

public class CallingCardManagedCall extends InteraxTelephonyManagedCall{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pinSerial;
	private int accessType;
	
	public String getPinSerial() {
		return pinSerial;
	}
	public void setPinSerial(String pinSerial) {
		this.pinSerial = pinSerial;
	}

	public CallingCardAccessType getAccessType() {
		return CallingCardAccessType.values()[this.accessType];
	}
	public void setAccessType(CallingCardAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = CallingCardAccessType.valueOf(accessType).ordinal();
	}
	
}
