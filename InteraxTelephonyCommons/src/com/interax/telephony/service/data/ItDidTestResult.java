package com.interax.telephony.service.data;

import java.util.Calendar;

public class ItDidTestResult {

	private long id;
	private String externalNumber;
	private Calendar requestDate;
	private long requestNumber;
	private Calendar testDate;
	private boolean didAnswered;
	private boolean serverAnswered;
	private boolean receiveDtmf;
	private boolean sendDtmf;
	private String metaData;
	
	
	public boolean getDidAnswered() {
		return didAnswered;
	}
	public void setDidAnswered(boolean didAnswered) {
		this.didAnswered = didAnswered;
	}
	public String getExternalNumber() {
		return externalNumber;
	}
	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMetaData() {
		return metaData;
	}
	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}
	public boolean getReceiveDtmf() {
		return receiveDtmf;
	}
	public void setReceiveDtmf(boolean receiveDtmf) {
		this.receiveDtmf = receiveDtmf;
	}
	public Calendar getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Calendar requestDate) {
		this.requestDate = requestDate;
	}
	public long getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}
	public boolean getSendDtmf() {
		return sendDtmf;
	}
	public void setSendDtmf(boolean sendDtmf) {
		this.sendDtmf = sendDtmf;
	}
	public boolean getServerAnswered() {
		return serverAnswered;
	}
	public void setServerAnswered(boolean serverAnswered) {
		this.serverAnswered = serverAnswered;
	}
	public Calendar getTestDate() {
		return testDate;
	}
	public void setTestDate(Calendar testDate) {
		this.testDate = testDate;
	}

	
	
}
