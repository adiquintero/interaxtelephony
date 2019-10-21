package com.interax.telephony.service.exception.pickdialing;


public class PickDialingInvalidCountryCodeException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingInvalidCountryCodeException(String message) { super(message);}
    
    public PickDialingInvalidCountryCodeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}