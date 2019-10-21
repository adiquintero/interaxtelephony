package com.interax.telephony.service.ami.pickdialing;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.interax.telephony.util.InteraxTelephonyConfig;

public class PickDialingAmiConfig extends InteraxTelephonyConfig {

    public final int SLEEP_TIME;
    public final int SAFETY_SECONDS_TO_HANGUP;
	public final String RATER_EJB_SERVER;
	public final String RATER_EJB_PORT;
	public final String ENTERPRISE_NAME;
	public final Long ENTERPRISE_ID;
	
	public PickDialingAmiConfig(ResourceBundle rb) throws MissingResourceException, NumberFormatException {
		this.SLEEP_TIME = Integer.parseInt(rb.getString("SLEEP_TIME"));
		this.SAFETY_SECONDS_TO_HANGUP = Integer.parseInt(rb.getString("SAFETY_SECONDS_TO_HANGUP"));
		this.RATER_EJB_SERVER = rb.getString("RATER_EJB_SERVER");
		this.RATER_EJB_PORT = rb.getString("RATER_EJB_PORT");
		this.ENTERPRISE_NAME = rb.getString("ENTERPRISE_NAME");
		this.ENTERPRISE_ID = Long.parseLong(rb.getString("ENTERPRISE_ID"));

	}


}
