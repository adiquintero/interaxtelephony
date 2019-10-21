package com.interax.remoting.security;
import java.io.Serializable;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

/**
 * The CipherManager class is used to encrypt and decrypt data (Java Objects) using a given secret key and the AES encryption algorithm. 
 * @author Vicente Robles Bango
 */
public class CipherManager
 {
  private Cipher cipher;
  
  /**
   * @param data - The Java Object to be encrypted
   * @param secretKey - The key used to encrypt the data 
   * @return A SealedObject that contains the original Java Object data
   */
 public SealedObject encrypt(Serializable data, SecretKey secretKey)
   {
	try
	 {
	  if(this.cipher==null) this.cipher = Cipher.getInstance("AES");
	  this.cipher.init(Cipher.ENCRYPT_MODE,secretKey);
	  return new SealedObject(data,this.cipher);
	 }
    catch(Exception e)
     {
      return null;
     }
   }

 /**
  * @param data - The Java SealedObject to be decrypted
  * @param secretKey - The key used to decrypt the data 
  * @return The original Java Object contained in the SealedObject data
  */
 public Object decrypt(SealedObject data, SecretKey secretKey)
   {
	try
	 {
	  if(this.cipher==null) this.cipher = Cipher.getInstance("AES");
	  this.cipher.init(Cipher.DECRYPT_MODE,secretKey);
	  return data.getObject(this.cipher); 
	 }
    catch(Exception e)
     {
      return null;
     }
   }

 }
