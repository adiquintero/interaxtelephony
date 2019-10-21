package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class DIDException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public DIDException(String message) {
		super(message);
	}
    
    public DIDException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public DIDException(Exception e) {
    	super(e);
    }
}