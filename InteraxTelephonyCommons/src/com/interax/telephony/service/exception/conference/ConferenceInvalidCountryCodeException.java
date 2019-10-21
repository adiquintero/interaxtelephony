package com.interax.telephony.service.exception.conference;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class ConferenceInvalidCountryCodeException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public ConferenceInvalidCountryCodeException(String message) {
		super(message);
	}
    
    public ConferenceInvalidCountryCodeException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceInvalidCountryCodeException(Exception e) {
    	super(e);
    }
}