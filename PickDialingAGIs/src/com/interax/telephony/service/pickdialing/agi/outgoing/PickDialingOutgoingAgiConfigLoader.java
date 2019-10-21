package com.interax.telephony.service.pickdialing.agi.outgoing;

import java.io.File;
//import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;
import java.util.HashMap;


public class PickDialingOutgoingAgiConfigLoader extends InteraxTelephonyConfigLoader {

	private static HashMap<Long, PickDialingOutgoingAgiConfig> config = null;
	private static String configPath;

	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "/enterprises/";
		load();
	}
	
	public static void load(){
	
		config = new HashMap<Long, PickDialingOutgoingAgiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					PickDialingOutgoingAgiConfig pickDialingOutgoingAgiConfig = (PickDialingOutgoingAgiConfig)loadConfig(files[i].replace(".properties", ""), configPath, PickDialingOutgoingAgiConfig.class);
					Long enterpriseId = pickDialingOutgoingAgiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, pickDialingOutgoingAgiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static PickDialingOutgoingAgiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null|| InteraxTelephonyConfig.checkReloadConfigFile(PickDialingOutgoingAgiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}
	
}
