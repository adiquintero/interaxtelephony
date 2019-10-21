package com.interax.security.utils;

import java.security.MessageDigest;

public class HashFunctions {
	public static String md5(String str)
    {
    	try
    	{
    		if(str==null)
        		return null;
        	byte[] defaultBytes = str.getBytes();

        	MessageDigest algorithm = MessageDigest.getInstance("MD5");
    	     algorithm.reset();
    	     algorithm.update(defaultBytes);
    	     byte messageDigest[] = algorithm.digest();
    	                    
    	     StringBuffer hexString = new StringBuffer();
    	     for (int i=0;i<messageDigest.length;i++) 
    	     {
    	    	 String hex = Integer.toHexString(0xFF & messageDigest[i]);
    	    	 if(hex!=null && hex.length()==1)
    	    		 hex = "0"+hex;
    	         hexString.append(hex);
    	     }
    	     return hexString.toString();
    	}
    	catch(Exception e)
    	{
    		return null;
    	}
    }
}
