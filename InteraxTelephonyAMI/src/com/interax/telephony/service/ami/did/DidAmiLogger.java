package com.interax.telephony.service.ami.did;

import com.interax.telephony.service.ami.InteraxTelephonyAmiServerLogger;
import com.interax.telephony.service.ami.did.DidAmi;

public class DidAmiLogger extends InteraxTelephonyAmiServerLogger {
	
	DidAmi didAmi;
	
	public DidAmiLogger(DidAmi didAmi, String path) {
		super(DidAmi.class, path);
		this.didAmi = didAmi;
	}
	
	protected String getPreamble(){
		//return this.didAmi.getChannelId() == null ? "" : this.didAmi.getChannelId() + "|";
		return "";
	}

}
