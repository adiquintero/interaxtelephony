package com.interax.telephony.util;

import java.util.Calendar;
//import java.util.Hashtable;

import com.interax.telephony.service.log.InteraxTelephonyLogger;
import java.util.HashMap;

public class InteraxTelephonyGenericEjbCaller {


	private static final long serialVersionUID = 1L;
		
	private static final String SEPARATOR = "|"; 
	private static final int BACKOFF_SECONDS = 60;
	private static final int MAX_ATTEMPTS = 3;
	
	protected InteraxTelephonyEjbWrapper ejbWrapper;
	protected String defaultServer;
	protected String defaultPort;
	protected InteraxTelephonyLogger logger;
	
	private static HashMap<String,InteraxTelephonyGenericEjb> genericControllers;
	private static HashMap<String, Integer> genericControllersAttempts;
	private static HashMap<String, Calendar> genericControllersLastAttempt;
	private static HashMap<String, Boolean> genericControllersBackedOff;
	
	public InteraxTelephonyGenericEjbCaller(InteraxTelephonyLogger logger) {
		this.logger = logger;
		initializeGenericControllerCache();
		this.ejbWrapper = new InteraxTelephonyEjbWrapper(null, null);
		this.defaultServer = null;
		this.defaultPort = null;
	}
	
	public InteraxTelephonyGenericEjbCaller(InteraxTelephonyLogger logger, String defaultServer, String defaultPort) {
		this.logger = logger;
		initializeGenericControllerCache();
		this.ejbWrapper = new InteraxTelephonyEjbWrapper(null, null, defaultServer, defaultPort);
		this.defaultServer = defaultServer;
		this.defaultPort = defaultPort;
	}
	
	
//	public Object getGenericController(String controllerName){
//		return getGenericController(controllerName, this.defaultServer, this.defaultPort);
//	}
	
	public Object getGenericController(String controllerName, String server, String port){

		String key = controllerName + SEPARATOR + server + SEPARATOR + port;
		Object genericController = getGenericControllerFromCache(key, controllerName);

		if(genericController!= null)
			return genericController;
		else
			return lookupGenericController(key, controllerName, server, port);
	}
	
	private synchronized void initializeGenericControllerCache(){
		if(genericControllers == null) {
			genericControllers = new HashMap<String, InteraxTelephonyGenericEjb>();
			genericControllersAttempts = new HashMap<String, Integer>();
			genericControllersLastAttempt = new HashMap<String, Calendar>();
			genericControllersBackedOff = new HashMap<String, Boolean>();
		}
	}

	
	private synchronized Object getGenericControllerFromCache(String key, String controllerName){
	
		if(genericControllers.containsKey(key))
		{
			InteraxTelephonyGenericEjb genericController = genericControllers.get(key);
			this.logger.info(controllerName + " found, testing echo.");
			try {
				genericController.echo();
				this.logger.info("Echo test successfull.");
				return genericController;
			} catch (Exception e) {
				this.logger.warn("An error ocurred while doing echo test, removing controller from cache.");
				genericControllers.remove(key);
				return null;
			}
		}
		else{
			this.logger.info(controllerName + " not found in cache.");
			return null;
		}
	}
	
	private synchronized Object lookupGenericController(String key, String controllerName, String server, String port){
		
		try{
			InteraxTelephonyGenericEjb genericController = null;
			Calendar now = Calendar.getInstance();

			
			if(!genericControllersBackedOff.containsKey(key)){
				genericControllersBackedOff.put(key, false);
			}
			if(!genericControllersAttempts.containsKey(key)){
				genericControllersAttempts.put(key, 0);
			}
			
			Boolean isBackedOff = genericControllersBackedOff.get(key);
			Integer currentAttempts = genericControllersAttempts.get(key);

			
			
			if(isBackedOff){
				Calendar lastAttempt = genericControllersLastAttempt.get(key);
				Calendar nextAttempt = (Calendar) lastAttempt.clone();
				nextAttempt.add(Calendar.SECOND, BACKOFF_SECONDS);
				this.logger.info("The lookup of " + controllerName + " has been backed off until " + nextAttempt.getTime());
				if(nextAttempt.before(now)){
					this.logger.info("Backoff of " + controllerName + " has concluded. Turning off the backoff.");
					currentAttempts = 0;
					genericControllersBackedOff.put(key, false);
				}
				else{
					this.logger.info("Backoff end date has not been reached for " + controllerName + ". Returning null.");
					return null;
				}
			}

			if(currentAttempts<MAX_ATTEMPTS){
				try {
					this.logger.info("Looking for " + controllerName);
					genericController = (InteraxTelephonyGenericEjb) this.ejbWrapper.getEjbRef(controllerName, server, port);
					this.logger.info(controllerName + " found.");

				} catch (Exception e) {
					this.logger.error("An error ocurred while trying to lookup for " + controllerName + ". Detail: " + e.getMessage());
                                        e.printStackTrace();
				}
			}
			
			if(genericController != null){
				this.logger.info(controllerName + " initialized successfully.");
				genericControllers.put(key, genericController);
				genericControllersAttempts.put(key, 0);
				genericControllersLastAttempt.put(key, now);
			}
			else{
				currentAttempts++;
				this.logger.info(controllerName + " couldn't be initialized. Attempt count: " + currentAttempts + "/" + MAX_ATTEMPTS);
				genericControllers.remove(key);
				genericControllersLastAttempt.put(key, now);
				
				if(currentAttempts >= MAX_ATTEMPTS){
					Calendar nextAttempt = (Calendar) now.clone();
					nextAttempt.add(Calendar.SECOND, BACKOFF_SECONDS);
					this.logger.info("Max attempts count for " + controllerName + " has been reached. Turning backoff on until" + nextAttempt.getTime());
					genericControllersBackedOff.put(key, true);
				}
				genericControllersAttempts.put(key, currentAttempts);
			}
			
			return genericController;
	
		}
		catch(Exception e)
		{
			this.logger.warn("An error ocurred while trying to initialize " + controllerName);
			return null;
		}
			
	}

}

