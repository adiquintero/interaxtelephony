package com.interax.telephony.service.exception.security;

public class ITSecurityRegisterIntentNotFoundException extends ITSecurityException{
	private static final long serialVersionUID = 1L;

	public ITSecurityRegisterIntentNotFoundException(String message) { super(message);}
    
    public ITSecurityRegisterIntentNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
