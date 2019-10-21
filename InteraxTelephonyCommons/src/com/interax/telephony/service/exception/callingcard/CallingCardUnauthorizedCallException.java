package com.interax.telephony.service.exception.callingcard;

public class CallingCardUnauthorizedCallException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardUnauthorizedCallException(String message) { super(message);}
    
    public CallingCardUnauthorizedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}