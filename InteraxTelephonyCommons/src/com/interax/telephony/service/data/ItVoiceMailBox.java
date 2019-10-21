package com.interax.telephony.service.data;

import java.io.Serializable;

public class ItVoiceMailBox implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected long id;
	protected int type;	
	long foreignId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setType(ItVoiceMailBoxType itVoiceMailBoxType){
		this.type = itVoiceMailBoxType.ordinal();
	}
	
	public void setType(String itVoiceMailBoxType){
		this.type = ItVoiceMailBoxType.valueOf(itVoiceMailBoxType).ordinal();
	}
	
	public ItVoiceMailBoxType getType(){
		return ItVoiceMailBoxType.values()[this.type];
	}
	
	public long getForeignId() {
		return foreignId;
	}

	public void setForeignId(long foreignId) {
		this.foreignId = foreignId;
	}
}
