package com.interax.remoting;

import java.util.Properties;

import javax.naming.InitialContext;

import com.sun.appserv.security.ProgrammaticLogin;

public class EjbWrapper
 {
  private static final String DEFAULT_SERVER = "localhost";
  private static final String DEFAULT_PORT = "3700";
	
  String defaultServer;
  String defaultPort;
  String login;
  String password;
  ProgrammaticLogin pm;
	
  
  // Constructores
  public EjbWrapper()
  {
	this.login = null;
	this.password = null;
	this.defaultServer = EjbWrapper.DEFAULT_SERVER;
	this.defaultPort = EjbWrapper.DEFAULT_PORT;
  }
  
  
  public EjbWrapper(String login, String password)
   {
	this.login = login;
	this.password = password;
	this.defaultServer = EjbWrapper.DEFAULT_SERVER;
	this.defaultPort = EjbWrapper.DEFAULT_PORT;
   }
  
  public EjbWrapper(String login, String password, String defaultServer)
   {
	this.login = login;
	this.password = password;
	this.defaultServer = defaultServer;
	this.defaultPort = EjbWrapper.DEFAULT_PORT;
   }
  
  public EjbWrapper(String login, String password, String defaultServer, String defaultPort)
   {
	this.login = login;
	this.password = password;
	this.defaultServer = defaultServer;
	this.defaultPort = defaultPort;
   }
	
  
  // Métodos para obtener referencias a los diferentes EJBs
	  
  public Object getEjbRef(String ejbName) throws Exception{
	return getEjbRef(ejbName, this.defaultServer, this.defaultPort);
   }

  public Object getEjbRef(String ejbName, String server) throws Exception{
	return getEjbRef(ejbName, server, this.defaultPort);
   }

  public Object getEjbRef(String ejbName, String server, Long port) throws Exception{
	  return this.getEjbRef(ejbName, server, "" + port);
  }

  public Object getEjbRef(String ejbName, String server, String port) throws Exception
   {
	  Properties props = System.getProperties();
//	  props.setProperty("java.naming.factory.initial", "com.sun.appserv.naming.S1ASCtxFactory");
	  props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
	  props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
	  props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
	  
	  props.setProperty("java.naming.provider.url", "iiop://"+ server + ":" + port);
	  props.setProperty("com.sun.appserv.iiop.endpoints", server + ":" + port);
	  props.setProperty("com.sun.corba.ee.transport.ORBTCPTimeouts","100:60000:20:2147483647");
		
//	  System.setProperty("sun.rmi.transport.proxy.connectTimeout", "5");
//	  System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "5");
//	  System.setProperty("sun.rmi.transport.tcp.responseTimeout", "5");
//	  System.setProperty("com.sun.CORBA.transport.ORBTCPReadTimeouts", "1000");

	  // initial:max:backoff:maxsingle
	  // props.setProperty(ORBConstants.TRANSPORT_TCP_TIMEOUTS_PROPERTY,"2000:6000:20");
	  // props.setProperty(ORBConstants.TRANSPORT_TCP_CONNECT_TIMEOUTS_PROPERTY,"250:250:100:250");
//	  props.setProperty(ORBConstants.WAIT_FOR_RESPONSE_TIMEOUT,"5000");
	  
//	  props.setProperty("com.sun.corba.ee.transport.ORBWaitForResponseTimeout","5000");
//	  props.setProperty("com.sun.corba.ee.transport.ORBTCPConnectTimeouts","10:50:10:50");
//	  props.setProperty("com.sun.corba.ee.transport.ORBTCPTimeouts","50:200:5:10");
	  
	  
	  InitialContext ic = new InitialContext(props);
	  if(pm == null && login != null && password !=null){
		  pm = new ProgrammaticLogin();
	  	  if(!pm.login(this.login, this.password)){
	  		  throw new Exception("failure of login");
	  	  }
	  }
	  return ic.lookup(ejbName);
   }
	
}