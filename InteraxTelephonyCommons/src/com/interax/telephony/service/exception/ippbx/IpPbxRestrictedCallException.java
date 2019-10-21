package com.interax.telephony.service.exception.ippbx;


public class IpPbxRestrictedCallException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxRestrictedCallException(String message) { super(message);}
    
    public IpPbxRestrictedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}