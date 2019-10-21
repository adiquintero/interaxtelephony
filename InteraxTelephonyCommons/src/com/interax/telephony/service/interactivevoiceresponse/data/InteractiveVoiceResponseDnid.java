package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;

public class InteractiveVoiceResponseDnid implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String dnid;
	
	
	public void setDnid(String dnid) {
		this.dnid = dnid;
	}
	public String getDnid() {
		return dnid;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	
	
}