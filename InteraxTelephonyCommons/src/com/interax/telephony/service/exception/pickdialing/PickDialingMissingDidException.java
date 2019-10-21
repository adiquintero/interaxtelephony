package com.interax.telephony.service.exception.pickdialing;


public class PickDialingMissingDidException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingMissingDidException(String message) { super(message);}
    
    public PickDialingMissingDidException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}