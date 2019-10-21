package com.interax.telephony.service.conference.data;

import java.io.Serializable;
import java.util.Calendar;

import com.interax.telephony.service.data.ServiceDialStatus;

public class ConferenceStatObject implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userId;
	private int attempt;
	private Calendar joinDate;
	private Calendar leaveDate;
	private boolean invited;
	private int serviceDialStatus = ServiceDialStatus.NOANSWER.ordinal();
	private int leaveReason;
	

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getAttempt() {
		return attempt;
	}
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	
	public Calendar getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Calendar joinDate) {
		this.joinDate = joinDate;
	}

	public Calendar getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Calendar leaveDate) {
		this.leaveDate = leaveDate;
	}
	public boolean getInvited() {
		return invited;
	}
	public void setInvited(boolean invited) {
		this.invited = invited;
	}
	
	public ServiceDialStatus getServiceDialStatus() {
		return ServiceDialStatus.values()[this.serviceDialStatus];
	}

	public void setServiceDialStatus(ServiceDialStatus serviceDialStatus) {
		this.serviceDialStatus = serviceDialStatus.ordinal();
	}

	public void setServiceDialStatus(String serviceDialStatus) {
		this.serviceDialStatus = ServiceDialStatus.valueOf(serviceDialStatus).ordinal();
	}
	
	public LeaveReason getLeaveReason() {
		return LeaveReason.values()[this.leaveReason];
	}

	public void setLeaveReason(LeaveReason leaveReason) {
		this.leaveReason = leaveReason.ordinal();
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = LeaveReason.valueOf(leaveReason).ordinal();
	}

}