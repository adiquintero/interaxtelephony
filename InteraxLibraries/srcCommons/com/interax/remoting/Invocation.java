package com.interax.remoting;

import java.io.Serializable;
import java.util.HashMap;
//import java.util.Hashtable;

public class Invocation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String method;
	private SessionInfo sessionInfo;
	private HashMap<String, Object> params;
	
	public Invocation(String method, HashMap<String, Object> params, SessionInfo sessionInfo) throws Exception
	{
		if(method!=null && sessionInfo!=null)
		{
			this.method = method;
			this.sessionInfo = sessionInfo;
			this.params = params;			
		}
		else
		{
			throw new Exception("Incorrect parameter number");
		}
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public HashMap<String, Object> getParams() {
		return params;
	}
	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}
	public void setSessionInfo(SessionInfo sessionKey) {
		this.sessionInfo = sessionKey;
	}
}
