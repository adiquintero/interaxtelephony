package com.interax.telephony.service.did.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ServiceRequestData;
import com.interax.telephony.service.did.data.DidAccessType;
import com.interax.telephony.service.did.data.DidData;
import com.interax.telephony.service.did.data.DidServiceType;

public class DidRequestData extends ServiceRequestData implements Serializable, DidData {
	
	private static final long serialVersionUID = 1L;
	private String ani;
	private String dni;
	private String dnid;
	private String cdrId;
	private long didId;
	private long didService;
	
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
	public DidAccessType getAccessType() {
		return DidAccessType.values()[this.accessType];
	}
	public DidServiceType getServiceType() {
		return DidServiceType.values()[this.serviceType];
	}
	public void setAccessType(DidAccessType accessType) {
		this.accessType = accessType.ordinal();
		
	}
	public void setAccessType(String accessType) {
		this.accessType = DidAccessType.valueOf(accessType).ordinal();
		
	}
	public void setServiceType(DidServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
		
	}
	public void setServiceType(String serviceType) {
		this.serviceType = DidServiceType.valueOf(serviceType).ordinal();
		
	}
	public long getDidId() {
		return didId;
	}
	public void setDidId(long didId) {
		this.didId = didId;
	}
	
	public long getDidService() {
		return didService;
	}
	public void setDidService(long didService) {
		this.didService = didService;
	}
	
	@Override
	public String toString() {
		return "DidRequestData{ani="+this.ani+",dni="+this.dni+",cdrId="+this.cdrId+",didId="+this.didId+",didService="+this.didService+",serviceType="+this.serviceType+",accessType="+this.accessType+"}";
	}

}
