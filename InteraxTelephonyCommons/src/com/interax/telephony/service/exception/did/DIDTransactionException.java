package com.interax.telephony.service.exception.did;


public class DIDTransactionException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DIDTransactionException(String message) {
		super(message);
	}
    
    public DIDTransactionException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public DIDTransactionException(Exception e) {
    	super(e);
    }
}