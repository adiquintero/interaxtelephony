package com.interax.telephony.service.did.data;

import java.io.Serializable;
import java.util.Calendar;

import com.interax.telephony.service.data.ServiceCancelData;
import com.interax.telephony.service.data.ServiceDialStatus;
import com.interax.telephony.service.did.data.DidAccessType;
import com.interax.telephony.service.did.data.DidData;
import com.interax.telephony.service.did.data.DidServiceType;

public class DidCancelData extends ServiceCancelData implements Serializable, DidData {
	
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
		return "DidCancelData{startTime="+this.startTime.getTime()+",stopTime="+this.stopTime.getTime()+",callDuration="+this.callDuration+",dialStatus="+this.dialStatus+",cdrId="+this.cdrId+",serviceType="+this.serviceType+",accessType="+this.accessType+",hangupCause="+this.hangupCause+"}";
	}

}
