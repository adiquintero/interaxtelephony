package com.interax.telephony.util;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class GeneralUtils {

	  public static Calendar dateString2Calendar(String s) throws ParseException {
			TimeZone.setDefault(TimeZone.getTimeZone("GMT0"));
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal=Calendar.getInstance();
		    Date d1=df.parse(s);
		    cal.setTime(d1);
		    return cal;
		  }
	  
	  public static String dateCalendar2String(Calendar c) throws ParseException {
			TimeZone.setDefault(TimeZone.getTimeZone("GMT0"));
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(c.getTime());
		  }
	  
	  public static Calendar dateLong2Calendar(Long l) throws ParseException {
		  TimeZone.setDefault(TimeZone.getTimeZone("GMT0"));
		  Calendar cal = Calendar.getInstance();
		  cal.setTimeInMillis(l);
		  return cal;
	  }
	  
	  
	  
	  
		public static String toText(Object object) {
			return toText(object, "\n", ":");
		}
	  
		public static String toText(Object object, String fieldSeparator, String valueSeparator) {
			return toText(object, fieldSeparator, valueSeparator, true);
		}
	  
		// PERMITIR PASAR EL PADDING (DEFAULT "  ") (OK)
		// PERMITIR PASAR SI ES RECURSIVO O NO (DEFAULT NO)
		// RECORRER LIST (OK, FALTA LISTA RECURSIVA)
		// RECORRER MAP (OK, FALTA MAP RECURSIVO)
		// HACER FIRMAS POLIMORFICAS
		// REVISAR 'FROM TEXT'
		
		public static String toText(Object object, String fieldSeparator, String valueSeparator, boolean calendarInMillis) {
			return toText(object, "\n", ":", calendarInMillis, 0, "  ");
		}

		@SuppressWarnings("unchecked")
		public static String toText(Object object, String fieldSeparator, String valueSeparator, boolean calendarInMillis, int level, String paddingSequence) {
			
			String padding = "";
			for(int i=0; i<level; i++) padding += paddingSequence;
			
			StringBuilder stringBuffer = new StringBuilder();
			
			stringBuffer.append(padding + "CLASS" + valueSeparator + object.getClass().getCanonicalName());
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
							if(calendarInMillis)
								attributeValue = "" + calendar.getTime().getTime();
							else
								attributeValue = calendar.getTime().toString();
						}
						else if(attribute instanceof Enum){
							attributeValue = attribute.toString();
						}
						else if(attribute instanceof List){
							attributeValue = "";
							int elementCount = 0;
							List elements = (List) attribute;
							for (Object element : elements) {
								attributeValue += fieldSeparator + padding + paddingSequence + "ELEMENT #" + (++elementCount) + fieldSeparator;
								attributeValue += toText(element, fieldSeparator, valueSeparator, calendarInMillis, level+2, paddingSequence);
							}
						}
//						else if(attribute instanceof Map){
//							attributeValue = "";
//							Map elements = (Map) attribute;
//						    Iterator it = elements.entrySet().iterator();
//						    while (it.hasNext()) {
//						        Map.Entry pairs = (Map.Entry)it.next();
//								attributeValue += fieldSeparator + padding + paddingSequence + "ELEMENT '" + pairs.getKey() + "'" + fieldSeparator;
//								attributeValue += toText(pairs.getValue(), fieldSeparator, valueSeparator, calendarInMillis, level+2, paddingSequence);
//						    }
//						}
						else{
							if(attribute.getClass().getPackage().toString().contains("com.interax.telephony")){
								attributeValue = fieldSeparator;
								attributeValue += toText(attribute, fieldSeparator, valueSeparator, calendarInMillis, level+1, paddingSequence);
							}
							else{
								attributeValue = attribute.toString();
							}
						}
						stringBuffer.append(fieldSeparator + padding + attributeName + valueSeparator + attributeValue);
					}
					catch(Exception e){
						stringBuffer.append(fieldSeparator + padding + attributeName + valueSeparator + "ERROR (" + e.getMessage() + ")");
					}
					
				}
			}
			return stringBuffer.toString();
		}
	  
	  
		@SuppressWarnings("unchecked")
		public static Object fromText(String text){
			HashMap<String, String> parsedData = parseText(text);
			try {
				Class clazz = Class.forName(parsedData.get("CLASS"));
				Object obj = clazz.newInstance();
				
				Method[] objectMethods = clazz.getMethods();
				int methodCount = objectMethods.length;
				for(int i=0; i<methodCount; i++){
					String methodName = objectMethods[i].getName();
					if(methodName.startsWith("set")){
						String attributeName = methodName.substring(3).toUpperCase();
						String attributeValue = parsedData.get(attributeName);
						try{
							Class<?> parametersTypes[] = objectMethods[i].getParameterTypes();
							String attributeType = parametersTypes[0].getName();
							Object arg = null;
							
							if(attributeType.equals("java.lang.String")){
								arg = attributeValue;
							}
							else if(attributeType.equals("java.lang.Integer") || attributeType.equals("int")){
								arg = Integer.parseInt(attributeValue);
							}
							else if(attributeType.equals("java.lang.Long") || attributeType.equals("long")){
								arg = Long.parseLong(attributeValue);
							}
							else if(attributeType.equals("java.util.Calendar")){
								Calendar calendar = Calendar.getInstance();
								calendar.setTimeInMillis(Long.parseLong(attributeValue));
								arg = calendar;
							
							}
							else{
								arg = null;
							}
							
							if(arg!=null){
								Object args[] = new Object[1];
								args[0] = arg;
								objectMethods[i].invoke(obj, args);
							}

						}
						catch(Exception e){
							return null;
						}
						
					}
				}
				
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
	    private static HashMap<String, String> parseText(String text){
	    	HashMap<String, String> parsedText = new HashMap<String, String>();
	    	String fields[] = text.split("\n");
	    	for(int i=0; i<fields.length; i++){
	    		String parsedField[] = fields[i].split(":");
	    		parsedText.put(parsedField[0], parsedField[1]);
	    	}
	    	return parsedText;
	    }
	  
	  
	  
	
	  public static void main(String[] args) {
		try {
			Calendar c = GeneralUtils.dateLong2Calendar(1274304466394L);
			System.out.println(GeneralUtils.dateCalendar2String(c));
			
			System.exit(0);
			String strDate = "2010-02-22 13:33:48";
			Calendar calendar = dateString2Calendar(strDate);
			System.out.println("STR: " + strDate);
			System.out.println("CALENDAR: " + calendar.toString());
			System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
			System.out.println("HOUR OF DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
			System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
			System.out.println("--------------------------------------------------------------------");

			strDate = "2010-02-22 00:33:48";
			calendar = dateString2Calendar(strDate);
			System.out.println("STR: " + strDate);
			System.out.println("CALENDAR: " + calendar.toString());
			System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
			System.out.println("HOUR OF DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
			System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
			System.out.println("--------------------------------------------------------------------");

			strDate = "2010-02-22 12:33:48";
			calendar = dateString2Calendar(strDate);
			System.out.println("STR: " + strDate);
			System.out.println("CALENDAR: " + calendar.toString());
			System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
			System.out.println("HOUR OF DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
			System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
			System.out.println("--------------------------------------------------------------------");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
