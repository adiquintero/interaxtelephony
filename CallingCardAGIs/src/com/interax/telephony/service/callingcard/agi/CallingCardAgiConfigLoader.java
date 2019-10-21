package com.interax.telephony.service.callingcard.agi;

import java.io.File;
import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;

public class CallingCardAgiConfigLoader extends InteraxTelephonyConfigLoader {

	private static Hashtable<String, CallingCardAgiConfig> config = null;
	private static String configPath;
	
	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "/services/";
		load();
	}
	
	public static void load(){
		config = new Hashtable<String, CallingCardAgiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					CallingCardAgiConfig callingCardAgiConfig = (CallingCardAgiConfig)loadConfig(files[i].replace(".properties", ""), configPath, CallingCardAgiConfig.class);
					if (callingCardAgiConfig != null)
						config.put(files[i].substring(0, files[i].lastIndexOf('.')), callingCardAgiConfig);
//					int dnidCount = callingCardAgiConfig.DNIDS.size();
//					for(int j=0; j<dnidCount; j++)
//						config.put(callingCardAgiConfig.DNIDS.get(j), callingCardAgiConfig);
				}
			}
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static CallingCardAgiConfig getConfig(String configName, String agiConfigDir){
		if(config==null || InteraxTelephonyConfig.checkReloadConfigFile(CallingCardAgiConfig.class))
			init(agiConfigDir);
		return config.get(configName);
	}

 }
