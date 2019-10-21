package com.interax.persistence.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.Vector;

public class PersistenceObjectManager {

	// Directorio utilizado para almacenar el respaldo 
	//private static String backupFolder = null;//"backup";
	
	
	protected static List<Object> loadObjects(String backupFolder, String prefix)
	 {
	  List<Object> loadedObjects = new ArrayList<Object>();
	  File backupDir = new File(backupFolder);
	  if(!backupDir.exists()) backupDir.mkdir();
	  
	  String[] fileList = backupDir.list();
	  Arrays.sort(fileList);
	  
	  for(int i=0; i<fileList.length; i++)
	   {
		if(fileList[i].startsWith(prefix))
		 {
		  loadedObjects.add(loadObject(backupFolder, fileList[i]));
		 }
	   }
	  
	  return loadedObjects;
	 }
	
	protected static Object loadObject(String backupFolder, String filename)
	{
		ObjectInputStream in = null;

		try
		{
			File backupFile = new File(backupFolder, filename);
			in = new ObjectInputStream (new FileInputStream (backupFile));
			return in.readObject();
		}
		catch (IOException ioe)
		{
			// ioe.printStackTrace();
			return null;
		}
		catch (ClassNotFoundException cnfe)
		{
			return null;
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close ();
				}
				catch (IOException exception)
				{
					return null;
				}
			}
		}
	}

	
	protected static boolean saveObject(String backupFolder, Object obj, String filename)
	{
		ObjectOutputStream out = null;

		try
		{
			File backupFile = new File(backupFolder, filename);
			out = new ObjectOutputStream (new FileOutputStream (backupFile));
			out.writeObject(obj);
			out.flush ();
		}
		catch (IOException exception)
		{
			return false;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close ();
				}
				catch (IOException exception)
				{
					return false;
				}
			}
		}
		return true;
	}


	protected static boolean deleteObject(String backupFolder, String filename)
	{
		File backupFile = new File(backupFolder, filename);
		return backupFile.delete();
	}
	
}