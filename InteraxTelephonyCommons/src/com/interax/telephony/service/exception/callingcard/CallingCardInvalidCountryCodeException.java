package com.interax.telephony.service.exception.callingcard;


public class CallingCardInvalidCountryCodeException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardInvalidCountryCodeException(String message) { super(message);}
    
    public CallingCardInvalidCountryCodeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}