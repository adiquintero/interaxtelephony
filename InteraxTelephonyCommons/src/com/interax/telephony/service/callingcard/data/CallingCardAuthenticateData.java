package com.interax.telephony.service.callingcard.data;

import java.io.Serializable;

public class CallingCardAuthenticateData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String secret;
	private String ani;
	private String dnid;
	private int accessType;
	private long enterpriseId;
	
	public CallingCardAccessType getAccessType() {
		return CallingCardAccessType.values()[this.accessType];
	}
	public void setAccessType(CallingCardAccessType accessType) {
		this.accessType = accessType.ordinal();
	}
	public void setAccessType(String accessType) {
		this.accessType = CallingCardAccessType.valueOf(accessType).ordinal();
	}
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getDnid() {
		return dnid;
	}
	public void setDnid(String dnid) {
		this.dnid = dnid;
	}
	public long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	private String maskSecret(String secret)
	{
		if(secret!=null)
		{
			if(secret.length()>4)
				return secret.substring(1,4)+"************";
			else
				return secret+"************";
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "CallingCardAuthenticateData{secret="+maskSecret(this.secret)+",ani="+this.ani+",dnid="+this.dnid+",accessType="+this.accessType+",enterpriseId="+enterpriseId+"}";
	}
	
}