package com.interax.telephony.util.ippbx;

import java.io.File;
import java.io.FileOutputStream;

public class IpPbxPhoneConfigEncryptionKeyGenerator {

	@SuppressWarnings("null")
	public static void main(String[] args) {

		String keyFileName = "/tmp/javaKey.txt";
		File keyFile = new File(keyFileName); 
		String keyString = null;
		
		if(keyFile.exists()){
			keyFile.delete();
		}
		
		try{
			keyFile.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(keyFile);
			
			//String keyString = "3523658935236589352365893523658935236589352365893523658935236589";
			
			
			byte keyBytes[] = new byte[64];
		byte firstNumber = '0';
			byte firstLetter = 'A' - 10;
			
		for(int i=0; i<64; i++){
				int randomNumber =  (int) (Math.random() * 16);
			if(randomNumber<10)
				keyBytes[i] = (byte) (firstNumber + randomNumber);
			else
				keyBytes[i] = (byte) (firstLetter + randomNumber);
			}
			
			keyBytes = keyString.getBytes();
				
				//
			
			fileOutputStream.write(keyBytes, 0, 64);
			System.out.println(new String(keyBytes));
			fileOutputStream.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
}
