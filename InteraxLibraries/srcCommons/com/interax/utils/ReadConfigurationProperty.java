package com.interax.utils;

//import java.util.Hashtable;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReadConfigurationProperty {

	
	private static  ReadConfigurationProperty _instance;
	private HashMap<String, String> fileProperties;
	private ResourceBundle rb;	
	
	private ReadConfigurationProperty() {
		// construct object . . .
		fileProperties = new HashMap<String, String>();
		
	}
	
	// For lazy initialization
	public static synchronized ReadConfigurationProperty getInstance() {
		if (_instance==null) {
			_instance = new ReadConfigurationProperty();
		}
		return _instance;
	}

	/**
	 * Busca en el properties el valor asociado al key pasado por parametro 
	 * @param key 
	 * @return String - con el valor asociado a key
	 */
	@SuppressWarnings("unchecked")
	public synchronized String getProperty(String key, String fileNameProperties, Locale loc, Class clazz) {
	
		if(fileProperties.containsKey(key)){		
			return (String)fileProperties.get(key);
		}else{
			rb = ResourceBundle.getBundle(fileNameProperties,loc,clazz.getClassLoader());
			fileProperties.put(key, (String)rb.getString(key));
		}
		return (String)fileProperties.get(key);
	}
	
	
	public synchronized void clearProperties(){
		fileProperties.clear();
	}
	
}