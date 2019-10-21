package com.interax.telephony.util;

import com.interax.remoting.EjbWrapper;


public class InteraxTelephonyEjbWrapper extends EjbWrapper {
	
	public InteraxTelephonyEjbWrapper(){
		super();
	}
	
	public InteraxTelephonyEjbWrapper(String login, String password){
		super(login, password);
	}
	
	public InteraxTelephonyEjbWrapper(String login, String password, String defaultServer, String defaultPort){
		super(login, password, defaultServer, defaultPort);
	}
	
	public InteraxTelephonyEjbWrapper(String ejbName, String server, String port){
		super(ejbName, server, port);
	}
	
}