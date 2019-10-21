package com.interax.security.utils;

import java.util.Random;

public class PasswordGenerator {

	private static final String DIGITS_PATTERN = "n";
	private static final String ALPHAS_PATTERN = "c";
	private static final String ANY_CHARS_PATTERN = "*";
	private static final String DIGITS_AND_ALPHAS_PATTERN = "nc";
	private static final String ALPHAS_AND_DIGITS_PATTERN = "cn";
	
	
	/**
	 * This method generate random password mixing numbers and alpha chars
	 * @param length - password length
	 * @return 
	 * @throws Exception
	 */
	public static String generatePassword(int length) throws Exception {
		return generatePassword("*", length);
	}
	
	
	/**
	 * This method generate random password according with giving pattern, patterns are defined in static way in this class
	 * @param pattern - pattern
	 * @param length - password length
	 * @return
	 * @throws Exception
	 */
	public static String generatePassword(String pattern, int length) throws Exception {
		
		if(pattern != null && pattern.length() > 0 && length > 0){
		
			if(pattern.length() == 1){
				if(DIGITS_PATTERN.equalsIgnoreCase(pattern)){
					
					return generateRandomNumber(length);
					
				}else if(ALPHAS_PATTERN.equalsIgnoreCase(pattern)){
					
					return generateRandomAlpha(length);
					
				}else if(ANY_CHARS_PATTERN.equalsIgnoreCase(pattern)){
					
					String passwd = "";
					for (int i = 0; i < length; i++) {
						Random random = new Random();
						Double randomDouble = random.nextDouble();
						if(randomDouble>=0.5)
							passwd += generateRandomAlpha(1);
						else
							passwd += generateRandomNumber(1);
					}
					return passwd;
				}
			}else if(DIGITS_AND_ALPHAS_PATTERN.equalsIgnoreCase(pattern) || ALPHAS_AND_DIGITS_PATTERN.equalsIgnoreCase(pattern)){
				/*
				 * TODO por ahora se usa la mitad de la longitud, pero se pueden agregar mas flexibles
				 * nnnccc, nnnncc, y cualquier otro tipo de combinacion 
				 */
				int startLength = 0;
				int endLength = 0;
				
				if((int)(length % 2) == 0){
					endLength = startLength = (length / 2);
				}else{
					startLength = (endLength = (length / 2)) + 1;
				}
				
//				System.out.println(" start: " + startLength + " end: " + endLength);
				
				if(pattern.startsWith("c")){
					String randomAlpha = generateRandomAlpha(startLength);
					String randomNumber = generateRandomNumber(endLength);
					return randomAlpha + randomNumber;
				}else{
					String randomNumber = generateRandomNumber(startLength);
					String randomAlpha = generateRandomAlpha(endLength);
					return randomNumber + randomAlpha;
				}
			}
		}else {
			throw new IllegalArgumentException("Pattern should be != null AND length() > 0 ");
		}
		
		throw new Exception("Invalid pattern...");
	}

	
	/**
	 * This method return a string which contains random numbers
	 * @param length, string will have this length
	 * @return
	 */
	private static String generateRandomNumber(int length){
		Random random = new Random();
		Integer randomNumber;
		int lowerLimit = (int) Math.pow(10L, length-1);
		int upperLimit = (int) Math.pow(10L, length);
		do{
			Double randomDouble = random.nextDouble();
			randomNumber = (int) (randomDouble * upperLimit);
		}
		while(randomNumber<lowerLimit || randomNumber>=upperLimit);
		return randomNumber.toString();
	}

	/**
	 * This method return a string which contains alpha chars
	 * @param length, string will have this length
	 * @return
	 */
	private static String generateRandomAlpha(int length){
		Random random = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		StringBuffer randomAlpha = new StringBuffer();
		while(randomAlpha.length()<length){
			int position = random.nextInt(characters.length());
			randomAlpha.append(characters.charAt(position));
		}

		return randomAlpha.toString();
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.println();
		System.out.println(" *********** INIT TEST ************ ");
		System.out.println();
		
		// Testing errors parameters
		try {
			System.out.println(generatePassword(" ",8));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(generatePassword("n",0));
		}catch (Exception e) {
			e.printStackTrace();
		}

		
		System.out.println();
		System.out.println(" *********** CASES ************ ");
		System.out.println();
		
		// Testing good parameters
		try {
			
			System.out.println(generatePassword(8));
			System.out.println(" *********** UNIQUE ************ ");
			System.out.println(generatePassword("n",8));
			System.out.println(generatePassword("n",7));
			System.out.println(generatePassword("c",8));
			System.out.println(generatePassword("c",7));
			
			System.out.println(" *********** MIX ************ ");
			System.out.println(generatePassword("nc",8));
			System.out.println(generatePassword("nc",7));
			System.out.println(generatePassword("cn",8));
			System.out.println(generatePassword("cn",7));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println(" *********** END TEST ************ ");
		System.out.println();
	}
}
