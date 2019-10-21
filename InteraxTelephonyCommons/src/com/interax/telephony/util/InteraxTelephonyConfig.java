package com.interax.telephony.util;

import java.io.File;

public abstract class InteraxTelephonyConfig {
	
	private static String RELOAD_CONFIG_FILES_PATH = "/tmp/";

	/* UTIL */
	@SuppressWarnings("unchecked")
	public static void writeReloadConfigFile(Class clazz){
		try {
			File file = new File(RELOAD_CONFIG_FILES_PATH + clazz.getSimpleName() + ".reload");
			if(!file.exists())
			file.createNewFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static boolean checkReloadConfigFile(Class clazz){
		try {
			File file = new File(RELOAD_CONFIG_FILES_PATH + clazz.getSimpleName() + ".reload");
			if(file.exists()){
				file.delete();
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
	}
}
