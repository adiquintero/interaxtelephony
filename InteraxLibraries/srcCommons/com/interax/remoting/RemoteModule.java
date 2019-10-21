package com.interax.remoting;

import javax.crypto.SecretKey;

public class RemoteModule {
	private SessionInfo sessionInfo;
	private SecretKey secretKey;
	private InvokeInterface invocationInterface;
	
	
	public RemoteModule() {
		
	}
	public RemoteModule(SecretKey secretKey, InvokeInterface invocationInterface) {
		super();
		this.secretKey = secretKey;
		this.invocationInterface = invocationInterface;
	}
	public InvokeInterface getInvocationInterface() {
		return invocationInterface;
	}
	public void setInvocationInterface(InvokeInterface invocationInterface) {
		this.invocationInterface = invocationInterface;
	}
	
	public SecretKey getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}
	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}
	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}
		
}
