package com.interax.telephony.service.ami;

import java.io.Serializable;
import java.util.Calendar;

import com.interax.telephony.service.data.ServiceFamily;

public class InteraxTelephonyManagedCall implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String incomingChannelId;
	private String incomingChannelName;
	private String incomingCdrId;

	private String outgoingChannelId;
	private String outgoingChannelName;
	private String outgoingCdrId;
	
	private String ani;
	private String dni;
	private String dnid;
	
	private Calendar startDate;
	private Calendar nextWakeupDate;
	private long enterpriseId;
	private boolean toHangupCall;
	private String childrenCdrId;

	private ServiceFamily serviceFamily;
	private long reservationId;

	//RATER_TEST_MODE:1
	private boolean raterTestMode = false;
	private int raterTestAvailablesSeconds = -1;

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
	public long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getIncomingCdrId() {
		return incomingCdrId;
	}
	public void setIncomingCdrId(String incomingCdrId) {
		this.incomingCdrId = incomingCdrId;
	}
	public String getIncomingChannelId() {
		return incomingChannelId;
	}
	public void setIncomingChannelId(String incomingChannelId) {
		this.incomingChannelId = incomingChannelId;
	}
	public String getIncomingChannelName() {
		return incomingChannelName;
	}
	public void setIncomingChannelName(String incomingChannelName) {
		this.incomingChannelName = incomingChannelName;
	}
	public Calendar getNextWakeupDate() {
		return nextWakeupDate;
	}
	public void setNextWakeupDate(Calendar nextWakeupDate) {
		this.nextWakeupDate = nextWakeupDate;
	}
	public String getOutgoingCdrId() {
		return outgoingCdrId;
	}
	public void setOutgoingCdrId(String outgoingCdrId) {
		this.outgoingCdrId = outgoingCdrId;
	}
	public String getOutgoingChannelId() {
		return outgoingChannelId;
	}
	public void setOutgoingChannelId(String outgoingChannelId) {
		this.outgoingChannelId = outgoingChannelId;
	}
	public String getOutgoingChannelName() {
		return outgoingChannelName;
	}
	public void setOutgoingChannelName(String outgoingChannelName) {
		this.outgoingChannelName = outgoingChannelName;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public boolean isToHangupCall() {
		return toHangupCall;
	}
	public void setToHangupCall(boolean toHangupCall) {
		this.toHangupCall = toHangupCall;
	}
	public ServiceFamily getServiceFamily() {
		return serviceFamily;
	}
	public void setServiceFamily(ServiceFamily serviceFamily) {
		this.serviceFamily = serviceFamily;
	}
	public void setServiceFamily(String serviceFamily) {
		this.serviceFamily = ServiceFamily.valueOf(serviceFamily);
	}

	public long getReservationId() {
		return reservationId;
	}
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	public int getRaterTestAvailablesSeconds() {
		return raterTestAvailablesSeconds;
	}
	public void setRaterTestAvailablesSeconds(int raterTestAvailablesSeconds) {
		this.raterTestAvailablesSeconds = raterTestAvailablesSeconds;
	}
	public boolean isRaterTestMode() {
		return raterTestMode;
	}
	public void setRaterTestMode(boolean raterTestMode) {
		this.raterTestMode = raterTestMode;
	}
	public String getChildrenCdrId() {
		return childrenCdrId;
	}
	public void setChildrenCdrId(String childrenCdrId) {
		this.childrenCdrId = childrenCdrId;
	}
	
	
	
}
