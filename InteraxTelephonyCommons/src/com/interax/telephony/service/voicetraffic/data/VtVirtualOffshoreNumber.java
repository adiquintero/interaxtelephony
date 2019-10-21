package com.interax.telephony.service.voicetraffic.data;

import java.io.Serializable;


public class VtVirtualOffshoreNumber implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long customerId;
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
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getDidId() {
		return didId;
	}
	public void setDidId(long didId) {
		this.didId = didId;
	}

}