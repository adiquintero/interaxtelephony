package com.interax.persistence.common;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.contactmanager.date.ContactManagerDate;
import com.interax.date.InteraxDate;
import com.interax.logging.LoggerFactory;
import com.interax.persistence.datamanagers.DataManager;
import com.interax.utils.InteraxSerializedObject;
import java.util.List;

public class DBObject 
{
	static Logger log = LoggerFactory.getLogger(DataManager.class); 
	
	private Map<String,Object> values;
	private List<AttributeInfo> attributes;
	
	public DBObject(List<AttributeInfo> attributes, Map<String,Object> map)
	{
		this.attributes = attributes;
		this.values = map;
	}

	public DBObject(List<AttributeInfo> attributes, Object obj) throws Exception
	{
		this.attributes = attributes;
		this.values = convert(obj);
	}
	
	public DBObject(List<AttributeInfo> attributes, DynamicObject dynObj) throws Exception
	{
		this.attributes = attributes;
		this.values = convert(dynObj);
	}
	
	public Map<String,Object> getValues() {
		return values;
	}

	public void setValues(Map<String,Object> map) {
		this.values = map;
	}
	
	public List<AttributeInfo> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeInfo> attributes) {
		this.attributes = attributes;
	}
	

	@SuppressWarnings("unchecked")
	private Map<String,Object> convert(Object obj) throws Exception
	{
		Class theClass = obj.getClass();
		Method[] theMethods = theClass.getMethods();
		Map<String,Object> ret = new HashMap<String,Object>();
		for(int i=0;i<attributes.size();i++)
		{
		    for (int j=0;j<theMethods.length;j++)
		    {
		    	String methodString = theMethods[j].getName().toLowerCase();
		    	if((methodString.equals("get"+attributes.get(i).getName().toLowerCase()) || methodString.equals("is"+attributes.get(i).getName().toLowerCase())))
		    	{
		    		try
		    		{
		    			Object[] arguments = new Object[] {};
		    			//TODO check
//		    			if(false){
//		    				ret.put(attributes.get(i).getName(), (Object)theMethods[j].invoke(obj, arguments));
//		    			}
		    			ret.put(attributes.get(i).getName(), (Object)theMethods[j].invoke(obj, arguments));
		    		}
		    		catch(Exception e)
		    		{
		    			log.log(Level.SEVERE, "Error converting DBObject",e);
		    			throw e;
		    		}
		    	}
		    }			
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> convert(DynamicObject obj) throws Exception
	{
		Class theClass = obj.getClass();
		Method[] theMethods = theClass.getMethods();
		Map<String,Object> ret = new HashMap<String,Object>();
		for(int i=0;i<attributes.size();i++)
		{
			boolean isDynamic = true;
		    for (int j=0;j<theMethods.length;j++)
		    {
		    	String methodString = theMethods[j].getName().toLowerCase();
		    	if((methodString.equals("get"+attributes.get(i).getName().toLowerCase()) || methodString.equals("is"+attributes.get(i).getName().toLowerCase())))
		    	{
		    		try
		    		{
		    			Object[] arguments = new Object[] {};
		    			ret.put(attributes.get(i).getName(), (Object)theMethods[j].invoke(obj, arguments));
		    			isDynamic = false;
		    			break;
		    		}
		    		catch(Exception e)
		    		{
		    			log.log(Level.SEVERE, "Error converting DBObject",e);
		    			throw e;
		    		}
		    	}
		    }
		    if(isDynamic)
		    {
		    	ret.put(attributes.get(i).getName(), obj.getPropertyValue(attributes.get(i).getName()));
		    }
		}
		return ret;
	}
	
	
	
	public DBObject convertToDBObj(Object obj) throws Exception
	{
		return new DBObject(this.attributes,convert(obj));
	}


	@SuppressWarnings("unchecked")
	public Object convertToObj(Object obj) throws Exception
	{
		Class theClass = obj.getClass();
		Method[] theMethods = theClass.getMethods();
		String timeZoneId = null;

		timeZoneId = (String)values.get("timeZoneId");

		if (timeZoneId == null ){
			/** TODO check this validation */
		}

		for(int i=0;i<attributes.size();i++)
		{
			for (int j=0;j<theMethods.length;j++)
			{
				String methodString = theMethods[j].getName().toLowerCase();

				if(methodString.equals("set"+attributes.get(i).getName().toLowerCase()))
				{
					try
					{
						Object[] arguments = new Object[] {values.get(attributes.get(i).getName())};
						if(arguments[0] instanceof Blob)
						{
							String parameterType = theMethods[j].getGenericParameterTypes()[0].toString();
							Blob argumentBlob = (Blob) arguments[0];

							InputStream inputStream = argumentBlob.getBinaryStream();
							byte[] data = new byte[inputStream.available()];
							//	    				  int readedBytes = inputStream.read(data);
							inputStream.read(data);

							if(parameterType.endsWith("InteraxSerializedObject")){
								arguments[0] = new InteraxSerializedObject(data);
							}else if(parameterType.endsWith("String")){
								String tempString = new String(data);
								//		    				  log.log(Level.INFO, " ************* Leyendo data de un BLOB para un String: \n " +
								//			    				  		"readed bytes: "+readedBytes+" \n " +
								//			    				  		"data size: " + data.length+ " \n " + 
								//			    				  		"valor leido: " +tempString);
								arguments[0] = tempString;
							}else if(parameterType.endsWith("class [B")){ // means byte []
								arguments[0] = data;
							}else{
								inputStream = new ObjectInputStream(argumentBlob.getBinaryStream());
								arguments[0] = ((ObjectInputStream)inputStream).readObject();
							}
							inputStream.close();

						}
						else
						{
							String parameterType = theMethods[j].getGenericParameterTypes()[0].toString();
							if(parameterType.endsWith("Date")){
								String pattern = "";
								String argumentStr = arguments[0].toString();
								if(argumentStr.matches(InteraxDate.DATETIME_REGEX)) 
									pattern = InteraxDate.MySQLPatternDateTime;
								else
									if(argumentStr.matches(InteraxDate.DATE_REGEX))
										pattern = InteraxDate.MySQLPatternDate;
									else if(argumentStr.matches(InteraxDate.TIME_REGEX))
										pattern = InteraxDate.MySQLPatternTime;
									else
										// Nunca debería ocurrir
										throw new Exception("Invalid date format");

								//FIXME SACAR DE AQUÍ EL CASO CONTACTMANAGER DATE
								if(theMethods[j].getGenericParameterTypes()[0].toString().endsWith("InteraxDate"))
									arguments[0] = new InteraxDate(argumentStr, timeZoneId );
								else if(theMethods[j].getGenericParameterTypes()[0].toString().endsWith("ContactManagerDate"))
									arguments[0] = new ContactManagerDate(argumentStr, timeZoneId );
								else {
									DateFormat formatter = new SimpleDateFormat(pattern);
									if(argumentStr.startsWith("0000-00-00 00:00:00") || argumentStr.startsWith("0002-11-30 00:00:00"))
										arguments[0] = null;
									else
										arguments[0] = (Date)formatter.parse(argumentStr);

								}
							}
							else if(parameterType.endsWith("Calendar")){
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat(InteraxDate.MySQLPatternDateTime);
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(simpleDateFormat.parse(arguments[0].toString()));
								arguments[0] = calendar;
							}
						}

						theMethods[j].invoke(obj, arguments);
						break;
					}
					catch(IllegalArgumentException e)
					{
						//TODO MEJORAR
						// TIPS:
						// && Class.forName(theMethods[j].getGenericParameterTypes()[0].getClass().getName()).isInstance(values.get(attributes.get(i).getName()))) 
						// && theMethods[j].getGenericParameterTypes()[0].getClass().isInstance(values.get(attributes.get(i).getName())))
						continue;
					}
					catch(Exception e)
					{
						log.log(Level.SEVERE, "Error converting DBObject",e);
						throw e;
					}
				}
			}			
		}
		return obj;
	}
	
	
	@SuppressWarnings("unchecked")
	public DynamicObject convertToDynObj(DynamicObject dynObj) throws Exception
	{
		Class theClass = dynObj.getClass();
		Method[] theMethods = theClass.getMethods();
		String timeZoneId = null;
		
		timeZoneId = (String)values.get("timeZoneId");
		
		if (timeZoneId == null ){
			/** TODO check this validation */
		}
		
		for(int i=0;i<attributes.size();i++)
		{
			boolean isDynamic = true;
		    for (int j=0;j<theMethods.length;j++)
		    {
		    	String methodString = theMethods[j].getName().toLowerCase();
		    	if(methodString.equals("set"+attributes.get(i).getName().toLowerCase()))
		    	{
		    		try
		    		{
		    			Object[] arguments = new Object[] {values.get(attributes.get(i).getName())};

		    			if(theMethods[j].getGenericParameterTypes()[0].toString().endsWith("InteraxDate")){
		    				
		    				String argumentStr = arguments[0].toString();
		    		        if(argumentStr.matches(InteraxDate.DATETIME_REGEX)) 
		    		        	arguments[0] = new InteraxDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(arguments[0]), timeZoneId );
		    		        else
		    		            if(argumentStr.matches(InteraxDate.DATE_REGEX))
			    		        	arguments[0] = new InteraxDate(new SimpleDateFormat("yyyy-MM-dd").format(arguments[0]), timeZoneId );
		    		            else if(argumentStr.matches(InteraxDate.TIME_REGEX))
			    		        	arguments[0] = new InteraxDate(new SimpleDateFormat("HH:mm:ss").format(arguments[0]), timeZoneId );
		    		            else
		    		            	// Nunca debería ocurrir
		    		            	throw new Exception("Invalid date format");
		    			}
		    			else if(theMethods[j].getGenericParameterTypes()[0].toString().endsWith("ContactManagerDate")){
		    				
		    				String argumentStr = arguments[0].toString();
		    		        if(argumentStr.matches(ContactManagerDate.DATETIME_REGEX)) 
		    		        	arguments[0] = new ContactManagerDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(arguments[0]), timeZoneId );
		    		        else
		    		            if(argumentStr.matches(ContactManagerDate.DATE_REGEX))
			    		        	arguments[0] = new ContactManagerDate(new SimpleDateFormat("yyyy-MM-dd").format(arguments[0]), timeZoneId );
		    		            else if(argumentStr.matches(ContactManagerDate.TIME_REGEX))
			    		        	arguments[0] = new ContactManagerDate(new SimpleDateFormat("HH:mm:ss").format(arguments[0]), timeZoneId );
		    		            else
		    		            	// Nunca debería ocurrir
		    		            	throw new Exception("Invalid date format");
		    			}
	    			 	else if(theMethods[j].getGenericParameterTypes()[0].toString().endsWith("Date")){
	    					String argumentStr = arguments[0].toString();
	    			 		DateFormat formatter = new SimpleDateFormat(InteraxDate.MySQLPatternDateTime);
	    			 		arguments[0] = (Date)formatter.parse(argumentStr);
	    			 	}

		    			theMethods[j].invoke(dynObj, arguments);
		    			isDynamic = false;
		    			break;
		    		}
		    		catch(Exception e)
		    		{
		    			log.log(Level.SEVERE, "Error converting DBObject",e);
		    			throw e;
		    		}
		    	}
		    }
		    if(isDynamic)
		    {
		    	String name = attributes.get(i).getName();
		    	
		    	DynamicAttribute dynAttr = dynObj.getProperty(name);
		    	if(dynAttr!=null){
		    		//FIXME SACAR DE AQUÍ EL CONTACT MANAGER
			    	if(dynAttr!=null && "date".equals(dynAttr.getDataType())){
			    		dynObj.setPropertyValue(name,new ContactManagerDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(values.get(name)), timeZoneId ));
			    	}else{
			    		dynObj.setPropertyValue(name,values.get(name));
			    		/*if("boolean".equals(dynAttr.getDataType()))
			    		{
			    			if("1".equals(values.get(name)))
			    				dynObj.setPropertyValue(name, "true");
			    			else
			    				dynObj.setPropertyValue(name, "false");
			    		}*/
			    		;
			    	}
		    	}
		    }
		}
		return dynObj;
	}
	
	
}

