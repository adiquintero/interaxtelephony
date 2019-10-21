package com.interax.telephony.service.data;

import java.util.Calendar;

public class ItDidTest {

	private long id;
	private String externalNumber;
	private Calendar requestDate;
	private long requestNumber;

	public String getExternalNumber() {
		return externalNumber;
	}
	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Calendar getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Calendar requestDate) {
		this.requestDate = requestDate;
	}
	public long getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}
	
}
