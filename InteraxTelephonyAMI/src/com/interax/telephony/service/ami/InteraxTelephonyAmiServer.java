package com.interax.telephony.service.ami;
import java.io.IOException;

import org.asteriskjava.live.AsteriskServer;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.event.ManagerEvent;

import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;


public abstract class InteraxTelephonyAmiServer implements ManagerEventListener{
	
	public ManagerConnection managerConnection;
    public AsteriskServer asteriskServer;
    public InteraxTelephonyManagedCallCache managedCallCache; 
    
    protected String asteriskHost;
    protected String asteriskUser;
    protected String asteriskPassword;
    protected long sleepTime;
    protected InteraxTelephonyGenericEjbCaller genericEjbCaller;

    public InteraxTelephonyAmiServer(){
    	
    	managedCallCache = InteraxTelephonyManagedCallCache.load();
        
    }
    
    public void connectToAsterisk(){
    	
        ManagerConnectionFactory factory = new ManagerConnectionFactory(this.asteriskHost, this.asteriskUser, this.asteriskPassword);
        this.managerConnection = factory.createManagerConnection();
        this.asteriskServer = new DefaultAsteriskServer(this.asteriskHost, this.asteriskUser, this.asteriskPassword);
    }
    

    public void run() throws IOException, InterruptedException, IllegalStateException, AuthenticationFailedException, TimeoutException{
    	
	    	managerConnection.addEventListener(this);
	        managerConnection.login();
	            
	        boolean run = true;
	        while(run){
	        	Thread.sleep(600000);
	            //managerConnection.sendAction(new StatusAction());
	        }
	

	        managerConnection.logoff();
    }

    public abstract void onManagerEvent(ManagerEvent event);


}