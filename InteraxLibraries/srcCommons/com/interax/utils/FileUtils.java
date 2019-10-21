package com.interax.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.contactmanager.date.ContactManagerDate;

public class FileUtils {

	
	
	public String fillWithZero(int number){
		String aux = ""+number;
		int digitsQuantity = 2;
		for (int i = aux.length(); i < digitsQuantity; i++) {
			aux = "0"+aux;
		}
		return aux;
		
	}
	
	
	public String uploadNameExtension(String path, String fileName, byte[] data){
		int random = (int) (Math.random()*100);
		try {
			String newFileName = new ContactManagerDate().format("yyyyMMddHHmmss")+fillWithZero(random);
			newFileName += fileName.substring(fileName.lastIndexOf("."));
			if(!path.endsWith("/"))
				path += "/";
			if(upload(path,newFileName,data))
				return newFileName;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * @param path: ruta real del servidor hasta el nombre de la aplicacion con su barra al final
	 * @param fileName: el nombre del archivo a subir
	 * @param data: tamaño del archivo en bytes 
	 * @return: true
	 * @throws Exception
	 */
	public boolean upload(String path, String fileName, byte[] data) throws Exception{
		
		String uploadFile = path + fileName;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(uploadFile));
		bos.write(data);
		bos.flush();
		bos.close();
		
		return true;
	}
	/**
	 * @param file: Archivo al cual se escribira la data. 
	 * @param data: tamaño del archivo en bytes 
	 * @param append : Si es true agrega al final del archivo 
	 * @return: true
	 * @throws Exception
	 */
	public boolean upload(File file, byte[] data, boolean append) throws Exception{
		BufferedOutputStream bos = null;
		if(!append){
			bos = new BufferedOutputStream(new FileOutputStream(file));
		}else{
			bos = new BufferedOutputStream(new FileOutputStream(file,append));
		}
		bos.write(data);
		bos.flush();
		bos.close();
		
		return true;
	}
	/**
	 * @param path: ruta real del servidor donde se encuentra el archivo a descargar 
	 * @param fileName: nombre del archivo a descargar
	 * @return: el tamaño del archivo en bytes
	 * @throws Exception
	 */
	public byte[] download(String path, String fileName) throws Exception{
		return download (path+fileName);
	}

	public byte[] download(String filePath) throws Exception{
		byte[] datos = new byte[0];

		FileInputStream archivo = new FileInputStream(filePath); 
		int longitud = archivo.available();
		datos = new byte[longitud];
		archivo.read(datos);
		archivo.close();

		return datos;
	}

}
