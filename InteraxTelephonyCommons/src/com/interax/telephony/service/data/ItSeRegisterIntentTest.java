package com.interax.telephony.service.data;

import java.sql.Date;

public class ItSeRegisterIntentTest {
	
	private long id;
	private String ip;
	private String userName;
	private Date runDate;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
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
