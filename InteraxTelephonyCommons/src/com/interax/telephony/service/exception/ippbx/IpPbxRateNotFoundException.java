package com.interax.telephony.service.exception.ippbx;

public class IpPbxRateNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxRateNotFoundException(String message) { super(message);}
    
    public IpPbxRateNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}