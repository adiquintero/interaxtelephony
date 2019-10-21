package com.interax.telephony.service.data;

import java.io.Serializable;

import com.interax.telephony.util.GeneralUtils;


public class CdrCallDetailRecord  implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String uniqueid;
	private long startdate;
	private String clid;
	private String src;
	private String dst;
	private String dcontext;
	private String channel;
	private String dstchannel;
	private String lastapp;
	private String lastdata;
	private long duration;
	private long billsec;
	private String disposition;
	private long amaflags;
	private String accountcode;
	private String userfield;
	private String asteriskId;
	private long answerDate;
	private long stopDate;
	private long hangupCause;
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	public long getStartdate() {
		return startdate;
	}
	public void setStartdate(long startdate) {
		this.startdate = startdate;
	}
	public String getClid() {
		return clid;
	}
	public void setClid(String clid) {
		this.clid = clid;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getDcontext() {
		return dcontext;
	}
	public void setDcontext(String dcontext) {
		this.dcontext = dcontext;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getDstchannel() {
		return dstchannel;
	}
	public void setDstchannel(String dstchannel) {
		this.dstchannel = dstchannel;
	}
	public String getLastapp() {
		return lastapp;
	}
	public void setLastapp(String lastapp) {
		this.lastapp = lastapp;
	}
	public String getLastdata() {
		return lastdata;
	}
	public void setLastdata(String lastdata) {
		this.lastdata = lastdata;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getBillsec() {
		return billsec;
	}
	public void setBillsec(long billsec) {
		this.billsec = billsec;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public long getAmaflags() {
		return amaflags;
	}
	public void setAmaflags(long amaflags) {
		this.amaflags = amaflags;
	}
	public String getAccountcode() {
		return accountcode;
	}
	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}
	public String getUserfield() {
		return userfield;
	}
	public void setUserfield(String userfield) {
		this.userfield = userfield;
	}
	public String getAsteriskId() {
		return asteriskId;
	}
	public void setAsteriskId(String asteriskId) {
		this.asteriskId = asteriskId;
	}
	public long getAnswerDate() {
		return answerDate;
	}
	public void setAnswerDate(long answerDate) {
		this.answerDate = answerDate;
	}
	public long getStopDate() {
		return stopDate;
	}
	public void setStopDate(long stopDate) {
		this.stopDate = stopDate;
	}
	public long getHangupCause() {
		return hangupCause;
	}
	public void setHangupCause(long hangupCause) {
		this.hangupCause = hangupCause;
	}
	
	
	public static void main(String[] args) {
		CdrCallDetailRecord cdr = new CdrCallDetailRecord();
		cdr.setAccountcode("IGNORE");

		System.out.println("---------------------------------------");
		System.out.println(cdr);
		System.out.println("---------------------------------------");
		String text = GeneralUtils.toText(cdr);
		System.out.println(text);
		System.out.println("---------------------------------------");
		@SuppressWarnings("unused")
		CdrCallDetailRecord cdr2 = (CdrCallDetailRecord) GeneralUtils.fromText(text);
		System.out.println(cdr);
		System.out.println("---------------------------------------");
		
		
	}
	
}