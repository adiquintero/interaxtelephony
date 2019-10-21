package com.interax.telephony.service.exception.ippbx;


public class IpPbxUnknownExtensionException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxUnknownExtensionException(String message) { super(message);}
    
    public IpPbxUnknownExtensionException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}