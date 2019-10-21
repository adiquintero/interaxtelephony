package com.interax.telephony.service.exception.callingcard;


public class CallingCardInvalidServiceTypeException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardInvalidServiceTypeException(String message) { super(message);}
    
    public CallingCardInvalidServiceTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}