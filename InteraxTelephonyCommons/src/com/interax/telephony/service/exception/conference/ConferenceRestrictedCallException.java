package com.interax.telephony.service.exception.conference;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class ConferenceRestrictedCallException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public ConferenceRestrictedCallException(String message) {
		super(message);
	}
    
    public ConferenceRestrictedCallException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceRestrictedCallException(Exception e) {
    	super(e);
    }
}