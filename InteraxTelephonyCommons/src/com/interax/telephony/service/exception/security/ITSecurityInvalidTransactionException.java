package com.interax.telephony.service.exception.security;



public class ITSecurityInvalidTransactionException extends ITSecurityException{
	private static final long serialVersionUID = 1L;

	public ITSecurityInvalidTransactionException(String message) { super(message);}
    
    public ITSecurityInvalidTransactionException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}
