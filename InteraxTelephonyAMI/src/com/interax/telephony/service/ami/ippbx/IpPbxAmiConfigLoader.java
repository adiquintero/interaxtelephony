package com.interax.telephony.service.ami.ippbx;

import java.io.File;
import java.util.Hashtable;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;

public class IpPbxAmiConfigLoader extends InteraxTelephonyConfigLoader {


	private static Hashtable<Long, IpPbxAmiConfig> config = null;
	private static String configPath;
	
	public static void init(String agiConfigDir){
		configPath = agiConfigDir + "enterprises/";
		load();
	}
	
	
	public static void load(){
		
		config = new Hashtable<Long, IpPbxAmiConfig>();
		File configDir = new File(configPath);
		if(configDir.exists()){
			String[] files = configDir.list();
			for(int i=0; i<files.length; i++){
				if(files[i].endsWith(".properties")){
					IpPbxAmiConfig ipPbxAmiConfig = (IpPbxAmiConfig)loadConfig(files[i].replace(".properties", ""), configPath, IpPbxAmiConfig.class);
					Long enterpriseId = ipPbxAmiConfig.ENTERPRISE_ID;
					config.put(enterpriseId, ipPbxAmiConfig);
				}
			}
			
		}
		else{
			//TODO: Lanzar excepción
		}
	}
	
	public static IpPbxAmiConfig getConfig(long enterpriseId, String agiConfigDir){
		if(config==null|| InteraxTelephonyConfig.checkReloadConfigFile(IpPbxAmiConfig.class))
			init(agiConfigDir);
		return config.get(enterpriseId);
	}
	


}
