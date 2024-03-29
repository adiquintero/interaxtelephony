package com.interax.telephony.service.interactivevoiceresponse.agi.incoming;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.interax.telephony.util.InteraxTelephonyConfig;


public class InteractiveVoiceResponseIncomingAgiConfig extends InteraxTelephonyConfig {

	public final String DIAL_PROTOCOL;
	public final String DIAL_PREFIX;
	public final String DIAL_TEST_PREFIX;
	public final String DIAL_GATEWAY;
	public final String DIAL_TEST_GATEWAY;
	public final String DIAL_TIMEOUT;
	public final String RATER_EJB_SERVER;
	public final String RATER_EJB_PORT;
	public final String ENTERPRISE_NAME;
	public final Long ENTERPRISE_ID;
	public final int UNLIMITED_CALLS_MAX_MINUTES;
	public final int UNLIMITED_CALLS_RANDOM_MINUTES;
	
	public final String EJB_SERVER;
	public final String EJB_PORT;
	public final String AUDIO_FOLDER;

	
	public InteractiveVoiceResponseIncomingAgiConfig(ResourceBundle rb)  throws MissingResourceException, NumberFormatException {
		
		this.DIAL_PROTOCOL = rb.getString("DIAL_PROTOCOL");
		this.DIAL_PREFIX = rb.getString("DIAL_PREFIX");
		this.DIAL_TEST_PREFIX = rb.getString("DIAL_TEST_PREFIX");
		this.DIAL_GATEWAY = rb.getString("DIAL_GATEWAY");
		this.DIAL_TEST_GATEWAY = rb.getString("DIAL_TEST_GATEWAY");
		this.DIAL_TIMEOUT = rb.getString("DIAL_TIMEOUT");
		this.RATER_EJB_SERVER = rb.getString("RATER_EJB_SERVER");
		this.RATER_EJB_PORT = rb.getString("RATER_EJB_PORT");
		this.ENTERPRISE_NAME = rb.getString("ENTERPRISE_NAME");
		this.ENTERPRISE_ID = Long.parseLong(rb.getString("ENTERPRISE_ID"));
		this.UNLIMITED_CALLS_MAX_MINUTES = Integer.parseInt(rb.getString("UNLIMITED_CALLS_MAX_MINUTES"));
		this.UNLIMITED_CALLS_RANDOM_MINUTES = Integer.parseInt(rb.getString("UNLIMITED_CALLS_RANDOM_MINUTES"));
		
		this.EJB_SERVER = rb.getString("EJB_SERVER");
		this.EJB_PORT = rb.getString("EJB_PORT");
		this.AUDIO_FOLDER = rb.getString("AUDIO_FOLDER");
	}

}
