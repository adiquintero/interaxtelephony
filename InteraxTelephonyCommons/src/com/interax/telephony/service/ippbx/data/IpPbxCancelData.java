package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;
import java.util.Calendar;

import com.interax.telephony.service.data.ServiceDialStatus;
import com.interax.telephony.service.data.ServiceCancelData;

public class IpPbxCancelData extends ServiceCancelData implements Serializable, IpPbxData {
	
	private static final long serialVersionUID = 1L;
	private Calendar startTime;
	private Calendar stopTime;
	private Integer callDuration;
	private int dialStatus;
	private String cdrId;
	private int hangupCause;
	
	private int serviceType;
	private int accessType;
	private boolean dirty;
	
	
	public String getCdrId() {
		return cdrId;
	}
	public void setCdrId(String cdrId) {
		this.cdrId = cdrId;
	}
	public Calendar getStartTime() {
		return startTime;
	}
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	public ServiceDialStatus getDialStatus() {
		return ServiceDialStatus.values()[dialStatus];
	}
	public void setDialStatus(ServiceDialStatus dialStatus) {
		this.dialStatus = dialStatus.ordinal();
	}
	public void setDialStatus(String dialStatus) {
		this.dialStatus = ServiceDialStatus.valueOf(dialStatus).ordinal();
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
	public Integer getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(Integer callDuration) {
		this.callDuration = callDuration;
	}
	public Calendar getStopTime() {
		return stopTime;
	}
	public void setStopTime(Calendar stopTime) {
		this.stopTime = stopTime;
	}
	public void setHangupCause(int hangupCause) {
		this.hangupCause = hangupCause;
	}
	public int getHangupCause() {
		return hangupCause;
	}
	public boolean getDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	@Override
	public String toString() {
		return "IpPbxCancelData{startTime="+this.startTime.getTime()+",stopTime="+this.stopTime.getTime()+",callDuration="+this.callDuration+",dialStatus="+this.dialStatus+",cdrId="+this.cdrId+",serviceType="+this.serviceType+",accessType="+this.accessType+",hangupCause="+this.hangupCause+"}";
	}

}
