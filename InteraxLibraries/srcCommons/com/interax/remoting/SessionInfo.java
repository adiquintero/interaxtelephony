package com.interax.remoting;

import java.io.Serializable;

public class SessionInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String a;
	private String serverA;
	private String portA;
	private String b;
	private String serverB;
	private String portB;
	private String sessionKey; 
	
	public String getPortA() {
		return portA;
	}
	public void setPortA(String portA) {
		this.portA = portA;
	}
	public String getPortB() {
		return portB;
	}
	public void setPortB(String portB) {
		this.portB = portB;
	}
	public String getServerA() {
		return serverA;
	}
	public void setServerA(String serverA) {
		this.serverA = serverA;
	}
	public String getServerB() {
		return serverB;
	}
	public void setServerB(String serverB) {
		this.serverB = serverB;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
}
