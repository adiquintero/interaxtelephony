package com.interax.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import com.interax.ftp.InteraxFtp;
import com.interax.ftp.genericftp.RemoteFile;
import com.interax.ftp.genericftp.SearchType;
import com.interax.ftp.genericftp.SearchTypeDate;

public class FtpTest {


	public static void caso1(String address, String keyWord, SearchType type){

		InteraxFtp ftp = new InteraxFtp();
		ftp.connect("joe", "123456", "127.0.0.1", 21);
		
		System.out.println("::Busqueda de archivos::");
		System.out.println("keyword="+keyWord);
		System.out.println("searchType="+type);
		
		List<RemoteFile> files = ftp.searchByFile(address,keyWord, type);

		for (int i = 0; i < files.size(); i++) {
			RemoteFile file = files.get(i);
			System.out.println("Nombre: "+ file.getNameFile());
		}

	}
	
	public static void caso2(String directory) throws FileNotFoundException{

		InteraxFtp ftp = new InteraxFtp();
		ftp.connect("joe", "123456", "127.0.0.1", 21);
		System.out.println("::Cambio de directorio remoto::");
		System.out.println("directorio actual="+ftp.currentPath());
		System.out.println("** directorio a ubicar="+directory);
		ftp.changeDir("ans");
		System.out.println("directorio actual="+ftp.currentPath());
		
	}
	
	public static void caso3(String remoteFileName) throws FileNotFoundException{

		InteraxFtp ftp = new InteraxFtp();
		ftp.connect("joe", "123456", "127.0.0.1", 21);
	
		System.out.println("::Descarga de archivo remoto a maquina local::");
		System.out.println("** Archivo remoto a descargar="+remoteFileName);
		System.out.println("Destino local="+"/home/joseluis/Documents/Mediador/Download/"+remoteFileName);
		
		
		File file = new File("/home/joseluis/Documents/Mediador/Download/"+remoteFileName);
		
		ftp.changeDir("ans");
//		ftp.download(new FileOutputStream(file), "/ans/"+remoteFileName);
		ftp.download(new FileOutputStream(file), remoteFileName);
		System.out.println("Descarga completada");
		
	}
	
	public static void caso4(Date dateA, SearchTypeDate type) throws FileNotFoundException{

		InteraxFtp ftp = new InteraxFtp();
		ftp.connect("joe", "123456", "127.0.0.1", 21);
		
		ftp.changeDir("ans");
		
		System.out.println("::Busqueda por fecha::");
		System.out.println("dateA="+dateA);
		System.out.println("searchTypeDate="+type.toString());
				
		List<RemoteFile> files = ftp.searchByDate(dateA, null, type);
		
		for (int i = 0; i < files.size(); i++) {
			RemoteFile file = files.get(i);
			System.out.println("Nombre: "+ file.getNameFile());
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {

		
		FtpTest.caso1("ans","C",SearchType.BEGIN);  // listado de nombres de archivos remotos
//		FtpTest.caso2("ans");  // cambiar directorio en servidor remoto
//		FtpTest.caso3("CH101025.010");  // descarga de archivo remoto
//		FtpTest.caso4(new Date(), searchTypeDate.GREATER); // fechas.
		
	}

}