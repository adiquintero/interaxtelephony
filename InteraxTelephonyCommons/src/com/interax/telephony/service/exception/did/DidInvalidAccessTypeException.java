package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidInvalidAccessTypeException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidInvalidAccessTypeException(String message) { super(message);}
    
    public DidInvalidAccessTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
