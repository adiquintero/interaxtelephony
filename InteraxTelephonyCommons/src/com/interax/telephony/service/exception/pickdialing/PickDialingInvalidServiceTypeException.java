package com.interax.telephony.service.exception.pickdialing;


public class PickDialingInvalidServiceTypeException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingInvalidServiceTypeException(String message) { super(message);}
    
    public PickDialingInvalidServiceTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}