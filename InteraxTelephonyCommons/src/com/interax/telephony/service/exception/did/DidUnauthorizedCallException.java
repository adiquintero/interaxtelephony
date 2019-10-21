package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidUnauthorizedCallException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidUnauthorizedCallException(String message) { super(message);}
    
    public DidUnauthorizedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
