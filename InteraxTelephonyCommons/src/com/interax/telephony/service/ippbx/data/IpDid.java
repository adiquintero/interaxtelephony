package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;
import java.util.List;
//import java.util.Vector;


public class IpDid implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long ipPbxId;
	private String externalNumber;
	private boolean monitored;
	private int type;
	private String language;
	private int serviceType;
	private int accessType;
	private long foreignId;
	
		
	List<IpDidStep> steps; //TODO Change to LIST
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIpPbxId() {
		return ipPbxId;
	}
	public void setIpPbxId(long ipPbxId) {
		this.ipPbxId = ipPbxId;
	}
	public String getExternalNumber() {
		return externalNumber;
	}
	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}

	public IpDidType getType() {
		return IpDidType.values()[type];
	}
	public void setType(IpDidType type) {
		this.type = type.ordinal();
	}
	public void setType(String type) {
		this.type = IpDidType.valueOf(type).ordinal();
	}
	
	public List<IpDidStep> getSteps() {
		return steps;
	}
	public void setSteps(List<IpDidStep> steps) {
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
	public IpPbxAccessType getAccessType() {
		return IpPbxAccessType.values()[accessType];
	}
	public void setAccessType(IpPbxAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = IpPbxAccessType.valueOf(accessType).ordinal();
	}
	
	public IpPbxServiceType getServiceType() {
		return IpPbxServiceType.values()[serviceType];
	}
	public void setServiceType(IpPbxServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
	}
	public void setServiceType(String serviceType) {
		this.serviceType = IpPbxServiceType.valueOf(serviceType).ordinal();
	}
	
	public void setForeignId(long foreingId){
		this.foreignId = foreingId;
	}
	
	public long getForeignId(){
		return foreignId;
	}

	
}