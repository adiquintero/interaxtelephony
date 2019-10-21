package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;
import java.util.Calendar;


public class IpEquipmentActivationAttempt implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long equipmentId;
	private Calendar attemptDate;
	private String activationCode;
	private boolean success;
	private long extensionId;
	private String ipAddress;
	
	
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public Calendar getAttemptDate() {
		return attemptDate;
	}
	public void setAttemptDate(Calendar attemptDate) {
		this.attemptDate = attemptDate;
	}
	public long getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(long equipmentId) {
		this.equipmentId = equipmentId;
	}
	public long getExtensionId() {
		return extensionId;
	}
	public void setExtensionId(long extensionId) {
		this.extensionId = extensionId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
}