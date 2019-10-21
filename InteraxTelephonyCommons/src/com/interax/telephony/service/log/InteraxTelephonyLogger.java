package com.interax.telephony.service.log;
import java.lang.reflect.Method;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;


@SuppressWarnings("deprecation")
public abstract class InteraxTelephonyLogger {
	
	protected Logger logger;

	@SuppressWarnings("unchecked")
	public InteraxTelephonyLogger(Class loggerClass) {
		super();
		this.logger = Logger.getLogger(loggerClass);
	}
	
	@SuppressWarnings("unchecked")
	public InteraxTelephonyLogger(Class loggerClass, String path) {
		super();
		this.logger = Logger.getLogger(loggerClass);
		PropertyConfigurator.configure(path + "log4j.properties");
	}
	
	public void debug(String metadata){
		this.logger.debug(this.getPreamble() + metadata);
	}
	
	
	public void debug(String metadata, Object object){
		this.printObject(metadata, object, Priority.DEBUG);
	}

	public void error(String metadata, Exception e){
		this.error(metadata);
		this.errorDetail(e);
	}

	public void error(String metadata){
		this.logger.error(this.getPreamble() + metadata);
	}

	public void errorDetail(Exception e){
		this.error("Error message: " + e.getMessage());
	}

	public void fatal(String metadata){
		this.logger.fatal(this.getPreamble() + metadata);
	}

	public void fatal(String metadata, Exception e){
		this.logger.fatal(this.getPreamble() + metadata);
	}

	public void fatalDetail(Exception e){
		this.fatal("Error message: " + e.getMessage());
	}

	public void info(String metadata){
		this.logger.info(this.getPreamble() + metadata);
	}

	public void warn(String metadata){
		this.logger.warn(this.getPreamble() + metadata);
	}
	
	protected abstract String getPreamble();
	
	protected void printObject(String title, Object object, Priority priority) {
		
		String preamble = this.getPreamble();
		this.logger.log(priority, preamble + title);
		
		Method[] objectMethods = object.getClass().getMethods();
		Object[] noArgs = new Object[0];
		int methodCount = objectMethods.length;
		for(int i=0; i<methodCount; i++){
			String methodName = objectMethods[i].getName();
			if(methodName.startsWith("get")){
				String attributeName = methodName.substring(3).toUpperCase();
				if(attributeName.equals("CLASS")) continue;
				try{
					Object attribute = objectMethods[i].invoke(object, noArgs);
					String attributeValue = "";
					
					if(attribute instanceof Calendar){
						Calendar calendar = (Calendar) attribute;
						attributeValue = calendar.getTime().toString();
					}
					else{
						attributeValue = attribute.toString();
					}
					this.logger.log(priority, preamble + "-" + attributeName + ": " + attributeValue);
				}
				catch(Exception e){
					this.logger.log(priority, preamble + "-" + attributeName + ": ERROR (" + e.getMessage() + ")");
				}
				
			}
		}
	}

}
