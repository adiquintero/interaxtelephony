package com.interax.telephony.service.interactivevoiceresponse.agi.incoming;

import java.io.File;
//import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfigLoader;
import java.util.HashMap;


public class InteractiveVoiceResponseIncomingAgiConfigLoader extends InteraxTelephonyConfigLoader {

	private static HashMap<Long, InteractiveVoiceResponseIncomingAgiConfig> config = null;
	
	public static void init(String path){
		String configPath = path + "/enterprises/";
		System.out.println(path + "/enterprises/");
		config = new HashMap<Long, InteractiveVoiceResponseIncomingAgiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					InteractiveVoiceResponseIncomingAgiConfig ivrIncomingIvrAgiConfig = (InteractiveVoiceResponseIncomingAgiConfig)loadConfig(files[i].replace(".properties", ""), configPath, InteractiveVoiceResponseIncomingAgiConfig.class);
					Long enterpriseId = ivrIncomingIvrAgiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, ivrIncomingIvrAgiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static InteractiveVoiceResponseIncomingAgiConfig getConfig(long enterpriseId, String path){
		if(config==null)
			init(path);
		return config.get(enterpriseId);
	}
	
}
