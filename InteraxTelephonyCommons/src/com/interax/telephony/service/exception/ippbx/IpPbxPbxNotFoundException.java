package com.interax.telephony.service.exception.ippbx;


public class IpPbxPbxNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxPbxNotFoundException(String message) { super(message);}
    
    public IpPbxPbxNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}