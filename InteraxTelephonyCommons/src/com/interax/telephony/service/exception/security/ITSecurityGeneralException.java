package com.interax.telephony.service.exception.security;


public class ITSecurityGeneralException extends ITSecurityException{
	private static final long serialVersionUID = 1L;

	public ITSecurityGeneralException(String message) {
		super(message);
	}
    
    public ITSecurityGeneralException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ITSecurityGeneralException(Exception e) {
    	super(e);
    }
}
