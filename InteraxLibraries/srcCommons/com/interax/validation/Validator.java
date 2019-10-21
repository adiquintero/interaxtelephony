package com.interax.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator 
{
	
	public final static String EMAIL_REGEX 	 	=	"^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?";
	public final static String HARD_EMAIL_REGEX = 	"[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum)\\b";

	/**
	* @param Regular expression
	* @param Pattern to check against the regular expression
	* @return True if the pattern is valid against the regular expression and else false
	*/
	private static boolean isValidEmail(String sRegEx, String sCheckPattern) {
		Pattern p = Pattern.compile(sRegEx);
		Matcher m = p.matcher(sCheckPattern);
		return m.matches();
	}
	
	public static boolean checkEmail(String email)
	{
	      return isValidEmail(EMAIL_REGEX, email);
	}
	
	
	public static boolean checkInt(Object obj)
	{
		try
		{
			String str = obj+"";
			Integer.parseInt(str);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static boolean checkLong(Object obj)
	{
		try
		{
			String str = obj+"";
			Long.parseLong(str);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static boolean checkDouble(Object obj)
	{
		try
		{
			String str = obj+"";
			Double.parseDouble(str);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static boolean checkFloat(Object obj)
	{
		try
		{
			String str = obj+"";
			Float.parseFloat(str);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static boolean checkWord(String str)
	{
		Pattern p = Pattern.compile("\\w+{1}");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	
	// TODO add this to a JUnit Test
	public static void main(String[] args) {

		
		String testValues [] = {
				"amarin@interaxmedia.com","amarin84@123.com.ve","04081984@192.168.3.93.com",
				"AMARIN@interaxmedia.com",".@interaxmedia.com","Amarin@.interaxmedia.com",".@.interaxmedia.com",
				"AMARIN_@interaxmedia.com","progr_amador84@interaxmedia.com","hola@_interaxmedia.com","hola@interax_media.com","hola_@_interaxmedia.com",
				"AMARIN-@guion.com","guion-guion@guion.com","guion@-guion.com","guion@guion-guion.com","guion-@-guion.com" 
				};
		
		for (int i = 0; i < testValues.length; i++) {
		
			System.out.println("Evaluating: "+testValues[i]);
			if (Validator.isValidEmail(EMAIL_REGEX, testValues[i])) {
				System.out.println("\tEMAIL_REGEX: Valid email address!");
			} else {
				System.err.println("\tEMAIL_REGEX: Invalid email address!");
			}
			/*
			if (Validator.isValidEmail(HARD_EMAIL_REGEX, testValues[i])) {
				System.out.println("\tHARD_EMAIL_REGEX: Valid email address!");
			} else {
				System.err.println("\tHARD_EMAIL_REGEX: Invalid email address!");
			}*/
		}
	}
}