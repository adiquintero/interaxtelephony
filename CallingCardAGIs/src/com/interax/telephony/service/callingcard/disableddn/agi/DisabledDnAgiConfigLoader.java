package com.interax.telephony.service.callingcard.disableddn.agi;

import java.io.File;
import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;

public class DisabledDnAgiConfigLoader extends InteraxTelephonyConfigLoader {

	private static Hashtable<String, DisabledDnAgiConfig> config = null;
	private static String configPath;
	
	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "/services/";
		load();
	}
	
	public static void load(){
		config = new Hashtable<String, DisabledDnAgiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					DisabledDnAgiConfig promiscuousDnAgiConfig = (DisabledDnAgiConfig)loadConfig(files[i].replace(".properties", ""), configPath, DisabledDnAgiConfig.class);
					if (promiscuousDnAgiConfig != null)
						config.put(files[i].substring(0, files[i].lastIndexOf('.')), promiscuousDnAgiConfig);
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
	
	public static DisabledDnAgiConfig getConfig(String configName, String agiConfigDir){
		if(config==null || InteraxTelephonyConfig.checkReloadConfigFile(DisabledDnAgiConfig.class))
			init(agiConfigDir);
		return config.get(configName);
	}

 }
