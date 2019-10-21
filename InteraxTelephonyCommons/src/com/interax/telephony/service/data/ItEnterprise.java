package com.interax.telephony.service.data;

import java.io.Serializable;


public class ItEnterprise implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String contextSuffix;
	private String audioFolder;

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
	public String getContextSuffix() {
		return contextSuffix;
	}
	public void setContextSuffix(String contextSuffix) {
		this.contextSuffix = contextSuffix;
	}
	public String getAudioFolder() {
		return audioFolder;
	}
	public void setAudioFolder(String audioFolder) {
		this.audioFolder = audioFolder;
	}
	

}