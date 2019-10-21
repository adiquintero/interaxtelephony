package com.interax.telephony.service.data;

import java.io.Serializable;
import java.util.Date;


public class ItSeIpBlackList implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String ip;
	private long recordNumber;
	private String status;
	private Date runDate;
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
	public long getRecordNumber() {
		return recordNumber;
	}
	public void setRecordNumber(long recordNumber) {
		this.recordNumber = recordNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getRunDate() {
		return runDate;
	}
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


}
