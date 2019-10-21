package com.interax.telephony.service.exception.did;


public class DIDInvalidTransactionIdException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DIDInvalidTransactionIdException(String message) {
		super(message);
	}
    
    public DIDInvalidTransactionIdException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public DIDInvalidTransactionIdException(Exception e) {
    	super(e);
    }
}