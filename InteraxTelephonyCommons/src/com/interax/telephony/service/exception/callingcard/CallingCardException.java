package com.interax.telephony.service.exception.callingcard;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class CallingCardException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public CallingCardException(String message) {
		super(message);
	}
    
    public CallingCardException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public CallingCardException(Exception e) {
    	super(e);
    }
}