package com.interax.telephony.service.did.data;

import java.io.Serializable;

public class DidDid implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long didServiceId;
	private String externalNumber;
	private boolean monitored;
	private long foreignId;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getDidServiceId() {
		return didServiceId;
	}
	
	public void setDidServiceId(long didServiceId) {
		this.didServiceId = didServiceId;
	}
	
	public String getExternalNumber() {
		return externalNumber;
	}
	
	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}

	public boolean isMonitored() {
		return monitored;
	}
	
	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}
	
	public void setForeignId(long foreingId){
		this.foreignId = foreingId;
	}
	
	public long getForeignId(){
		return foreignId;
	}

}