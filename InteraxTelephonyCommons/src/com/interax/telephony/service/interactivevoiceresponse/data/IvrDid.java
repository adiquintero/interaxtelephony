package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;
import java.util.List;
//import java.util.Vector;

public class IvrDid implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String externalNumber;
	private boolean monitored;
//	private String configName;
	private String language;
	private int serviceType;
	private int accessType;
	private long foreignId;
	
	
	List<IvrDidStep> steps;
	
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

//	public String getConfigName() {
//		return configName;
//	}
//	public void setConfigName(String configName) {
//		this.configName = configName;
//	}
	
	public void setMonitored(boolean monitored){
		this.monitored = monitored;
	}
	public boolean isMonitored(){
		return monitored;
	}
	public InteractiveVoiceResponseAccessType getAccessType() {
		return InteractiveVoiceResponseAccessType.values()[accessType];
	}
	public void setAccessType(InteractiveVoiceResponseAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = InteractiveVoiceResponseAccessType.valueOf(accessType).ordinal();
	}
	
	public InteractiveVoiceResponseServiceType getServiceType() {
		return InteractiveVoiceResponseServiceType.values()[serviceType];
	}
	public void setServiceType(InteractiveVoiceResponseServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
	}
	public void setServiceType(String serviceType) {
		this.serviceType = InteractiveVoiceResponseServiceType.valueOf(serviceType).ordinal();
	}
	
	public void setForeignId(long foreignId){
		this.foreignId = foreignId;
	}
	
	public long getForeignId(){
		return foreignId;
	}
	
	public List<IvrDidStep> getSteps() {
		return steps;
	}
	public void setSteps(List<IvrDidStep> steps) {
		this.steps = steps;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}


	

}
