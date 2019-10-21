package com.interax.telephony.service.data;

import java.io.Serializable;


public class RtIaxPeer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String username;
	private String type;
	private String secret;
	private String md5secret;
	private String dbsecret;
	private String notransfer;
	private String inkeys;
	private String outkey;
	private String auth;
	private String accountcode;
	private String amaflags;
	private String callerid;
	private String context;
	private String defaultip;
	private String host;
	private String language;
	private String mailbox;
	private String deny;
	private String permit;
	private String qualify;
	private String disallow;
	private String allow;
	private String ipaddr;
	private long port;
	private long regseconds;
	
	private long itPeerId;
	private int itPeerType;
	private String userfield;

	public long getId() {
		return id;
	}
	public long getItPeerId() {
		return itPeerId;
	}
	public void setItPeerId(long ipPeerId) {
		this.itPeerId = ipPeerId;
	}
	
	//WORKAROUND
	public void setItPeerType(ItPeerType itPeerType){
		this.itPeerType = itPeerType.ordinal();
	}
	
	public void setItPeerType(String itPeerType){
		this.itPeerType = ItPeerType.valueOf(itPeerType).ordinal();
	}
	
	public ItPeerType getItPeerType(){
		return ItPeerType.values()[this.itPeerType];
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getMd5secret() {
		return md5secret;
	}
	public void setMd5secret(String md5secret) {
		this.md5secret = md5secret;
	}
	public String getDbsecret() {
		return dbsecret;
	}
	public void setDbsecret(String dbsecret) {
		this.dbsecret = dbsecret;
	}
	public String getNotransfer() {
		return notransfer;
	}
	public void setNotransfer(String notransfer) {
		this.notransfer = notransfer;
	}
	public String getInkeys() {
		return inkeys;
	}
	public void setInkeys(String inkeys) {
		this.inkeys = inkeys;
	}
	public String getOutkey() {
		return outkey;
	}
	public void setOutkey(String outkey) {
		this.outkey = outkey;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getAccountcode() {
		return accountcode;
	}
	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}
	public String getAmaflags() {
		return amaflags;
	}
	public void setAmaflags(String amaflags) {
		this.amaflags = amaflags;
	}
	public String getCallerid() {
		return callerid;
	}
	public void setCallerid(String callerid) {
		this.callerid = callerid;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getDefaultip() {
		return defaultip;
	}
	public void setDefaultip(String defaultip) {
		this.defaultip = defaultip;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}
	public String getDeny() {
		return deny;
	}
	public void setDeny(String deny) {
		this.deny = deny;
	}
	public String getPermit() {
		return permit;
	}
	public void setPermit(String permit) {
		this.permit = permit;
	}
	public String getQualify() {
		return qualify;
	}
	public void setQualify(String qualify) {
		this.qualify = qualify;
	}
	public String getDisallow() {
		return disallow;
	}
	public void setDisallow(String disallow) {
		this.disallow = disallow;
	}
	public String getAllow() {
		return allow;
	}
	public void setAllow(String allow) {
		this.allow = allow;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public long getPort() {
		return port;
	}
	public void setPort(long port) {
		this.port = port;
	}
	public long getRegseconds() {
		return regseconds;
	}
	public void setRegseconds(long regseconds) {
		this.regseconds = regseconds;
	}
	public String getUserfield() {
		return userfield;
	}
	public void setUserfield(String userfield) {
		this.userfield = userfield;
	}
	
	
}