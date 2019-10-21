package com.interax.telephony.service.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.Vector;


public class ItProvider implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private boolean enabled;

	private boolean deleted;


	private List<ItProviderPeer> providerPeers;
	
	

	public ItProvider() {
		super();
		this.providerPeers = new ArrayList<ItProviderPeer>();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	

	public List<ItProviderPeer> getProviderPeers() {
		return providerPeers;
	}
	public void setProviderPeers(List<ItProviderPeer> peers) {
		this.providerPeers = peers;
	}


	

}
