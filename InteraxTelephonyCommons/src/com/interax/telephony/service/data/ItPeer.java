package com.interax.telephony.service.data;

import java.io.Serializable;

public abstract class ItPeer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected long id;
	protected int type;	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	//WORKAROUND
	public void setType(ItPeerType itInteraxTelephonyPeerType){
		this.type = itInteraxTelephonyPeerType.ordinal();
	}
	
	public void setType(String itInteraxTelephonyPeerType){
		this.type = ItPeerType.valueOf(itInteraxTelephonyPeerType).ordinal();
	}
}
