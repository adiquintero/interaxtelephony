package com.interax.telephony.service.security.data;

import java.io.Serializable;
import java.util.Calendar;

public class ITSeRegisterDetails implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String userName;
	private String secret;
	private String srcIpAdress;
	private Calendar date;
	private String status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getSrcIpAdress() {
		return srcIpAdress;
	}
	public void setSrcIpAdress(String srcIpAdress) {
		this.srcIpAdress = srcIpAdress;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	

}
