package com.interax.telephony.service.exception.pickdialing;


public class PickDialingInvalidTransactionIdException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingInvalidTransactionIdException(String message) { super(message);}
    
    public PickDialingInvalidTransactionIdException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}