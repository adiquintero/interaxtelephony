package com.interax.telephony.service.exception.callingcard;


public class CallingCardApplicationNotFoundException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardApplicationNotFoundException(String message) { super(message);}
    
    public CallingCardApplicationNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}