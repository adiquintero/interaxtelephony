package com.interax.persistence.audict;

import java.io.Serializable;
import java.util.Date;

public class AdminAction implements Serializable
{
	private static final long serialVersionUID = 1L;

	private long id;
	private String objectType;
	private String objectIds;
	private String userLogin;
	private String actionType;
	private String actionInfo;
	private Date actionTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getObjectIds() {
		return objectIds;
	}
	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getActionInfo() {
		return actionInfo;
	}
	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}
	public Date getActionTime() {
		return actionTime;
	}
	public void setOperationTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
}
