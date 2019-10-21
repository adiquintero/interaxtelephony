package com.interax.telephony.service.callingcard.data;

import java.io.Serializable;


public class CcDidStep implements Serializable{
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
	public CcDidStepAction getAction() {
		return CcDidStepAction.values()[action];
	}
	public void setAction(CcDidStepAction action) {
		this.action = action.ordinal();
	}
	public void setAction(String action) {
		this.action = CcDidStepAction.valueOf(action).ordinal();
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}