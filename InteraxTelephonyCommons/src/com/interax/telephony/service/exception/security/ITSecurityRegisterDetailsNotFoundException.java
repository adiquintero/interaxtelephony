package com.interax.telephony.service.exception.security;


public class ITSecurityRegisterDetailsNotFoundException extends ITSecurityException{
	private static final long serialVersionUID = 1L;

	public ITSecurityRegisterDetailsNotFoundException(String message) { super(message);}
    
    public ITSecurityRegisterDetailsNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
