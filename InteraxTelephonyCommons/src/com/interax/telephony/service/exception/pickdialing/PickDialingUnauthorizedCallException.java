package com.interax.telephony.service.exception.pickdialing;


public class PickDialingUnauthorizedCallException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingUnauthorizedCallException(String message) { super(message);}
    
    public PickDialingUnauthorizedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}