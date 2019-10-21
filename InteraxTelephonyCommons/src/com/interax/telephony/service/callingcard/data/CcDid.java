package com.interax.telephony.service.callingcard.data;

import java.io.Serializable;
import java.util.List;
//import java.util.Vector;


public class CcDid implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String externalNumber;
	private boolean monitored;
	private int type;
	private int serviceType;
	private int accessType;
	private long foreignId;

	
	List<CcDidStep> steps;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExternalNumber() {
		return externalNumber;
	}
	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}
	
	public void setMonitored(boolean monitored){
		this.monitored = monitored;
	}
	public boolean isMonitored(){
		return monitored;
	}

	public CcDidType getType() {
		return CcDidType.values()[type];
	}
	public void setType(CcDidType type) {
		this.type = type.ordinal();
	}
	public void setType(String type) {
		this.type = CcDidType.valueOf(type).ordinal();
	}
	
	public CallingCardAccessType getAccessType() {
		return CallingCardAccessType.values()[accessType];
	}
	public void setAccessType(CallingCardAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = CallingCardAccessType.valueOf(accessType).ordinal();
	}
	
	public CallingCardServiceType getServiceType() {
		return CallingCardServiceType.values()[serviceType];
	}
	public void setServiceType(CallingCardServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
	}
	public void setServiceType(String serviceType) {
		this.serviceType = CallingCardServiceType.valueOf(serviceType).ordinal();
	}
	
	public void setForeignId(Long foreignId){
		this.foreignId = foreignId;
	}
	
	public Long getForeignId(){
		return foreignId;
	}
	
	
	public List<CcDidStep> getSteps() {
		return steps;
	}
	public void setSteps(List<CcDidStep> steps) {
		this.steps = steps;
	}
	


	
}