package com.interax.security.utils;

//import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
//import java.util.Hashtable;

public class ParamEncrypter {

	public static String encryptParams(HashMap<String,String> decryptedParams, String encryptionScheme, String encryptionKey){
		try{
			String decryptedParamString = "";
                        Set<String> paramNames = decryptedParams.keySet();
                        Iterator<String> iterator = paramNames.iterator();
			while(iterator.hasNext()){
				String name = (String)iterator.next();
				String value = decryptedParams.get(name);
				decryptedParamString += name + "=" + value;
				if(iterator.hasNext()) decryptedParamString += ",";
			}
			
			return encryptParams(decryptedParamString, encryptionScheme, encryptionKey);
		}
		catch(Exception e){
			return null;
		}		   
	}
	
	public static String encryptParams(String decryptedParams, String encryptionScheme, String encryptionKey){
		try{
			StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
			String encryptedParamString = encrypter.encrypt(decryptedParams);
			return encryptedParamString;
		}
		catch(Exception e){
			return null;
		}		   
	}
	
	public static HashMap<String,String> decryptParams(String encryptedParams, String encryptionScheme, String encryptionKey){
		try{
			StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
			String decryptedParamString = encrypter.decrypt(encryptedParams);
			  
			String paramArray[] = decryptedParamString.split(",");
			HashMap<String, String> params = new HashMap<String, String>();
			for(int i=0; i<paramArray.length; i++){
				String[] splittedParam = paramArray[i].split("=");
				params.put(splittedParam[0], splittedParam[1]);
			}
		  
			return params;
		}
		catch(Exception e){
			return null;
		}
	}
	
}
