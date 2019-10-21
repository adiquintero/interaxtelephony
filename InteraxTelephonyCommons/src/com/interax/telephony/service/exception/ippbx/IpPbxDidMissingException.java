package com.interax.telephony.service.exception.ippbx;


public class IpPbxDidMissingException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxDidMissingException(String message) { super(message);}
    
    public IpPbxDidMissingException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}