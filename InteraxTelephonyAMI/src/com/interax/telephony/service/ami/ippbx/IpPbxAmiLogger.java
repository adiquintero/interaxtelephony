package com.interax.telephony.service.ami.ippbx;
import com.interax.telephony.service.ami.InteraxTelephonyAmiServerLogger;


public class IpPbxAmiLogger extends InteraxTelephonyAmiServerLogger {
	
	IpPbxAmi ipPbxAmi;
	
	public IpPbxAmiLogger(IpPbxAmi ipPbxAmi, String path) {
		super(IpPbxAmi.class, path);
		this.ipPbxAmi = ipPbxAmi;
	}
	
	protected String getPreamble(){
		//return this.ipPbxAmi.getChannelId() == null ? "" : this.ipPbxAmi.getChannelId() + "|";
		return "";
	}
	
}
