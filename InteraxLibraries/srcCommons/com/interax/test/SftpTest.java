package com.interax.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.interax.ftp.genericftp.RemoteFile;
import com.interax.ftp.genericftp.SearchType;
import com.interax.sftp.InteraxSftp;

public class SftpTest {

	public static String host = "192.168.3.33";
	public static String user = "root";
	public static String password = "r3p4tr14c10n$01";
	public static int port = 22;
	

	public static void caso1(String address){

		InteraxSftp sftp = new InteraxSftp();
		sftp.connect(user,password,host,port);
		List<RemoteFile> files = sftp.searchByFile(address,"CH10",SearchType.BEGIN);
		
		System.out.println("::SFTP busqueda de archivos remotos");

		for (int i = 0; i < files.size(); i++) {
			RemoteFile file = files.get(i);
			System.out.println("Nombre: "+ file.getNameFile());
		}

	}
	
	public static void caso2(String directory) throws FileNotFoundException{

		InteraxSftp sftp = new InteraxSftp();
		sftp.connect(user,password,host,port);
	
		System.out.println("::Cambio de directorio remoto::");
		System.out.println("directorio actual="+sftp.currentPath());
		System.out.println("** directorio a ubicar="+directory);
		sftp.changeDir("/var/cdr/ans/test");
		System.out.println("directorio actual="+sftp.currentPath());
		
	}
	
	public static void caso3(String remoteFileName) throws FileNotFoundException{

		InteraxSftp sftp = new InteraxSftp();
		sftp.connect(user,password,host,port);
		
		File file = new File("/home/joseluis/Documents/Mediador/Download/"+remoteFileName);
		
		sftp.changeDir("/var/cdr/ans/test");
		sftp.download(new FileOutputStream(file), remoteFileName);
		
	}
	
	public static void caso4(){

		InteraxSftp sftp = new InteraxSftp();
		sftp.connect(user,password,host,port);
		
		System.out.println("::SFTP directorio actual::");
		System.out.println("ruta="+sftp.currentPath());
		sftp.changeDir("/var/cdr/ans");
		System.out.println("ruta actual="+sftp.currentPath());
		
	}
	
	public static void caso5(String keyWord, SearchType type){

		InteraxSftp sftp = new InteraxSftp();
		sftp.connect(user,password,host,port);
		
		System.out.println("::SFTP Busqueda de archivos::");
		System.out.println("keyword="+keyWord);
		System.out.println("searchType="+type);
		
		sftp.changeDir("/var/cdr/ans/test");
		System.out.println("ruta actual="+sftp.currentPath());
		
		List<RemoteFile> files = sftp.searchByFile(keyWord, type);

		for (int i = 0; i < files.size(); i++) {
			RemoteFile file = files.get(i);
			System.out.println("Nombre: "+ file.getNameFile());
		}

	}

	public static void main(String[] args) throws FileNotFoundException {

		
		SftpTest.caso1("var/cdr/ans/test");  // listado de nombres de archivos remotos
//		SftpTest.caso2("ans");  // cambiar directorio en servidor remoto
//		SftpTest.caso3("CH101025.010");  // descarga de archivo remoto
		
//		SftpTest.caso4();
		SftpTest.caso5("2",SearchType.END);
		
	}

}
