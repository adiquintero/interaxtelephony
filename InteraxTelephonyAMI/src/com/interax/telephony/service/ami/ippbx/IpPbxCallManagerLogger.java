package com.interax.telephony.service.ami.ippbx;

import com.interax.telephony.service.ami.callingcard.CallingCardAmi;
import com.interax.telephony.service.log.InteraxTelephonyLogger;

public class IpPbxCallManagerLogger extends InteraxTelephonyLogger{
	protected IpPbxCallManager ipPbxdCallManager;

	public IpPbxCallManagerLogger(IpPbxCallManager ipPbxdCallManager, String path) {
		super(IpPbxCallManager.class, path);
		this.ipPbxdCallManager = ipPbxdCallManager;
	}

	protected String getPreamble() {
		return ipPbxdCallManager.getChildrenCdrId() + "|";
	}

}
