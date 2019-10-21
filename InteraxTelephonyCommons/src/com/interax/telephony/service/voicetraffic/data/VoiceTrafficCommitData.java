package com.interax.telephony.service.voicetraffic.data;

import java.io.Serializable;
import java.util.Calendar;

import com.interax.telephony.service.data.ServiceDialStatus;
import com.interax.telephony.service.data.ServiceCommitData;

public class VoiceTrafficCommitData extends ServiceCommitData implements Serializable, VoiceTrafficData {
	
	private static final long serialVersionUID = 1L;
	private Calendar startTime;
	private Calendar answerTime;
	private Calendar stopTime;
	private Integer callDuration;
	private Integer billSeconds;
	private int dialStatus;
	private String cdrId;
	private int hangupCause;
	
	private int serviceType;
	private int accessType;
	
	public Integer getBillSeconds() {
		return billSeconds;
	}
	public void setBillSeconds(Integer billSeconds) {
		this.billSeconds = billSeconds;
	}
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
	public Calendar getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Calendar answerTime) {
		this.answerTime = answerTime;
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
	public ServiceDialStatus getDialStatus() {
		return ServiceDialStatus.values()[dialStatus];
	}
	public void setDialStatus(ServiceDialStatus dialStatus) {
		this.dialStatus = dialStatus.ordinal();
	}
	public void setDialStatus(String dialStatus) {
		this.dialStatus = ServiceDialStatus.valueOf(dialStatus).ordinal();
	}
	public void setHangupCause(int hangupCause) {
		this.hangupCause = hangupCause;
	}
	public int getHangupCause() {
		return hangupCause;
	}
	
	@Override
	public String toString() {
		return "VoiceTrafficCommitData{startTime="+this.startTime.getTime()+",answerTime="+answerTime.getTime()+",stopTime="+this.stopTime.getTime()+",callDuration="+this.callDuration+",billSeconds="+this.billSeconds+",dialStatus="+this.dialStatus+",cdrId="+this.cdrId+",serviceType="+this.serviceType+",accessType="+this.accessType+",hangupCause="+this.hangupCause+"}";
	}

}
