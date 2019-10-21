package com.interax.telephony.service.callingcard.data;

import java.io.Serializable;
import java.util.Calendar;

import com.interax.telephony.service.data.ServiceCancelData;
import com.interax.telephony.service.data.ServiceDialStatus;

public class CallingCardCancelData extends ServiceCancelData implements Serializable, CallingCardData {
	
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

	public CallingCardAccessType getAccessType() {
		return CallingCardAccessType.values()[this.accessType];
	}
	public void setAccessType(CallingCardAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = CallingCardAccessType.valueOf(accessType).ordinal();
	}

	public CallingCardServiceType getServiceType() {
		return CallingCardServiceType.values()[this.serviceType];
	}
	public void setServiceType(CallingCardServiceType serviceType) {
		this.serviceType = serviceType.ordinal();
	}
	public void setServiceType(String serviceType) {
		this.serviceType = CallingCardServiceType.valueOf(serviceType).ordinal();
	}
	
	public int getHangupCause() {
		return hangupCause;
	}
	public void setHangupCause(int hangupCause) {
		this.hangupCause = hangupCause;
	}
	public boolean getDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	
	
}
