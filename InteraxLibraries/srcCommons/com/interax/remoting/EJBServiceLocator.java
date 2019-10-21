package com.interax.remoting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.interax.logging.LoggerFactory;

public class EJBServiceLocator {
	static Logger log = LoggerFactory.getLogger(EJBServiceLocator.class); 
	
	private InitialContext initalContext;
	private Map<String, Object>cache;
	private static EJBServiceLocator theInstance;
	
	public static EJBServiceLocator getInstance() 
	{ 
		if(theInstance==null)
			theInstance = new EJBServiceLocator();	
		return theInstance; 
	}
	
	private EJBServiceLocator()  
	{
		try 
		{ 
			this.initalContext = new InitialContext();
			this.cache = Collections.synchronizedMap(new HashMap<String, Object>());
		} 
		catch (NamingException ex) 
		{
			log.log(Level.SEVERE, "Error creating EJB Service Locator", ex);
		}
	}
	
	public Object get(String jndiName) 
	{
		Object ret = null;  
		try 
		{
			if(this.cache.containsKey(jndiName)) 
				ret = this.cache.get(jndiName);
			else 
			{
				ret = initalContext.lookup(jndiName);
				this.cache.put(jndiName, ret);
			}
		}
		catch (NamingException ex)
		{
			log.log(Level.SEVERE, "Error in EJBServiceLocator CTX lookup", ex);
		}
		return ret;
	}
	
	public boolean reset()  
	{
		try 
		{ 
			this.initalContext = new InitialContext();
			this.cache = Collections.synchronizedMap(new HashMap<String, Object>());
			return true;
		} 
		catch (NamingException ex) 
		{
			log.log(Level.SEVERE, "Error creating EJB Service Locator", ex);
			return false;
		}
	}
}
