package com.interax.telephony.service.ami.did;

import com.interax.telephony.service.ami.did.DidCallManager;
import com.interax.telephony.service.log.InteraxTelephonyLogger;

public class DidCallManagerLogger extends InteraxTelephonyLogger{

	protected DidCallManager didCallManager;

	public DidCallManagerLogger(DidCallManager didCallManager , String path) {
		super(DidCallManager.class, path);
		this.didCallManager = didCallManager;
	}

	protected String getPreamble() {
		return didCallManager.getChildrenCdrId() + "|";
	}

}
