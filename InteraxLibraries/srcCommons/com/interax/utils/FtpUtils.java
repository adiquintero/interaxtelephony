package com.interax.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPMessageCollector;
import com.enterprisedt.net.ftp.FTPTransferType;

 
public class FtpUtils {
	
	private FTPClient ftp;
	private FTPMessageCollector messageListener;
	private boolean isPassive = true;
	private boolean isBinary = true;
 
	public void connect(String remoteHost, int remotePort, String user, String password){
		try {
			this.messageListener = new FTPMessageCollector();
			this.ftp = new FTPClient();	
			this.ftp.setMessageListener(messageListener);

			this.ftp.setRemoteHost(remoteHost);
			this.ftp.setRemotePort(remotePort);
			this.ftp.connect();
			this.ftp.login(user, password);
			
			if(isBinary){
				this.ftp.setType(FTPTransferType.BINARY);
			}
			else{
				this.ftp.setType(FTPTransferType.ASCII);
			}
			
			if(isPassive){
				this.ftp.setConnectMode(FTPConnectMode.PASV);
			}
			else{
				this.ftp.setConnectMode(FTPConnectMode.ACTIVE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void disconnect(){
		try {
			ftp.quit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	public void rename(String filefrom, String fileto ){
		try {
			ftp.rename(filefrom, fileto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void delete(String file){
		try {
			ftp.delete(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void changeDir(String fileRemote){
		try {
			ftp.chdir(fileRemote);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
	public void streamUpload(InputStream inputStream, String remoteDirectory, String remoteFileName){
		try {
			ftp.chdir(remoteDirectory);
			ftp.put(inputStream, remoteFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void fileUpload(File inputFile, String remoteDirectory, String remoteFileName){
		try {
			InputStream inputStream = new FileInputStream(inputFile);
		    this.streamUpload(inputStream, remoteDirectory, remoteFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 }
	
	public void fileUpload(String inputFileName, String remoteDirectory, String remoteFileName){
			File inputFile=new File(inputFileName);
			fileUpload(inputFile,remoteDirectory,remoteFileName);
	 }
	
	public void stringBufferUpload(StringBuffer inputStringBuffer, String remoteDirectory, String remoteFileName){
			ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStringBuffer.toString().getBytes());
			this.streamUpload(inputStream, remoteDirectory, remoteFileName);
	}


//    public static void main(String[] args) {
//    	FtpUtils ftpUtils = new FtpUtils();
//    	ftpUtils.connect("localhost", 21, "mariet", "daniel");
//    	
//    	/******Prueba 1 Stream ****/
//    	String fileString = "ARCHIVO DE PRUEBA";
//		ByteInputStream fileStream=new ByteInputStream(fileString.getBytes(), fileString.length());
//    	ftpUtils.streamUpload(fileStream, ".", "/home/mariet/FTP-public/upload/prueba.txt");
//    	    	
//    	/*******Prueba 2 File ****/
//    	File output= new File("/home/mariet/Escritorio/aprovisionamiento/salida.txt");
//    	ftpUtils.fileUpload(output,".","/home/mariet/FTP-public/upload/prueba2.txt");
//    	    	
//    	
//    	/**** Prueba 5 Cambiar Directorio */
//    	ftpUtils.changeDir("/home/mariet/FTP-public/dowload");
//    	
//    	/*******Prueba 3 StringBuffer ****/
//    	StringBuffer fileBuffer = new StringBuffer(); 
//    	fileBuffer.append("<elem1>valor elem1 </elem1>\n <elem2>valor elem2 </elem2>\n <elem3>valor elem3 </elem3>");
//    	ftpUtils.stringBufferUpload(fileBuffer,".","/home/mariet/FTP-public/upload/prueba3.txt");
//    	
//    	ftpUtils.changeDir("../");
//    	/****Prueba 4 rename *****/
//    	ftpUtils.rename("/home/mariet/FTP-public/upload/prueba.txt", "/home/mariet/FTP-public/upload/epa.txt");
//    	
//    	/*** Prueba 6 Borrar ***/
//    	ftpUtils.delete("/home/mariet/FTP-public/upload/epa.txt");
//    	
//    	ftpUtils.disconnect();
//    }
    

}
