package com.interax.telephony.service.exception.ippbx;


public class IpPbxInvalidCountryCodeException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxInvalidCountryCodeException(String message) { super(message);}
    
    public IpPbxInvalidCountryCodeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}