package com.interax.telephony.service.callingcard.data;

import java.io.Serializable;


public class CallingCardPinQuota implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
    private String name;
    private int initQuantity;
    private int currentQuantity;
    private int callingCardQuotaUnit;
	
    
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
	public int getInitQuantity() {
		return initQuantity;
	}
	public void setInitQuantity(int initQuantity) {
		this.initQuantity = initQuantity;
	}
	public int getCurrentQuantity() {
		return currentQuantity;
	}
	public void setCurrentQuantity(int currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public CallingCardQuotaUnit getCallingCardQuotaUnit() {
		return CallingCardQuotaUnit.values() [this.callingCardQuotaUnit];
	}
	
	public void setCallingCardQuotaUnit(CallingCardQuotaUnit callingCardQuotaUnit) {
		this.callingCardQuotaUnit = callingCardQuotaUnit.ordinal();
	}
    
    
    
    
	
}
