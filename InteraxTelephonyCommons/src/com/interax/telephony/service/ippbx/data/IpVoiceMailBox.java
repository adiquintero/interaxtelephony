package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;


public class IpVoiceMailBox implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected long id;
	private long ipPbxId;
	private String password;
	private String email;
	private String name;

	public IpVoiceMailBox() {
		super();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
}