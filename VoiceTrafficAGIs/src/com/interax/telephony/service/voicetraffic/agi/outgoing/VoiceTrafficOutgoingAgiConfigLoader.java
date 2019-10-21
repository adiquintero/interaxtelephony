package com.interax.telephony.service.voicetraffic.agi.outgoing;

import java.io.File;
//import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfigLoader;
import java.util.HashMap;


public class VoiceTrafficOutgoingAgiConfigLoader extends InteraxTelephonyConfigLoader {

	private static HashMap<Long, VoiceTrafficOutgoingAgiConfig> config = null;
	
	public static void init(String path){
		String configPath = path + "/enterprises/";
		System.out.println(path + "/enterprises/");
		config = new HashMap<Long, VoiceTrafficOutgoingAgiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					VoiceTrafficOutgoingAgiConfig voiceTrafficOutgoingAgiConfig = (VoiceTrafficOutgoingAgiConfig)loadConfig(files[i].replace(".properties", ""), configPath, VoiceTrafficOutgoingAgiConfig.class);
					Long enterpriseId = voiceTrafficOutgoingAgiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, voiceTrafficOutgoingAgiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static VoiceTrafficOutgoingAgiConfig getConfig(long enterpriseId, String path){
		if(config==null)
			init(path);
		return config.get(enterpriseId);
	}
	
}
