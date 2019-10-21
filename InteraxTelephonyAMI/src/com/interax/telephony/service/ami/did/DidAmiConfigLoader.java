package com.interax.telephony.service.ami.did;

import java.io.File;
import java.util.Hashtable;

import com.interax.telephony.service.ami.did.DidAmiConfig;
import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;

public class DidAmiConfigLoader extends InteraxTelephonyConfigLoader {


	private static Hashtable<Long, DidAmiConfig> config = null;
	private static String configPath;
	

	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "enterprises/";
		load();
	}
	

	public static void load(){
		config = new Hashtable<Long, DidAmiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					DidAmiConfig didAmiConfig = (DidAmiConfig)loadConfig(files[i].replace(".properties", ""), configPath, DidAmiConfig.class);
					Long enterpriseId = didAmiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, didAmiConfig);
				}
			}
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static DidAmiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null|| InteraxTelephonyConfig.checkReloadConfigFile(DidAmiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}

}
