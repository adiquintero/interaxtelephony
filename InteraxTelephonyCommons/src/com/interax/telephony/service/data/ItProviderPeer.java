package com.interax.telephony.service.data;

import java.io.Serializable;

public class ItProviderPeer implements Serializable{
	private static final long serialVersionUID = 1L;

	// GENERAL
	private long id;
	private long providerId;
	private boolean enabled;
	private boolean deleted;
	private String name;
	private String host;
	private String secret;
	
	

	// IAX
	private boolean hasIax;
	
	// SIP
	private boolean hasSip;
	
	public  ItProviderPeer() {
		super();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getProviderId() {
		return providerId;
	}
	public void setProviderId(long providerId) {
		this.providerId = providerId;
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
	
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}

	
	
}
