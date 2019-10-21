package com.interax.telephony.service.voicetraffic.agi.forward;

import java.io.File;
//import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;
import java.util.HashMap;


public class VoiceTrafficForwardAgiConfigLoader extends InteraxTelephonyConfigLoader {

	private static HashMap<Long, VoiceTrafficForwardAgiConfig> config = null;
	private static String configPath;
	
	

	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "/enterprises/";
		load();
	}
	
	public static void load(){
	
		config = new HashMap<Long, VoiceTrafficForwardAgiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					VoiceTrafficForwardAgiConfig voiceTrafficForwardAgiConfig = (VoiceTrafficForwardAgiConfig)loadConfig(files[i].replace(".properties", ""), configPath, VoiceTrafficForwardAgiConfig.class);
					Long enterpriseId = voiceTrafficForwardAgiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, voiceTrafficForwardAgiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static VoiceTrafficForwardAgiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null|| InteraxTelephonyConfig.checkReloadConfigFile(VoiceTrafficForwardAgiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}
	
}
