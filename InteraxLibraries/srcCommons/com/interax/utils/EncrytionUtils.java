package com.interax.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class EncrytionUtils {

	private KeyGenerator keyGen; 
	private SecretKey secretKey;
	private static Cipher aesEncrypt;
	private static Cipher aesDecrypt;
	byte[] key="Hola".getBytes();
//	private static Cipher pkCipher;
	
	@SuppressWarnings("static-access")
	public EncrytionUtils(){
		
			try {
				this.keyGen= KeyGenerator.getInstance("AES");
				this.keyGen.init(128);
				this.secretKey = keyGen.generateKey();	        
				this.aesEncrypt= Cipher.getInstance("AES");
				this.aesEncrypt.init(Cipher.ENCRYPT_MODE,secretKey,aesEncrypt.getParameters());
				this.aesDecrypt=Cipher.getInstance("AES");
				this.aesDecrypt.init(Cipher.DECRYPT_MODE,secretKey,aesDecrypt.getParameters());
			
//			} catch (InvalidKeySpecException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private static void encript(InputStream inputDecryptedStream, OutputStream outputEcryptedStream){				
				
			try {
										
				byte[] byteDataToEncrypt = new byte[inputDecryptedStream.available()];
				inputDecryptedStream.read(byteDataToEncrypt);

				byte[] byteCipherText = aesEncrypt.doFinal(byteDataToEncrypt); 
				String cipherText = new BASE64Encoder().encode(byteCipherText);

				byte[] byteDataCipher=cipherText.getBytes();
				outputEcryptedStream.write(byteDataCipher);
				
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private static void decrypt(InputStream inputEcryptedStream, OutputStream outputDecryptedStream){
		
				try {
					byte[] byteDataToDecrypt = new BASE64Decoder().decodeBuffer(inputEcryptedStream);
					
					byte[] byteDecryptedText = aesDecrypt.doFinal(byteDataToDecrypt);
					String decryptedText = new String(byteDecryptedText);

					byte[] byteDataDecrypt=decryptedText.getBytes();
					outputDecryptedStream.write(byteDataDecrypt);
					
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	public static void fileEncrypt(String inputDecryptedString, String outputEncryptedString){
		
		File inputFile=new File(inputDecryptedString);
		File outputFile=new File(outputEncryptedString);	
		fileEncrypt(inputFile,outputFile);
    }
	
	public static void fileDecrypt(String inputEncryptedString, String outputDecryptedString){
		
		File inputFile=new File(inputEncryptedString);
		File outputFile=new File(outputDecryptedString);	
		fileDecrypt(inputFile,outputFile);
    }
	
	public static void fileEncrypt(File inputDecryptedFile, File outputEncryptedFile){
		try {
			InputStream inputStream = new FileInputStream(inputDecryptedFile);
			OutputStream outputStream = new FileOutputStream(outputEncryptedFile);
			encript(inputStream,outputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
	
	public static void fileDecrypt(File inputEncryptedFile, File outputDecryptedFile){
		try {
			InputStream inputStream = new FileInputStream(inputEncryptedFile);
			OutputStream outputStream = new FileOutputStream(outputDecryptedFile);
			decrypt(inputStream,outputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
	
	public static void stringBufferEncrypt(StringBuffer inputDecryptedStringBuffer, StringBuffer outputEncryptedStringBuffer){
		ByteArrayInputStream inputStream=new ByteArrayInputStream(inputDecryptedStringBuffer.toString().getBytes());
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		encript(inputStream,outStream);
		try {
			outStream.flush();
			outputEncryptedStringBuffer.append(outStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void stringBufferDecrypt(StringBuffer inputEncryptedStringBuffer, StringBuffer outputDecryptedStringBuffer){
		
		ByteArrayInputStream inputStream=new ByteArrayInputStream(inputEncryptedStringBuffer.toString().getBytes());
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		decrypt(inputStream,outStream);
		try {
			outStream.flush();
			outputDecryptedStringBuffer.append(outStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static String stringEncrypt(String inputDecrytedString){
		ByteArrayInputStream inputStream=new ByteArrayInputStream(inputDecrytedString.getBytes());
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		encript(inputStream,outputStream);
		return new String(outputStream.toByteArray());
    }

	public static String stringDecrypt(String inputEncrytedString){
		ByteArrayInputStream inputStream=new ByteArrayInputStream(inputEncrytedString.getBytes());
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		decrypt(inputStream,outputStream);
		return new String(outputStream.toByteArray());
    }
	
	
//	public static void main(String[] args) {
//		
//		EncrytionUtils encrypt=new EncrytionUtils();
//		
//		/***Prueba 1 String ****/
//		System.out.println("Prueba 1: String ");
//		String stringtoencrypt="Hola Vicente!!!";
//		String stringEncrypt=encrypt.stringEncrypt(stringtoencrypt);
//		System.out.println("Texto Encriptado:"+stringEncrypt);
//		String stringDecrypt=encrypt.stringDecrypt(stringEncrypt);
//		System.out.println("Texto Desencriptado:"+stringDecrypt+"\n");
//		
//		
//	//	/***Prueba 2 rutas/String ****/}
//		System.out.println("Prueba 2 Archivos: Lista!\n");
//		String fileEncrypt="/home/mariet/Escritorio/encriptar.txt";
//		String outputFile="/home/mariet/Escritorio/encrypt.txt";
//		encrypt.fileEncrypt(fileEncrypt,outputFile);
//		String fileDescrypt="/home/mariet/Escritorio/descrypt.txt";
//		encrypt.fileDecrypt(outputFile, fileDescrypt);
//		
//		/***Prueba 3 StringBuffer****/
//		System.out.println("Prueba 3 StringBuffer");
//		StringBuffer inputDecryptStringBuffer =new StringBuffer();
//		StringBuffer outputEncryptStringBuffer =new StringBuffer();
//		StringBuffer outputDecryptStringBuffer =new StringBuffer();
//		inputDecryptStringBuffer.append("Hola Vicente!!!");
//		encrypt.stringBufferEncrypt(inputDecryptStringBuffer,outputEncryptStringBuffer);
//		encrypt.stringBufferDecrypt(outputEncryptStringBuffer,outputDecryptStringBuffer);
//		
//		System.out.println("StringBufferEncrypt:" + outputEncryptStringBuffer.toString());
//		System.out.println("StringBufferDecrypt:" + outputDecryptStringBuffer.toString());
//	}
}
