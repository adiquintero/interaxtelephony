package com.interax.telephony.service.exception.ippbx;


public class IpPbxInvalidAccessTypeException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxInvalidAccessTypeException(String message) { super(message);}
    
    public IpPbxInvalidAccessTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}