package com.interax.persistence.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.interax.logging.LoggerFactory;


public class ServiceLocator 
{
	static Logger log = LoggerFactory.getLogger(ServiceLocator.class); 
	static String targetServer = "GLASSFISH";
		
	private InitialContext initalContext;
	private Map<String, DataSource>cache;
	private static ServiceLocator theInstance;
	
	public static ServiceLocator getInstance() 
	{ 
		if(theInstance==null)
			theInstance = new ServiceLocator();	
		return theInstance; 
	}
	
	private ServiceLocator()  
	{
		try 
		{ 
			this.initalContext = new InitialContext();
			this.cache = Collections.synchronizedMap(new HashMap<String, DataSource>());
		} 
		catch (NamingException ex) 
		{
			log.log(Level.SEVERE, "Error creating Service Locator", ex);
		}
	}
	
	public DataSource getDataSource(String dataSourceName) 
	{
		DataSource datasource = null;  
		try 
		{
			if(this.cache.containsKey(dataSourceName)) 
				datasource = (DataSource) this.cache.get(dataSourceName);
			else 
			{
				if(targetServer.equals("GLASSFISH"))
				 {
				  //Context envContext = (Context) initalContext.lookup("java:comp/env");
				  datasource = (DataSource) initalContext.lookup(dataSourceName);
				  this.cache.put(dataSourceName, datasource);
				 }
				else if(targetServer.equals("TOMCAT"))
				 {
				  Context envContext = (Context) initalContext.lookup("java:comp/env");
				  datasource = (DataSource) envContext.lookup(dataSourceName);
				  this.cache.put(dataSourceName, datasource);
				 }
			}
		}
		catch (NamingException ex)
		{
			log.log(Level.SEVERE, "Error in CTX lookup", ex);
		}
		return datasource;
	}

	public static String getTargetServer() {
		return targetServer;
	}

	public static void setTargetServer(String targetServer) {
		ServiceLocator.targetServer = targetServer;
	}
}
