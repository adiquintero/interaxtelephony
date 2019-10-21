package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidRestrictedCallException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidRestrictedCallException(String message) { super(message);}
    
    public DidRestrictedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}