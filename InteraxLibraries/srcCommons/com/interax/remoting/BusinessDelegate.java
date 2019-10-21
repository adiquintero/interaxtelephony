package com.interax.remoting;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.naming.InitialContext;

import com.interax.logging.LoggerFactory;
import com.interax.remoting.security.CipherManager;
import com.interax.remoting.security.DHKeyManager;

public class BusinessDelegate {
	static Logger log = LoggerFactory.getLogger(BusinessDelegate.class); 
	
	private Map<String, RemoteModule>cache;
	private static BusinessDelegate theInstance;// = new BusinessDelegate();
	//private SessionInfo coreSessionInfo;
	
	public static BusinessDelegate getInstance() { 
		if(theInstance==null)
			theInstance = new BusinessDelegate();
		
		return theInstance; 
	}
	
      private BusinessDelegate()
      {
          try 
          {
        	//TODO parametros sacar de un archivo de configuraci�n  
        	  
        	  Properties defaultProps = new Properties();
	    	  InputStream in = this.getClass().getResourceAsStream("/module.properties");
	    	  defaultProps.load(in);
	    	  in.close();
			
			this.cache = Collections.synchronizedMap(new HashMap<String, RemoteModule>());
			SessionInfo session = new SessionInfo();
			session.setA(defaultProps.getProperty("module.id"));
			session.setServerA(defaultProps.getProperty("module.ip"));
			session.setPortA(defaultProps.getProperty("module.port"));
			session.setB(defaultProps.getProperty("core.id"));
			session.setServerB(defaultProps.getProperty("core.ip"));
			session.setPortB(defaultProps.getProperty("core.port"));
			if(!session.getA().equals(session.getB()))
				negotiateKeys(session);
          } catch (Exception ex) {
        	  log.log(Level.SEVERE, "Error creando BusinessDelegate", ex);
          }
      }
      
      private boolean negotiateKeys(SessionInfo sessionInfo) throws Exception
      {
    	  RemoteModule remoteModule = cache.get(sessionInfo.getB());
    	  InvokeInterface invoke = null;
    	  if(remoteModule!=null)
    		  invoke = remoteModule.getInvocationInterface();
    	  else
    			  remoteModule = new RemoteModule();
    	  if(invoke==null)
    	  {
    		Properties props = new Properties();
  			props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
  			props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
  			props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
  			props.setProperty("org.omg.CORBA.ORBInitialHost", sessionInfo.getServerB());
  			props.setProperty("org.omg.CORBA.ORBInitialPort", sessionInfo.getPortB());
  			InitialContext ctx = new InitialContext(props);
  			invoke = (InvokeInterface)ctx.lookup("ejb/InvokeInterface");
  			remoteModule.setInvocationInterface(invoke);
  			
  			cache.put(sessionInfo.getB(), remoteModule);
    	  }   		  
    	  
    	  DHKeyManager dh = new DHKeyManager();
    	  dh.generateParams();
    	  dh.createKeyPair();
    	  if(sessionInfo.getSessionKey()==null)
    	  {//Core authentication
        	  byte[] serverPublicKey = invoke.authenticate(sessionInfo, dh.getPublicKey());
        	  dh.agreeKey(serverPublicKey);    		  
    	  }
    	  else
    	  {//Other module key agreement
    		  //TODO hacer para canal
    		  //byte[] serverPublicKey = invoke.authenticate(sessionInfo, dh.getPublicKey());
        	  //dh.agreeKey(serverPublicKey, false); 
    	  }
    	  remoteModule.setSecretKey(dh.getSecretKey());
    	  remoteModule.setSessionInfo(sessionInfo);
    	  return true;
      }
  	
  	public void registerRemoteModule(SessionInfo session, SecretKey secretKey) throws Exception
  	{
  		String temp = session.getA();
  		session.setA(session.getB());
  		session.setB(temp);

  		temp = session.getServerA();
  		session.setServerA(session.getServerB());
  		session.setServerB(temp);
  		
  		temp = session.getPortA();
  		session.setPortA(session.getPortB());
  		session.setPortB(temp);
  		
  		RemoteModule module = new RemoteModule();
  		module.setSessionInfo(session);
  		module.setSecretKey(secretKey);
  		
  		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
		props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
		props.setProperty("org.omg.CORBA.ORBInitialHost", session.getServerB());
		props.setProperty("org.omg.CORBA.ORBInitialPort", session.getPortB());
		InitialContext ctx = new InitialContext(props);
		InvokeInterface invoke = (InvokeInterface)ctx.lookup("ejb/InvokeInterface");
		module.setInvocationInterface(invoke);
			
  		if(session!=null)
  			cache.put(session.getB(), module);
  		else
  			throw new Exception("No se pudo  registrar el modulo remoto");
  	}
         
      public Object invoke(Invocation invocation) throws Exception
      {
    	  SessionInfo session = invocation.getSessionInfo();
    	  CipherManager cipher = new CipherManager();
    	  RemoteModule remoteModule = cache.get(session.getB());
    	  SecretKey secret = remoteModule.getSecretKey();
    	  InvokeInterface invoke = remoteModule.getInvocationInterface();
    	  SealedObject sealed = cipher.encrypt(invocation, secret);
    	  SealedObject ret = invoke.invoke(sealed);
    	  return cipher.decrypt(ret, secret);
      }
      
      // And so on...

      public void destroy() {
          // In this case, do nothing
      }

	public SessionInfo getSessionInfo(String id) {
		RemoteModule module = cache.get(id);
		if(module!=null)
			return module.getSessionInfo();
		else
			return null;
	}
}