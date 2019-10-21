package com.interax.telephony.service.exception.pickdialing;


public class PickDialingInvalidTransactionException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingInvalidTransactionException(String message) { super(message);}
    
    public PickDialingInvalidTransactionException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}