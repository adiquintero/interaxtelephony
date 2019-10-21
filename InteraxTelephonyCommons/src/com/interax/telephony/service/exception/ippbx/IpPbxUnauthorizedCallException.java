package com.interax.telephony.service.exception.ippbx;


public class IpPbxUnauthorizedCallException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxUnauthorizedCallException(String message) { super(message);}
    
    public IpPbxUnauthorizedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}