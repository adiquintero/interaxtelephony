package com.interax.telephony.service.ami.callingcard;
import com.interax.telephony.service.ami.InteraxTelephonyAmiServerLogger;


public class CallingCardAmiLogger extends InteraxTelephonyAmiServerLogger {
	CallingCardAmi callingCardAmi;
	public CallingCardAmiLogger(CallingCardAmi callingCardAmi, String path) {
		super(CallingCardAmi.class, path);
		this.callingCardAmi = callingCardAmi;
	}
	
	protected String getPreamble(){
		//return this.callingCardAmi.getChannelId()==null ? "" : this.callingCardAmi.getChannelId() + "|";
		return "";
	}
	
}
