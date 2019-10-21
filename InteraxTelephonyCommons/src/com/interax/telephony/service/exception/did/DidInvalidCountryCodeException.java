package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidInvalidCountryCodeException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidInvalidCountryCodeException(String message) { super(message);}
    
    public DidInvalidCountryCodeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
