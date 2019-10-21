package com.interax.telephony.service.exception.ippbx;


public class IpPbxInvalidServiceTypeException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxInvalidServiceTypeException(String message) { super(message);}
    
    public IpPbxInvalidServiceTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}