package com.interax.telephony.service.ami.pickdialing;

import com.interax.telephony.service.ami.InteraxTelephonyAmiServerLogger;

public class PickDialingAmiLogger extends InteraxTelephonyAmiServerLogger {
	
	PickDialingAmi pickDialingAmi;
	
	public PickDialingAmiLogger(PickDialingAmi pickDialingAmi, String path) {
		super(PickDialingAmi.class, path);
		this.pickDialingAmi = pickDialingAmi;
	}
	
	protected String getPreamble(){
		//return this.pickDialingAmi.getChannelId() == null ? "" : this.pickDialingAmi.getChannelId() + "|";
		return "";
	}
	
}
