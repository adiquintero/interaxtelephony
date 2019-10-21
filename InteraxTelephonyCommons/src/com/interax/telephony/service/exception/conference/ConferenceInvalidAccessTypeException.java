package com.interax.telephony.service.exception.conference;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class ConferenceInvalidAccessTypeException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public ConferenceInvalidAccessTypeException(String message) {
		super(message);
	}
    
    public ConferenceInvalidAccessTypeException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceInvalidAccessTypeException(Exception e) {
    	super(e);
    }
}