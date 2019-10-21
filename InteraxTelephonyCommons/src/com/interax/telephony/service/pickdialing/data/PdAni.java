package com.interax.telephony.service.pickdialing.data;

import java.io.Serializable;


public class PdAni implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long customerId;
	
	private String externalNumber;
	
	private boolean monitored;
	
	private String language;
	
	private int serviceType;
	
	private int accessType;
	
	private boolean ldn;
	
	private boolean ldi;
	
	private String ldnPrefix;
	
	private String ldiPrefix; 
	
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
	public PickDialingAccessType getAccessType() {
		return PickDialingAccessType.values()[accessType];
	}
	public void setAccessType(PickDialingAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = PickDialingAccessType.valueOf(accessType).ordinal();
	}
	
	public PickDialingServiceType getServiceType() {
		return PickDialingServiceType.values()[serviceType];
	}
	public void setServiceType(PickDialingServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
	}
	public void setServiceType(String serviceType) {
		this.serviceType = PickDialingServiceType.valueOf(serviceType).ordinal();
	}
	public boolean isLdn() {
		return ldn;
	}
	public void setLdn(boolean ldn) {
		this.ldn = ldn;
	}
	public boolean isLdi() {
		return ldi;
	}
	public void setLdi(boolean ldi) {
		this.ldi = ldi;
	}
	public String getLdnPrefix() {
		return ldnPrefix;
	}
	public void setLdnPrefix(String ldnPrefix) {
		this.ldnPrefix = ldnPrefix;
	}
	public String getLdiPrefix() {
		return ldiPrefix;
	}
	public void setLdiPrefix(String ldiPrefix) {
		this.ldiPrefix = ldiPrefix;
	}
	
	
}