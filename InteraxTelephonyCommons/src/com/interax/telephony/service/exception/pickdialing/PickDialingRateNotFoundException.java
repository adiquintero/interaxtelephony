package com.interax.telephony.service.exception.pickdialing;

public class PickDialingRateNotFoundException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingRateNotFoundException(String message) { super(message);}
    
    public PickDialingRateNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}