package com.interax.telephony.server.agi;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.interax.telephony.util.InteraxTelephonyConfig;

public class InteraxTelephonyAgiServerConfig extends InteraxTelephonyConfig {


    public final int AGI_SERVER_PORT;
    public final int AGI_SERVER_POOL_SIZE;
    public final String CONFIG_PATH;
    public final String AGIS_CONFIG_PATH;
    public final String DEFAULT_INPUT_PREFIX;
    public final String DID_INCOMING_CONTEXT;
    public final String PERSISTENCE_PATH;
    public final String RATER_EJB_SERVER;
    public final String RATER_EJB_PORT;
    
	public InteraxTelephonyAgiServerConfig(ResourceBundle rb)  throws MissingResourceException, NumberFormatException {
		
		this.AGI_SERVER_PORT = Integer.parseInt(rb.getString("AGI_SERVER_PORT").trim());
		this.AGI_SERVER_POOL_SIZE = Integer.parseInt(rb.getString("AGI_SERVER_POOL_SIZE").trim());
		this.CONFIG_PATH = rb.getString("CONFIG_PATH").trim();
		this.AGIS_CONFIG_PATH = rb.getString("AGIS_CONFIG_PATH").trim();
		this.DEFAULT_INPUT_PREFIX = rb.getString("DEFAULT_INPUT_PREFIX").trim();
		this.DID_INCOMING_CONTEXT = rb.getString("DID_INCOMING_CONTEXT").trim();
		this.PERSISTENCE_PATH = rb.getString("PERSISTENCE_PATH").trim();
                this.RATER_EJB_SERVER = rb.getString("RATER_EJB_SERVER").trim();
                this.RATER_EJB_PORT = rb.getString("RATER_EJB_PORT").trim();
	}
    
    
	
}
