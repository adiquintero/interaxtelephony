package com.interax.telephony.service.voicetraffic.agi.von;

import java.io.File;
//import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;
import java.util.HashMap;


public class VoiceTrafficVonAgiConfigLoader extends InteraxTelephonyConfigLoader {

	private static HashMap<Long, VoiceTrafficVonAgiConfig> config = null;
	private static String configPath;
	

	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "/enterprises/";
		load();
	}
	
	public static void load(){
		
		config = new HashMap<Long, VoiceTrafficVonAgiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					VoiceTrafficVonAgiConfig voiceTrafficVonAgiConfig = (VoiceTrafficVonAgiConfig)loadConfig(files[i].replace(".properties", ""), configPath, VoiceTrafficVonAgiConfig.class);
					Long enterpriseId = voiceTrafficVonAgiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, voiceTrafficVonAgiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static VoiceTrafficVonAgiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null || InteraxTelephonyConfig.checkReloadConfigFile(VoiceTrafficVonAgiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}
	
}
