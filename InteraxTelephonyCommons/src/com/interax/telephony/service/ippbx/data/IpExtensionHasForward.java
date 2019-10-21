package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;

public class IpExtensionHasForward  implements Serializable{

	private long extensionId ;
	private int forwardType;
	private String number;
	
	
	public long getExtensionId() {
		return extensionId;
	}
	
	public void setExtensionId(long extensionId) {
		this.extensionId = extensionId;
	}
	
	public IpExtensionForwardType getForwardType() {
		return IpExtensionForwardType.values()[forwardType];
	}
	
	public void setForwardType(IpExtensionForwardType forwardType) {
		this.forwardType = forwardType.ordinal();
	}
	
	public void setForwardType(String forwardType) {
		this.forwardType = IpExtensionForwardType.valueOf(forwardType).ordinal();
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
