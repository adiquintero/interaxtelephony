package com.interax.telephony.service.exception.security;

public class ITSecurityIpBlackListNotFoundException extends ITSecurityException{
	private static final long serialVersionUID = 1L;

	public ITSecurityIpBlackListNotFoundException(String message) { super(message);}
    
    public ITSecurityIpBlackListNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }


}
