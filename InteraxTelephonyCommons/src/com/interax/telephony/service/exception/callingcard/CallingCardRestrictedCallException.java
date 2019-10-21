package com.interax.telephony.service.exception.callingcard;


public class CallingCardRestrictedCallException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardRestrictedCallException(String message) { super(message);}
    
    public CallingCardRestrictedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}