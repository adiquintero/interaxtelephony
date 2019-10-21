package com.interax.telephony.service.data;

import java.io.Serializable;


public class RtMeetMe implements Serializable{
	private static final long serialVersionUID = 1L;

	private String confno;
	private String pin;
	private String adminpin;
	private int members;
	

	public String getConfno() {
		return confno;
	}
	public void setConfno(String confno) {
		this.confno = confno;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getAdminPin() {
		return adminpin;
	}
	public void setAdminPin(String adminpin) {
		this.adminpin = adminpin;
	}

	public int getmembers() {
		return members;
	}
	public void setmembers(int members) {
		this.members = members;
	}
}