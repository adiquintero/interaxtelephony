package com.interax.telephony.service.callingcard.disableddn.agi;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import com.interax.telephony.server.agi.InteraxTelephonyAgiServer;
import com.interax.telephony.service.callingcard.data.CallingCardAccessType;
import com.interax.telephony.service.callingcard.data.CallingCardServiceType;
import com.interax.telephony.util.InteraxTelephonyConfig;


public class DisabledDnAgiConfig extends InteraxTelephonyConfig {

    public final Long ENTERPRISE_ID;
    public final String ENTERPRISE_NAME;
    public final Vector<CallingCardServiceType> SERVICE_TYPES;
    public final CallingCardAccessType ACCESS_TYPE;

    public final String AUDIO_FOLDER;

    public final int AUTHENTICATION_MAX_ATTEMPTS;
    public final int AUTHENTICATION_DIGIT_TIMEOUT;
    public final int MAX_PIN_SECRET_LENGTH;

    public final String PIN_EJB_SERVER;
    public final String PIN_EJB_PORT;

    public DisabledDnAgiConfig(ResourceBundle rb)  throws MissingResourceException, NumberFormatException {

        this.ENTERPRISE_ID = Long.parseLong(rb.getString("ENTERPRISE_ID"));
        this.ENTERPRISE_NAME = rb.getString("ENTERPRISE_NAME");
        this.SERVICE_TYPES = new Vector<CallingCardServiceType>();
        String[] serviceTypes = rb.getString("SERVICE_TYPES").split(",");
        for(int i=0; i<serviceTypes.length; i++){
            CallingCardServiceType serviceType = CallingCardServiceType.valueOf(serviceTypes[i]);
            this.SERVICE_TYPES.add(serviceType);
        }
        this.ACCESS_TYPE = CallingCardAccessType.valueOf(rb.getString(("ACCESS_TYPE")));
        this.AUDIO_FOLDER = rb.getString("AUDIO_FOLDER");

        switch(this.ACCESS_TYPE) {
            case IVR:
                this.AUTHENTICATION_MAX_ATTEMPTS = Integer.parseInt(rb.getString("AUTHENTICATION_MAX_ATTEMPTS"));
                this.AUTHENTICATION_DIGIT_TIMEOUT = Integer.parseInt(rb.getString("AUTHENTICATION_DIGIT_TIMEOUT"));
                this.MAX_PIN_SECRET_LENGTH = Integer.parseInt(rb.getString("MAX_PIN_SECRET_LENGTH"));
            break;

            default:
                this.AUTHENTICATION_MAX_ATTEMPTS = 0;
                this.AUTHENTICATION_DIGIT_TIMEOUT = 0;
                this.MAX_PIN_SECRET_LENGTH = 0;
            break;
        }

        this.PIN_EJB_SERVER = rb.getString("PIN_EJB_SERVER");
        this.PIN_EJB_PORT = rb.getString("PIN_EJB_PORT");
    }
	
}
