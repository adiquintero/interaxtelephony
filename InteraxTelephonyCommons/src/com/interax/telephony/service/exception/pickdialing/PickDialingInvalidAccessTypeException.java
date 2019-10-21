package com.interax.telephony.service.exception.pickdialing;


public class PickDialingInvalidAccessTypeException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingInvalidAccessTypeException(String message) { super(message);}
    
    public PickDialingInvalidAccessTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}