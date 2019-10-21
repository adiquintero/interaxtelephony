package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.Vector;


public class IpRingGroup implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long ipPbxId;
	private String name;
	
	private List<Long> extensionIds;
	
	public IpRingGroup() {
		this.extensionIds = new ArrayList<Long>();
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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	
	public List<Long> getExtensionIds() {
		return extensionIds;
	}
	public void setExtensionIds(List<Long> extensionIds) {
		this.extensionIds = extensionIds;
	}


}