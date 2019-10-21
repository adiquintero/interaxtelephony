package com.interax.telephony.service.voicetraffic.agi.voipforward;

import java.io.File;
//import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;
import java.util.HashMap;


public class VoiceTrafficVoipForwardAgiConfigLoader extends InteraxTelephonyConfigLoader {

	private static HashMap<Long, VoiceTrafficVoipForwardAgiConfig> config = null;
	private static String configPath;
	
	

	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "/enterprises/";
		load();
	}
	
	public static void load(){
	
		config = new HashMap<Long, VoiceTrafficVoipForwardAgiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					VoiceTrafficVoipForwardAgiConfig voiceTrafficForwardAgiConfig = (VoiceTrafficVoipForwardAgiConfig)loadConfig(files[i].replace(".properties", ""), configPath, VoiceTrafficVoipForwardAgiConfig.class);
					Long enterpriseId = voiceTrafficForwardAgiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, voiceTrafficForwardAgiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static VoiceTrafficVoipForwardAgiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null|| InteraxTelephonyConfig.checkReloadConfigFile(VoiceTrafficVoipForwardAgiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}
	
}
