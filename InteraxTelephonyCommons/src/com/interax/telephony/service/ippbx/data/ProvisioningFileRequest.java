package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;

import sun.util.calendar.BaseCalendar.Date;


public class ProvisioningFileRequest implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String fileName;
	private Date requestDate;
	private String requestUserAgent;
	private String requestAddress;
	private String response;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestUserAgent() {
		return requestUserAgent;
	}
	public void setRequestUserAgent(String requestUserAgent) {
		this.requestUserAgent = requestUserAgent;
	}
	public String getRequestAddress() {
		return requestAddress;
	}
	public void setRequestAddress(String requestAddress) {
		this.requestAddress = requestAddress;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}	
}