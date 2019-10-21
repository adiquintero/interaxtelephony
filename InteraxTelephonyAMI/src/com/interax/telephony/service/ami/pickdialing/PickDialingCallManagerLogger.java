package com.interax.telephony.service.ami.pickdialing;

import com.interax.telephony.service.log.InteraxTelephonyLogger;

public class PickDialingCallManagerLogger extends InteraxTelephonyLogger{

	protected PickDialingCallManager pickDialingCallManager;

	public PickDialingCallManagerLogger(PickDialingCallManager pickDialingCallManager , String path) {
		super(PickDialingCallManager.class, path);
		this.pickDialingCallManager = pickDialingCallManager;
	}

	protected String getPreamble() {
		return pickDialingCallManager.getChildrenCdrId() + "|";
	}
	
}
