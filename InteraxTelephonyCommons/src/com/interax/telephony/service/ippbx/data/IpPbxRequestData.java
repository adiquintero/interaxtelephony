package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ServiceRequestData;

public class IpPbxRequestData extends ServiceRequestData implements Serializable, IpPbxData {
	
	private static final long serialVersionUID = 1L;
	private String ani;
	private String dni;
	private String dnid;
	private String cdrId;
	private long pbxId;
	private long extensionId;
	
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
	public String getCdrId() {
		return cdrId;
	}
	public void setCdrId(String cdrId) {
		this.cdrId = cdrId;
	}
	
	public IpPbxAccessType getAccessType() {
		return IpPbxAccessType.values()[this.accessType];
	}
	public IpPbxServiceType getServiceType() {
		return IpPbxServiceType.values()[this.serviceType];
	}
	public void setAccessType(IpPbxAccessType accessType) {
		this.accessType = accessType.ordinal();
		
	}
	public void setAccessType(String accessType) {
		this.accessType = IpPbxAccessType.valueOf(accessType).ordinal();
		
	}
	public void setServiceType(IpPbxServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
		
	}
	public void setServiceType(String serviceType) {
		this.serviceType = IpPbxServiceType.valueOf(serviceType).ordinal();
		
	}
	public long getExtensionId() {
		return extensionId;
	}
	public void setExtensionId(long extensionId) {
		this.extensionId = extensionId;
	}
	public long getPbxId() {
		return pbxId;
	}
	public void setPbxId(long pbxId) {
		this.pbxId = pbxId;
	}
	public String getDnid() {
		return dnid;
	}
	public void setDnid(String dnid) {
		this.dnid = dnid;
	}
	
	@Override
	public String toString() {
		return "IpPbxRequestData{ani="+this.ani+",dni="+this.dni+",cdrId="+this.cdrId+",pbxId="+this.pbxId+",extensionId="+this.extensionId+",serviceType="+this.serviceType+",accessType="+this.accessType+"}";
	}

}