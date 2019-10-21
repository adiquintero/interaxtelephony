package com.interax.telephony.service.exception.conference;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class ConferenceRateNotFoundException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public ConferenceRateNotFoundException(String message) {
		super(message);
	}
    
    public ConferenceRateNotFoundException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceRateNotFoundException(Exception e) {
    	super(e);
    }
}