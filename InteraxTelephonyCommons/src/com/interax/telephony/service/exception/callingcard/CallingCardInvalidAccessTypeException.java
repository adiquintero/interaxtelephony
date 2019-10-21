package com.interax.telephony.service.exception.callingcard;


public class CallingCardInvalidAccessTypeException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardInvalidAccessTypeException(String message) { super(message);}
    
    public CallingCardInvalidAccessTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}