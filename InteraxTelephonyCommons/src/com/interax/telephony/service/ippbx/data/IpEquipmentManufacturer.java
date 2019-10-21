package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;


public class IpEquipmentManufacturer implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private int ipEquipmentManufacturerInternalName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	//WORKAROUND
	
	public IpEquipmentManufacturerInternalName getInternalName() {
		return IpEquipmentManufacturerInternalName.values()[ipEquipmentManufacturerInternalName];
	}
	public void setInternalName(IpEquipmentManufacturerInternalName ipEquipmentManufacturerInternalName) {
		this.ipEquipmentManufacturerInternalName = ipEquipmentManufacturerInternalName.ordinal();
	}
	
	public void setInternalName(String ipEquipmentManufacturerInternalName) {
		this.ipEquipmentManufacturerInternalName = IpEquipmentManufacturerInternalName.valueOf(ipEquipmentManufacturerInternalName).ordinal();
	}
	
	
}