package com.interax.telephony.service.exception.ippbx;


public class IpPbxExtensionNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxExtensionNotFoundException(String message) { super(message);}
    
    public IpPbxExtensionNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}