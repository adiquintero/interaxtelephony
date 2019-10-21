package com.interax.telephony.service.exception.callingcard;


public class CallingCardInvalidTransactionException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardInvalidTransactionException(String message) { super(message);}
    
    public CallingCardInvalidTransactionException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}