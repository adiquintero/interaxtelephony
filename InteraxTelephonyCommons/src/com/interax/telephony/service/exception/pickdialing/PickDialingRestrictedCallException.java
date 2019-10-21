package com.interax.telephony.service.exception.pickdialing;


public class PickDialingRestrictedCallException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingRestrictedCallException(String message) { super(message);}
    
    public PickDialingRestrictedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}