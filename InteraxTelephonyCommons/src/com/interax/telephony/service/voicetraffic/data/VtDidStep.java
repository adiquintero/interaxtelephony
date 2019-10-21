package com.interax.telephony.service.voicetraffic.data;

import java.io.Serializable;


public class VtDidStep implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long didId;
	private int action;
	private String data;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDidId() {
		return didId;
	}
	public void setDidId(long didId) {
		this.didId = didId;
	}
	public VtDidStepAction getAction() {
		return VtDidStepAction.values()[action];
	}
	public void setAction(VtDidStepAction action) {
		this.action = action.ordinal();
	}
	public void setAction(String action) {
		this.action = VtDidStepAction.valueOf(action).ordinal();
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}