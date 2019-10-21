package com.interax.telephony.service.voicetraffic.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.Vector;

public class VtCustomer implements Serializable{

	private static final long serialVersionUID = 1L;

	private long id;

	private long enterpriseId;

	private String name;

	private String contextSuffix;

	private boolean enabled;

	private boolean deleted;

	private int type;

	private List<VtPeer> peers;
	
	private List<VtDid> dids;
	
	private List<VtVirtualOffshoreNumber> vons;

	public VtCustomer() {
		super();
		this.peers = new ArrayList<VtPeer>();
		this.dids = new ArrayList<VtDid>();
		this.vons = new ArrayList<VtVirtualOffshoreNumber>();
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

	public long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getContextSuffix() {
		return contextSuffix;
	}
	
	public void setContextSuffix(String contextSuffix) {
		this.contextSuffix = contextSuffix;
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
	
	public VoiceTrafficServiceType getType() {
		return VoiceTrafficServiceType.values()[this.type];
	}

	public void setType(VoiceTrafficServiceType type) {
		this.type = type.ordinal();
	}

	public void setType(String type) {
		this.type = VoiceTrafficServiceType.valueOf(type).ordinal();
	}

	public List<VtPeer> getPeers() {
		return peers;
	}
	public void setPeers(List<VtPeer> peers) {
		this.peers = peers;
	}

	public void setDids(List<VtDid> dids) {
		this.dids = dids;
	}

	public List<VtDid> getDids() {
		return dids;
	}

	public void setVons(List<VtVirtualOffshoreNumber> vons) {
		this.vons = vons;
	}

	public List<VtVirtualOffshoreNumber> getVons() {
		return vons;
	}
}
