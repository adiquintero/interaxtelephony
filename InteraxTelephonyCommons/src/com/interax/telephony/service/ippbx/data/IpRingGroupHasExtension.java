package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;


public class IpRingGroupHasExtension implements Serializable{
	private static final long serialVersionUID = 1L;

	private long ringGroupId;
	private long extensionId;
	
	
	public long getExtensionid() {
		return extensionId;
	}
	public void setExtensionid(long extensionid) {
		this.extensionId = extensionid;
	}
	public long getRingGroupId() {
		return ringGroupId;
	}
	public void setRingGroupId(long ringGroupId) {
		this.ringGroupId = ringGroupId;
	}



}