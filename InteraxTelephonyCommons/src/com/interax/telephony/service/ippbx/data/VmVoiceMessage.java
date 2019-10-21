package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;


public class VmVoiceMessage implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long msgnum;
	private String dir;
	private String context;
	private String macrocontext;
	private String callerid;
	private String origtime;
	private String duration;
	private String mailboxuser;
	private String mailboxcontext;
	private String recording;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMsgnum() {
		return msgnum;
	}
	public void setMsgnum(long msgnum) {
		this.msgnum = msgnum;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getMacrocontext() {
		return macrocontext;
	}
	public void setMacrocontext(String macrocontext) {
		this.macrocontext = macrocontext;
	}
	public String getCallerid() {
		return callerid;
	}
	public void setCallerid(String callerid) {
		this.callerid = callerid;
	}
	public String getOrigtime() {
		return origtime;
	}
	public void setOrigtime(String origtime) {
		this.origtime = origtime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getMailboxuser() {
		return mailboxuser;
	}
	public void setMailboxuser(String mailboxuser) {
		this.mailboxuser = mailboxuser;
	}
	public String getMailboxcontext() {
		return mailboxcontext;
	}
	public void setMailboxcontext(String mailboxcontext) {
		this.mailboxcontext = mailboxcontext;
	}
	public String getRecording() {
		return recording;
	}
	public void setRecording(String recording) {
		this.recording = recording;
	}
}