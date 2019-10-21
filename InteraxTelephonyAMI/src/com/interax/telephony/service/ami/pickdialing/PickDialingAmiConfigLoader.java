package com.interax.telephony.service.ami.pickdialing;

import java.io.File;
import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;

public class PickDialingAmiConfigLoader extends InteraxTelephonyConfigLoader {


	private static Hashtable<Long, PickDialingAmiConfig> config = null;
	private static String configPath;
	

	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "enterprises/";
		load();
	}
	

	public static void load(){
		config = new Hashtable<Long, PickDialingAmiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					PickDialingAmiConfig pickDialingAmiConfig = (PickDialingAmiConfig)loadConfig(files[i].replace(".properties", ""), configPath, PickDialingAmiConfig.class);
					Long enterpriseId = pickDialingAmiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, pickDialingAmiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static PickDialingAmiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null|| InteraxTelephonyConfig.checkReloadConfigFile(PickDialingAmiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}
	


}
