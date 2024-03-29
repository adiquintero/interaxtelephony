package com.interax.telephony.service.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Title:        Encoder <p>
 * Description:	 Codificador. Clase encargada de gestionar todos los servicios 
 * 				 de encripción para el sistema. <p> 
 * Copyright:    .<p>
 * Company:      Interaxmedia<p>
 *
 * @author Héctor Mártinez (HM)
 * @version 1.0
 * @since JDK1.6
 * 		  Created: 11-Agosto-2008
 */
public class Encoder {
	/**
	 * Codifica los bytes en formato Hexadecimal.
	 * @param data	Datos a codificar.
	 * @return java.lang.String
	 */
	private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	int halfbyte = (data[i] >>> 4) & 0x0F;
        	int two_halfs = 0;
        	do {
	        	if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	            	buf.append((char) ('a' + (halfbyte - 10)));
	        	halfbyte = data[i] & 0x0F;
        	} while(two_halfs++ < 1);
        }
        return buf.toString();
    }
 
	/**
	 * Realiza una codificación Hash MD5 al texto pasado por parámetro.
	 * @param text	Texto a codificar.
	 * @return java.lang.String
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String MD5(String text) 
		throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		md5hash = md.digest();
		return convertToHex(md5hash);
	}
}
