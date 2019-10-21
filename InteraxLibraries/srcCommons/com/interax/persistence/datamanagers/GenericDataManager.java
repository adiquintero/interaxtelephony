package com.interax.persistence.datamanagers;

import com.interax.logging.GenericLogger;
import com.interax.logging.LoggerFactory;

public abstract class GenericDataManager {

	//TODO: Poner todas las funciones comunes aca.
	

	protected GenericLogger logger;
	
	@SuppressWarnings("unchecked")
	protected GenericLogger getClassLogger(Class clazz){
		return LoggerFactory.getLogger(clazz);
	}
	
	protected void setLogger(GenericLogger logger){
		this.logger = logger;
	}
	
	protected GenericLogger getLogger(){
		return this.logger;
	}
}
