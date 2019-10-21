package com.interax.logging;

import java.io.InputStream;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public final class  LoggerFactory 
{
	private static HashMap<String, String> logFiles;
	private static HashMap<String, String> logLevels;
	private static HashMap<String, FileHandler> handlers;
	private static HashMap<String, GenericLogger> loggers;
	private int limitSize; 
	private int rollinCount; 
	
	@SuppressWarnings("unchecked")
	private LoggerFactory(Class clazz)
	{
		try 
		{
			if(logFiles==null) logFiles = new HashMap<String, String>();
			if(logLevels==null) logLevels = new HashMap<String, String>();

			Properties defaultProps = new Properties();
			InputStream in = clazz.getClassLoader().getResourceAsStream("/logging.properties");
			if(in == null) in = clazz.getResourceAsStream("/logging.properties");
			defaultProps.load(in);
			in.close();
	    	
			String fileName = defaultProps.getProperty("logger.filename");
			String level = (defaultProps.getProperty("logger.level")!=null) ? defaultProps.getProperty("logger.level").toUpperCase() : "ALL";
			if(!handlers.containsKey(fileName)){	
				limitSize = Integer.parseInt(defaultProps.getProperty("logger.size"))*1024*1024;
				rollinCount = Integer.parseInt(defaultProps.getProperty("logger.count"));
				FileHandler handler = new FileHandler(fileName, limitSize, rollinCount);
		        handler.setFormatter(new SimpleFormatter());
		        handlers.put(fileName, handler);
			}
			logFiles.put(clazz.getName(), fileName);
			logLevels.put(clazz.getName(), level);
	    } 
		catch (Exception e) 
		{
			System.out.print("Error creating logger handler.");
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static GenericLogger getLogger(Class clazz)
	{
		if(loggers==null) loggers = new HashMap<String, GenericLogger>();
		if(handlers==null) handlers = new HashMap<String, FileHandler>();
		 
		String className = clazz.getName();
		if(loggers.containsKey(className))
			return loggers.get(className);

		new LoggerFactory(clazz);
//		Logger logger = Logger.getLogger(clazz.getName());
		GenericLogger logger = new GenericLogger(clazz.getName());
		try
		{
			if(logger.getHandlers()==null || logger.getHandlers().length==0)
			{
				String level = logLevels.get(className);
				if(level.equals("SEVERE"))
					logger.setLevel(Level.SEVERE);
				else if(level.equals("WARNING"))
					logger.setLevel(Level.WARNING);
				else if(level.equals("INFO"))
					logger.setLevel(Level.INFO);
				else if(level.equals("CONFIG"))
					logger.setLevel(Level.CONFIG);
				else if(level.equals("FINE"))
					logger.setLevel(Level.FINE);
				else if(level.equals("FINER"))
					logger.setLevel(Level.FINER);
				else if(level.equals("FINEST"))
					logger.setLevel(Level.FINEST);
				else if(level.equals("OFF"))
					logger.setLevel(Level.OFF);
				else if(level.equals("ALL"))
					logger.setLevel(Level.ALL);

				
				logger.addHandler(handlers.get(logFiles.get(className)));	
				logger.setUseParentHandlers(false);
		        loggers.put(className, logger);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error getting Logger");
			e.printStackTrace();
		}
        return logger;
	}
}
