package com.interax.telephony.service.exception.conference;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class ConferenceException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public ConferenceException(String message) {
		super(message);
	}
    
    public ConferenceException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceException(Exception e) {
    	super(e);
    }
}