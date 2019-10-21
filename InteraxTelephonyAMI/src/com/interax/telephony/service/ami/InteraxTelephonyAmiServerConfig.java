package com.interax.telephony.service.ami;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.interax.telephony.util.InteraxTelephonyConfig;

public class InteraxTelephonyAmiServerConfig extends InteraxTelephonyConfig {

    public final String CONFIG_PATH;
    public final String AMIS_CONFIG_PATH;
    public final String ASTERISK_HOST;
    public final String ASTERISK_USER;
    public final String ASTERISK_PASSWORD;
    public final String PERSISTENCE_PATH;
    
	public InteraxTelephonyAmiServerConfig(ResourceBundle rb)  throws MissingResourceException, NumberFormatException {
		
		this.CONFIG_PATH = rb.getString("CONFIG_PATH");
		this.AMIS_CONFIG_PATH = rb.getString("AMIS_CONFIG_PATH");
		this.ASTERISK_HOST = rb.getString("ASTERISK_HOST");
		this.ASTERISK_USER = rb.getString("ASTERISK_USER");
		this.ASTERISK_PASSWORD = rb.getString("ASTERISK_PASSWORD");
		this.PERSISTENCE_PATH = rb.getString("PERSISTENCE_PATH");
	}
	
}
