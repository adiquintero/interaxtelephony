package com.interax.telephony.service.ami.voicetraffic;

import java.io.File;
import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;

public class VoiceTrafficAmiConfigLoader extends InteraxTelephonyConfigLoader {


	private static Hashtable<Long, VoiceTrafficAmiConfig> config = null;
	private static String configPath;
	

	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "enterprises/";
		load();
	}
	

	public static void load(){
		config = new Hashtable<Long, VoiceTrafficAmiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					VoiceTrafficAmiConfig voiceTrafficAmiConfig = (VoiceTrafficAmiConfig)loadConfig(files[i].replace(".properties", ""), configPath, VoiceTrafficAmiConfig.class);
					Long enterpriseId = voiceTrafficAmiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, voiceTrafficAmiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static VoiceTrafficAmiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null|| InteraxTelephonyConfig.checkReloadConfigFile(VoiceTrafficAmiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}
	


}
