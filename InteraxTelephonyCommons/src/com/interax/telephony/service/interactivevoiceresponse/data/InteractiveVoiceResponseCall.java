package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class InteractiveVoiceResponseCall implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String ani;
	private Long dnidId;
	private String callStatus;
	private Timestamp beginingDate;
	private Timestamp endingDate;
	private String language;
	

	public InteractiveVoiceResponseCallStatus getCallStatus() {
		return InteractiveVoiceResponseCallStatus.valueOf(callStatus);
	}
	public void setCallStatus(InteractiveVoiceResponseCallStatus callStatus) {
		this.callStatus = callStatus.name();
	}
	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getAni() {
		return ani;
	}
	public void setDnidId(Long dnidId) {
		this.dnidId = dnidId;
	}
	public Long getDnidId() {
		return dnidId;
	}
	public void setBeginingDate(Timestamp beginingDate) {
		this.beginingDate = beginingDate;
	}
	public Timestamp getBeginingDate() {
		return beginingDate;
	}
	public void setEndingDate(Timestamp endingDate) {
		this.endingDate = endingDate;
	}
	public Timestamp getEndingDate() {
		return endingDate;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLanguage() {
		return language;
	}
	
}