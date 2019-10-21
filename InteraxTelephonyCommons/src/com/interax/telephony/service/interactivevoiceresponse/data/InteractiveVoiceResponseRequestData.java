package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ServiceRequestData;

public class InteractiveVoiceResponseRequestData extends ServiceRequestData implements Serializable, InteractiveVoiceResponseData {
	
	private static final long serialVersionUID = 1L;
	private String ani;
	private String dni;
	private String dnid;
	private String cdrId;
	private long ivrId;
	
	private int serviceType;
	private int accessType;
	
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getDnid() {
		return dnid;
	}
	public void setDnid(String dnid) {
		this.dnid = dnid;
	}
	public void setCdrId(String cdrId) {
		this.cdrId = cdrId;
	}
	public String getCdrId() {
		return cdrId;
	}
	public InteractiveVoiceResponseAccessType getAccessType() {
		return InteractiveVoiceResponseAccessType.values()[this.accessType];
	}
	public InteractiveVoiceResponseServiceType getServiceType() {
		return InteractiveVoiceResponseServiceType.values()[this.serviceType];
	}
	public void setAccessType(InteractiveVoiceResponseAccessType accessType) {
		this.accessType = accessType.ordinal();
		
	}
	public void setAccessType(String accessType) {
		this.accessType = InteractiveVoiceResponseAccessType.valueOf(accessType).ordinal();
		
	}
	public void setServiceType(InteractiveVoiceResponseServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
		
	}
	public void setServiceType(String serviceType) {
		this.serviceType = InteractiveVoiceResponseServiceType.valueOf(serviceType).ordinal();
		
	}
	
	public long getIvrId() {
		return ivrId;
	}
	public void setIvrId(long ivrId) {
		this.ivrId = ivrId;
	}
	
	@Override
	public String toString() {
		return "InteractiveVoiceResponseRequestData{ani="+this.ani+",dni="+this.dni+",cdrId="+this.cdrId+",ivrId="+this.ivrId+",serviceType="+this.serviceType+",accessType="+this.accessType+"}";
	}
}
