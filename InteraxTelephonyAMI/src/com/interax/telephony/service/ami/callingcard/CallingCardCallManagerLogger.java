package com.interax.telephony.service.ami.callingcard;


import com.interax.telephony.service.log.InteraxTelephonyLogger;

public class CallingCardCallManagerLogger extends InteraxTelephonyLogger {

	protected CallingCardCallManager callingCardCallManager;

	public CallingCardCallManagerLogger(CallingCardCallManager callingCardCallManager, String path) {
		super(CallingCardCallManager.class, path);
		this.callingCardCallManager = callingCardCallManager;
	}

	protected String getPreamble() {
		return callingCardCallManager.getChildrenCdrId() + "|";
	}
}
