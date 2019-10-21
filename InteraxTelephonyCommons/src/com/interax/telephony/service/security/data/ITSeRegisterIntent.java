package com.interax.telephony.service.security.data;

import java.io.Serializable;
import java.util.Calendar;


public class ITSeRegisterIntent implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String ip;
	private Calendar runDate;
	private long recordNumber;
	private long lastRecordNumber;
	
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Calendar getRunDate() {
		return runDate;
	}

	public void setRunDate(Calendar runDate) {
		this.runDate = runDate;
	}

	public long getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(long recordNumber) {
		this.recordNumber = recordNumber;
	}

	public long getLastRecordNumber() {
		return lastRecordNumber;
	}

	public void setLastRecordNumber(long lastRecordNumber) {
		this.lastRecordNumber = lastRecordNumber;
	}





}
