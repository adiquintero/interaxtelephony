package com.interax.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.net.tftp.TFTPClient;


 
public class TftpUtils {
	
	private TFTPClient tftpClient;
	private String remoteHost;
	private int remotePort;
	private int transferMode;
	
	
	public static int ASCII_MODE = TFTPClient.ASCII_MODE;
	public static int BINARY_MODE = TFTPClient.BINARY_MODE;
	
	
	public TftpUtils(String remoteHost, int remotePort, int transferMode, int defaultTimeout){
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.transferMode = transferMode;

		this.tftpClient = new TFTPClient();
		this.tftpClient.setDefaultTimeout(defaultTimeout);
	}

	
	// Upload Methods
	
	public void streamUpload(InputStream inputStream, String remoteDirectory, String remoteFileName){

        try {
			String remotePath = remoteDirectory + remoteFileName;

			this.tftpClient.open();
			this.tftpClient.sendFile(remotePath, this.transferMode, inputStream, this.remoteHost, this.remotePort);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.tftpClient.close();
		}
	}

	public void fileUpload(File inputFile, String remoteDirectory, String remoteFileName) throws IOException{
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
		try {
			fileUpload(inputFile,remoteDirectory,remoteFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stringBufferUpload(StringBuffer inputStringBuffer, String remoteDirectory, String remoteFileName){
	    ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStringBuffer.toString().getBytes());
	    this.streamUpload(inputStream, remoteDirectory, remoteFileName);
	}

	
	
	// Download Methods
	
	public void streamDownload(OutputStream outputStream, String remoteDirectory, String remoteFileName){
		try {
			String remotePathOut = remoteDirectory + remoteFileName;
			
			this.tftpClient.open();
			this.tftpClient.receiveFile(remotePathOut, this.transferMode, outputStream, this.remoteHost, this.remotePort);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.tftpClient.close();
		}
	}
	
	public void fileDownload(File outputFile, String remoteDirectory, String remoteFileName){
		try {
			OutputStream outputStream = new FileOutputStream(outputFile);
		    this.streamDownload(outputStream, remoteDirectory, remoteFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fileDownload(String outputFileName, String remoteDirectory, String remoteFileName){
		File outputFile=new File(outputFileName);
		fileDownload(outputFile,remoteDirectory,remoteFileName);
	}
		
	public void stringBufferDownload(StringBuffer outputStringBuffer, String remoteDirectory, String remoteFileName){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(outputStringBuffer.length());
		this.streamDownload(outputStream, remoteDirectory, remoteFileName);
	}

		
	
	public static void main(String[] args) {
    	
    	String RemoteFilename, RemoteDirectory;
    	TftpUtils tftpUtils = new TftpUtils("localhost", 69, TFTPClient.ASCII_MODE, 5000);
    	
    	/******* EJEMPLO DE UPLOAD pruebaup al servidor ********
    	localFilename = "/home/yusmery/Desktop/pruebaup.txt";
    	RemoteDirectory = "/ejemplo/"; //No hay que colocarle el directorio completo solo el nombre de la carpeta
        RemoteFilename = "up.txt";
        tftpUtils.fileUpload(localFilename, RemoteDirectory, RemoteFilename);*/
        
        /******EJEMPLO Stream Upload ***
    	String fileString = "ARCHIVO DE PRUEBA STREAM";
		ByteInputStream fileStream=new ByteInputStream(fileString.getBytes(), fileString.length());
		RemoteDirectory = "/ejemplo/"; 
        RemoteFilename = "streamupload.txt";
    	tftpUtils.streamUpload(fileStream, RemoteDirectory, RemoteFilename);*/
        
    	/*****  EJEMPLO STREAM Download*******/
    	try {
			FileOutputStream file = new FileOutputStream("/home/yusmery/Desktop/prueba/hola.txt");
			DataOutputStream outputStream = new DataOutputStream(file);
	    	RemoteDirectory = "/tftpboot/ejemplo/"; 
	        RemoteFilename = "streamupload.txt";
	        tftpUtils.streamDownload(outputStream, RemoteDirectory, RemoteFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        /****** EJEMPLO DE DOWNLOAD pruebadown a la carpeta prueba del Desk ******
        RemoteDirectory = "/tftpboot/ejemplo/"; 
        RemoteFilename = "pruebadown.txt";
        outputFile = "/home/yusmery/Desktop/prueba/down.txt";
        tftpUtils.fileDownload(outputFile, RemoteDirectory, RemoteFilename);*/
        
     }

} 
