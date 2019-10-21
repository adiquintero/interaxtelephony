package com.interax.telephony.service.exception.ippbx;


public class IpPbxRingGroupNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxRingGroupNotFoundException(String message) { super(message);}
    
    public IpPbxRingGroupNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}