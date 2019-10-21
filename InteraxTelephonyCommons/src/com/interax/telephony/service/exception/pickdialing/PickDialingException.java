package com.interax.telephony.service.exception.pickdialing;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class PickDialingException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public PickDialingException(String message) {
		super(message);
	}
    
    public PickDialingException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public PickDialingException(Exception e) {
    	super(e);
    }
}