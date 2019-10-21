package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;


public class IpEquipment implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long equipmentModelId;
	private String serialNumber;
	private String macAddress;
	
	public long getEquipmentModelId() {
		return equipmentModelId;
	}
	public void setEquipmentModelId(long equipmentModelId) {
		this.equipmentModelId = equipmentModelId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	
	
}