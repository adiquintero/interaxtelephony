package com.interax.telephony.service.exception.did;


public class DidServiceNotFoundException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidServiceNotFoundException(String message) {
		super(message);
	}
    
    public DidServiceNotFoundException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public DidServiceNotFoundException(Exception e) {
    	super(e);
    }
}