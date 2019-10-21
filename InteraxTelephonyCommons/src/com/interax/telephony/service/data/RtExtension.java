package com.interax.telephony.service.data;

import java.io.Serializable;


public class RtExtension implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String context;
	private String exten;
	private long priority;
	private String app;
	private String appdata;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getExten() {
		return exten;
	}
	public void setExten(String exten) {
		this.exten = exten;
	}
	public long getPriority() {
		return priority;
	}
	public void setPriority(long priority) {
		this.priority = priority;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getAppdata() {
		return appdata;
	}
	public void setAppdata(String appdata) {
		this.appdata = appdata;
	}
}