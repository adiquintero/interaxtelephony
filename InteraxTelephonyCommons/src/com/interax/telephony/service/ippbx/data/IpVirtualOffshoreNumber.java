package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;


public class IpVirtualOffshoreNumber implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long ipPbxId;
	private long didId;
	private String language;
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIpPbxId() {
		return ipPbxId;
	}
	public void setIpPbxId(long ipPbxId) {
		this.ipPbxId = ipPbxId;
	}
	public long getDidId() {
		return didId;
	}
	public void setDidId(long didId) {
		this.didId = didId;
	}

}