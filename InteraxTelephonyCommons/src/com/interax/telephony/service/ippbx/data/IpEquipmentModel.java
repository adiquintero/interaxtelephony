package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;


public class IpEquipmentModel implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long equipmentManufacturerId;
	private String productNumber;
	private String name;
	
	
	public long getEquipmentManufacturerId() {
		return equipmentManufacturerId;
	}
	public void setEquipmentManufacturerId(long equipmentManufacturerId) {
		this.equipmentManufacturerId = equipmentManufacturerId;
	}
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
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
		
	
	
	
}