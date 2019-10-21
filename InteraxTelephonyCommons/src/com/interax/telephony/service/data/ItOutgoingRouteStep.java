package com.interax.telephony.service.data;
import java.io.Serializable;


public class ItOutgoingRouteStep implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long id;

	private long providerPeerId;

	private int priority;

	private String outgoingPrefix;

	private long routeId;

	


	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProviderPeerId() {
		return this.providerPeerId;
	}

	public void setProviderPeerId(long providerPeerId) {
		this.providerPeerId = providerPeerId;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getOutgoingPrefix() {
		return this.outgoingPrefix;
	}

	public void setOutgoingPrefix(String outgoingPrefix) {
		this.outgoingPrefix = outgoingPrefix;
	}

	public long getRouteId() {
		return this.routeId;
	}

	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}

}
