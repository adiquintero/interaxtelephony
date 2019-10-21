package com.interax.telephony.service.exception.conference;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class ConferenceInvalidServiceTypeException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public ConferenceInvalidServiceTypeException(String message) {
		super(message);
	}
    
    public ConferenceInvalidServiceTypeException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceInvalidServiceTypeException(Exception e) {
    	super(e);
    }
}