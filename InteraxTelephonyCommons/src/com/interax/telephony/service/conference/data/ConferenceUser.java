package com.interax.telephony.service.conference.data;

import java.io.Serializable;

public class ConferenceUser implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userId;
	private int attempt;
	private boolean muted;
	private boolean deaf;
	private String channel;
	private int userNumber;
	
	public int getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
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
	public boolean isMuted() {
		return muted;
	}
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
	public boolean isDeaf() {
		return deaf;
	}
	public void setDeaf(boolean deaf) {
		this.deaf = deaf;
	}

}