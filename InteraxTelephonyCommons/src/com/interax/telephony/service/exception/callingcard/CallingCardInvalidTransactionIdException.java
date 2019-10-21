package com.interax.telephony.service.exception.callingcard;


public class CallingCardInvalidTransactionIdException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardInvalidTransactionIdException(String message) { super(message);}
    
    public CallingCardInvalidTransactionIdException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}