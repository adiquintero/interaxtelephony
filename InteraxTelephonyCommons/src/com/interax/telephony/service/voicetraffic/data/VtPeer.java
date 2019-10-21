package com.interax.telephony.service.voicetraffic.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ItPeer;
import com.interax.telephony.service.data.ItPeerType;

public class VtPeer extends ItPeer implements Serializable{
	private static final long serialVersionUID = 1L;

	// GENERAL
	private long id;
	private long customerId;
	private boolean enabled;
	private boolean deleted;
	private String language;
	private String host;
	private String login;
	private String secret;
	
	

	// IAX
	private boolean hasIax;
	
	// SIP
	private boolean hasSip;
	
	public VtPeer() {
		super();
		this.setType(ItPeerType.VT_PEER);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public boolean getHasIax() {
		return hasIax;
	}
	public void setHasIax(boolean hasIax) {
		this.hasIax = hasIax;
	}
	
	public boolean getHasSip() {
		return hasSip;
	}
	public void setHasSip(boolean hasSip) {
		this.hasSip = hasSip;
	}
	
	public boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
}
