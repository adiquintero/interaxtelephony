package com.interax.telephony.service.voicetraffic.data;

import java.io.Serializable;
import java.util.List;
//import java.util.Vector;


public class VtDid implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long customerId;
	private String externalNumber;
	private boolean monitored;
	private int type; 
	private String language;
	private int serviceType;
	private int accessType;
	private long foreignId;
		
	List<VtDidStep> steps;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getExternalNumber() {
		return externalNumber;
	}
	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}
	


	public VtDidType getType() {
		return VtDidType.values()[type];
	}
	public void setType(VtDidType type) {
		this.type = type.ordinal();
	}
	public void setType(String type) {
		this.type = VtDidType.valueOf(type).ordinal();
	}
	
	public List<VtDidStep> getSteps() {
		return steps;
	}
	public void setSteps(List<VtDidStep> steps) {
		this.steps = steps;
	}
	public boolean isMonitored() {
		return monitored;
	}
	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
	public VoiceTrafficAccessType getAccessType() {
		return VoiceTrafficAccessType.values()[accessType];
	}
	public void setAccessType(VoiceTrafficAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = VoiceTrafficAccessType.valueOf(accessType).ordinal();
	}
	
	public VoiceTrafficServiceType getServiceType() {
		return VoiceTrafficServiceType.values()[serviceType];
	}
	public void setServiceType(VoiceTrafficServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
	}
	public void setServiceType(String serviceType) {
		this.serviceType = VoiceTrafficServiceType.valueOf(serviceType).ordinal();
	}
	
	public void setForeignId(long foreingId){
		this.foreignId = foreingId;
	}
	
	public long getForeignId(){
		return foreignId;
	}

	
}