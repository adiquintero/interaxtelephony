package com.interax.telephony.service.ami;

import com.interax.telephony.util.InteraxTelephonyConfig;
import com.interax.telephony.util.InteraxTelephonyConfigLoader;


public class InteraxTelephonyAmiServerConfigLoader extends InteraxTelephonyConfigLoader {

	protected String fileName;
	protected static InteraxTelephonyAmiServerConfig config = null;
	

	public static void load() {
		config = (InteraxTelephonyAmiServerConfig)loadConfig("amiserver", InteraxTelephonyAmiServerConfig.class);
	}
	
	public static InteraxTelephonyAmiServerConfig getConfig(){
		if(config == null|| InteraxTelephonyConfig.checkReloadConfigFile(InteraxTelephonyAmiServerConfig.class))
			load();
		return config;
	}
}
