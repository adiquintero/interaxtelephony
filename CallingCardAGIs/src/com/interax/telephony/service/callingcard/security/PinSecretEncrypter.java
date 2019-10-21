package com.interax.telephony.service.callingcard.security;



public class PinSecretEncrypter {
	
	private static final int RANDOM_FIELD_LENGTH = 4; 
	public static final int ENCRYPTED_SECRET_LENGTH = RANDOM_FIELD_LENGTH + 32; 

	public static String encryptPinSecret(String secret, String userIp) {
		
		try{
			secret = paddingString(secret, 20, '-', false);
			int randomNumber = (int) ((Math.random() * 10000) % 10000);
			String randomField = "" + paddingString("" + randomNumber,4,'0',true);
			String encryptionKey = paddingString("cc" + randomField + userIp.replace(".",""), 24, 'x', false); ;
			StringEncrypter stringEncrypter = new StringEncrypter(StringEncrypter.DESEDE_ENCRYPTION_SCHEME, encryptionKey);
			String encryptedSecret = stringEncrypter.encrypt(secret);
			return randomField + encryptedSecret;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public static String decryptPinSecret(String encryptedSecret, String userIp) {
		
		try{
			String randomField = encryptedSecret.substring(0,RANDOM_FIELD_LENGTH);
			encryptedSecret = encryptedSecret.substring(RANDOM_FIELD_LENGTH);
			String encryptionKey = paddingString("cc" + randomField + userIp.replace(".",""), 24, 'x', false); ;
			StringEncrypter stringEncrypter = new StringEncrypter(StringEncrypter.DESEDE_ENCRYPTION_SCHEME, encryptionKey);
			String decryptedSecret = stringEncrypter.decrypt(encryptedSecret);
			return decryptedSecret.replace("-", "");
		}
		catch (Exception e) {
			return null;
		}
	}
	
    private static String paddingString(String s, int n, char c, boolean paddingLeft) {
	    StringBuffer str = new StringBuffer(s);
	    int strLength  = str.length();
	    if ( n > 0 && n > strLength ) {
	      for ( int i = 0; i <= n ; i ++ ) {
	            if ( paddingLeft ) {
	              if ( i < n - strLength ) str.insert( 0, c );
	            }
	            else {
	              if ( i > strLength ) str.append( c );
	            }
	      }
	    }
	    return str.toString();
    }
	
	public static void main(String[] args){
		
			String userIp = "127.0.0.1";
			String plainSecret = "1234567812345678";
			System.out.println(plainSecret);
			String encryptedSecret = encryptPinSecret(plainSecret, userIp);
			System.out.println(encryptedSecret);
			encryptedSecret = encryptPinSecret(plainSecret, userIp);
			System.out.println(encryptedSecret.length());
			encryptedSecret = encryptPinSecret(plainSecret, userIp);
			System.out.println(encryptedSecret);
			String decryptedSecret = decryptPinSecret(encryptedSecret, userIp);
			System.out.println(decryptedSecret);
			userIp = "192.168.3.88";
			decryptedSecret = decryptPinSecret(encryptedSecret, userIp);
			System.out.println(decryptedSecret);

	}
	
}
