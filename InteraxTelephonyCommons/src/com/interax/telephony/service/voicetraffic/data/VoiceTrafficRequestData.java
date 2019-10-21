package com.interax.telephony.service.voicetraffic.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ServiceRequestData;

public class VoiceTrafficRequestData extends ServiceRequestData implements Serializable, VoiceTrafficData {
	
	private static final long serialVersionUID = 1L;
	private String ani;
	private String dni;
	private String dnid;
	private String cdrId;
	private long peerId;
	private long customerId;
	
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
	public VoiceTrafficAccessType getAccessType() {
		return VoiceTrafficAccessType.values()[this.accessType];
	}
	public VoiceTrafficServiceType getServiceType() {
		return VoiceTrafficServiceType.values()[this.serviceType];
	}
	public void setAccessType(VoiceTrafficAccessType accessType) {
		this.accessType = accessType.ordinal();
		
	}
	public void setAccessType(String accessType) {
		this.accessType = VoiceTrafficAccessType.valueOf(accessType).ordinal();
		
	}
	public void setServiceType(VoiceTrafficServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
		
	}
	public void setServiceType(String serviceType) {
		this.serviceType = VoiceTrafficServiceType.valueOf(serviceType).ordinal();
		
	}
	public long getPeerId() {
		return peerId;
	}
	public void setPeerId(long peerId) {
		this.peerId = peerId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	@Override
	public String toString() {
		return "VoiceTrafficRequestData{ani="+this.ani+",dni="+this.dni+",cdrId="+this.cdrId+",peerId="+this.peerId+",customerId="+this.customerId+",serviceType="+this.serviceType+",accessType="+this.accessType+"}";
	}
}
