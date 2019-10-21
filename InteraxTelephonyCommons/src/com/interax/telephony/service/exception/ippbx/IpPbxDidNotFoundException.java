package com.interax.telephony.service.exception.ippbx;


public class IpPbxDidNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxDidNotFoundException(String message) { super(message);}
    
    public IpPbxDidNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}