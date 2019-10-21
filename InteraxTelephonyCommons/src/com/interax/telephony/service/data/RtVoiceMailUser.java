package com.interax.telephony.service.data;

import java.io.Serializable;
import java.util.Date;

public class RtVoiceMailUser implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long id;
	private long uniqueid;
	private String customerId;
	private String context;
	private String mailbox;
	private String password;
	private String fullname;
	private String email;
	private String pager;
	private String tz;
	private String attach;
	private String saycid;
	private String dialout;
	private String callback;
	private String review;
	private String operator;
	private String envelope;
	private String sayduration;
	private long saydurationm;
	private String sendvoicemail;
	private String delete;
	private String nextaftercmd;
	private String forcename;
	private String forcegreetings;
	private String hidefromdir;
	private Date stamp;
	
	private long itVoiceMailBoxId;
	private String emailTemplate;
	private String mailboxName;

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(long uniqueid) {
		this.uniqueid = uniqueid;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPager() {
		return pager;
	}
	public void setPager(String pager) {
		this.pager = pager;
	}
	public String getTz() {
		return tz;
	}
	public void setTz(String tz) {
		this.tz = tz;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getSaycid() {
		return saycid;
	}
	public void setSaycid(String saycid) {
		this.saycid = saycid;
	}
	public String getDialout() {
		return dialout;
	}
	public void setDialout(String dialout) {
		this.dialout = dialout;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getEnvelope() {
		return envelope;
	}
	public void setEnvelope(String envelope) {
		this.envelope = envelope;
	}
	public String getSayduration() {
		return sayduration;
	}
	public void setSayduration(String sayduration) {
		this.sayduration = sayduration;
	}
	public long getSaydurationm() {
		return saydurationm;
	}
	public void setSaydurationm(long saydurationm) {
		this.saydurationm = saydurationm;
	}
	public String getSendvoicemail() {
		return sendvoicemail;
	}
	public void setSendvoicemail(String sendvoicemail) {
		this.sendvoicemail = sendvoicemail;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public String getNextaftercmd() {
		return nextaftercmd;
	}
	public void setNextaftercmd(String nextaftercmd) {
		this.nextaftercmd = nextaftercmd;
	}
	public String getForcename() {
		return forcename;
	}
	public void setForcename(String forcename) {
		this.forcename = forcename;
	}
	public String getForcegreetings() {
		return forcegreetings;
	}
	public void setForcegreetings(String forcegreetings) {
		this.forcegreetings = forcegreetings;
	}
	public String getHidefromdir() {
		return hidefromdir;
	}
	public void setHidefromdir(String hidefromdir) {
		this.hidefromdir = hidefromdir;
	}
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	public long getItVoiceMailBoxId() {
		return itVoiceMailBoxId;
	}
	public void setItVoiceMailBoxId(long itVoiceMailBoxId) {
		this.itVoiceMailBoxId = itVoiceMailBoxId;
	}
	
	public String getEmailTemplate() {
		return emailTemplate;
	}
	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	public String getMailboxName() {
		return mailboxName;
	}
	public void setMailboxName(String mailboxName) {
		this.mailboxName = mailboxName;
	}
	
}