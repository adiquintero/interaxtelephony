package com.interax.telephony.service.data;

import java.io.Serializable;


public class RtSipPeer implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String host;
	private String nat;
	private String type;
	private String accountcode;
	private String amaflags;
	private long callLimit;
	private String callgroup;
	private String callerid;
	private String cancallforward;
	private String canreinvite;
	private String context;
	private String defaultip;
	private String dtmfmode;
	private String fromuser;
	private String fromdomain;
	private String insecure;
	private String language;
	private String mailbox;
	private String md5secret;
	private String deny;
	private String permit;
	private String mask;
	private String musiconhold;
	private String pickupgroup;
	private String qualify;
	private String regexten;
	private String restrictcid;
	private String rtptimeout;
	private String rtpholdtimeout;
	private String secret;
	private String setvar;
	private String disallow;
	private String allow;
	private String fullcontact;
	private String ipaddr;
	private long port;
	private String regserver;
	private long regseconds;
	private String username;
	private String defaultuser;
	private String subscribecontext;
	
	private long itPeerId;
	private int itPeerType;	
	private String userfield;

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
	
	public long getId() {
		return id;
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
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getNat() {
		return nat;
	}
	public void setNat(String nat) {
		this.nat = nat;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public long getCallLimit() {
		return callLimit;
	}
	public void setCallLimit(long callLimit) {
		this.callLimit = callLimit;
	}
	public String getCallgroup() {
		return callgroup;
	}
	public void setCallgroup(String callgroup) {
		this.callgroup = callgroup;
	}
	public String getCallerid() {
		return callerid;
	}
	public void setCallerid(String callerid) {
		this.callerid = callerid;
	}
	public String getCancallforward() {
		return cancallforward;
	}
	public void setCancallforward(String cancallforward) {
		this.cancallforward = cancallforward;
	}
	public String getCanreinvite() {
		return canreinvite;
	}
	public void setCanreinvite(String canreinvite) {
		this.canreinvite = canreinvite;
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
	public String getDtmfmode() {
		return dtmfmode;
	}
	public void setDtmfmode(String dtmfmode) {
		this.dtmfmode = dtmfmode;
	}
	public String getFromuser() {
		return fromuser;
	}
	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}
	public String getFromdomain() {
		return fromdomain;
	}
	public void setFromdomain(String fromdomain) {
		this.fromdomain = fromdomain;
	}
	public String getInsecure() {
		return insecure;
	}
	public void setInsecure(String insecure) {
		this.insecure = insecure;
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
	public String getMd5secret() {
		return md5secret;
	}
	public void setMd5secret(String md5secret) {
		this.md5secret = md5secret;
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
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getMusiconhold() {
		return musiconhold;
	}
	public void setMusiconhold(String musiconhold) {
		this.musiconhold = musiconhold;
	}
	public String getPickupgroup() {
		return pickupgroup;
	}
	public void setPickupgroup(String pickupgroup) {
		this.pickupgroup = pickupgroup;
	}
	public String getQualify() {
		return qualify;
	}
	public void setQualify(String qualify) {
		this.qualify = qualify;
	}
	public String getRegexten() {
		return regexten;
	}
	public void setRegexten(String regexten) {
		this.regexten = regexten;
	}
	public String getRestrictcid() {
		return restrictcid;
	}
	public void setRestrictcid(String restrictcid) {
		this.restrictcid = restrictcid;
	}
	public String getRtptimeout() {
		return rtptimeout;
	}
	public void setRtptimeout(String rtptimeout) {
		this.rtptimeout = rtptimeout;
	}
	public String getRtpholdtimeout() {
		return rtpholdtimeout;
	}
	public void setRtpholdtimeout(String rtpholdtimeout) {
		this.rtpholdtimeout = rtpholdtimeout;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getSetvar() {
		return setvar;
	}
	public void setSetvar(String setvar) {
		this.setvar = setvar;
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
	public String getFullcontact() {
		return fullcontact;
	}
	public void setFullcontact(String fullcontact) {
		this.fullcontact = fullcontact;
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
	public String getRegserver() {
		return regserver;
	}
	public void setRegserver(String regserver) {
		this.regserver = regserver;
	}
	public long getRegseconds() {
		return regseconds;
	}
	public void setRegseconds(long regseconds) {
		this.regseconds = regseconds;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDefaultuser() {
		return defaultuser;
	}
	public void setDefaultuser(String defaultuser) {
		this.defaultuser = defaultuser;
	}
	public String getSubscribecontext() {
		return subscribecontext;
	}
	public void setSubscribecontext(String subscribecontext) {
		this.subscribecontext = subscribecontext;
	}
	public String getUserfield() {
		return userfield;
	}
	public void setUserfield(String userfield) {
		this.userfield = userfield;
	}
	
	
}