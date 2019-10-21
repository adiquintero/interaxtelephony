package com.interax.telephony.service.exception.did;


public class DIDGeneralException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DIDGeneralException(String message) {
		super(message);
	}
    
    public DIDGeneralException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public DIDGeneralException(Exception e) {
    	super(e);
    }
}