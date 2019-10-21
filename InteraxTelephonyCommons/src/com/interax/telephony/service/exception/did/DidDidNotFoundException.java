package com.interax.telephony.service.exception.did;


public class DidDidNotFoundException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidDidNotFoundException(String message) {
		super(message);
	}
    
    public DidDidNotFoundException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public DidDidNotFoundException(Exception e) {
    	super(e);
    }
}