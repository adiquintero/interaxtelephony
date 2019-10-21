package com.interax.telephony.service.ami.callingcard;

import java.io.File;
import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;

public class CallingCardAmiConfigLoader extends InteraxTelephonyConfigLoader {
	
	private static Hashtable<Long, CallingCardAmiConfig> config = null;
	private static String configPath;
	
	public static void init(String path){
		configPath = path + "enterprises/";
		load();
	}
	
	public static void load(){
		config = new Hashtable<Long, CallingCardAmiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					CallingCardAmiConfig callingCardAmiConfig = (CallingCardAmiConfig)loadConfig(files[i].replace(".properties", ""), configPath, CallingCardAmiConfig.class);
					Long enterpriseId = callingCardAmiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, callingCardAmiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}

	public static CallingCardAmiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null || InteraxTelephonyConfig.checkReloadConfigFile(CallingCardAmiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}
	

	
}