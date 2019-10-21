package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidInvalidServiceTypeException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidInvalidServiceTypeException(String message) { super(message);}
    
    public DidInvalidServiceTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
