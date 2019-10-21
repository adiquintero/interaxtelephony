package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidRateNotFoundException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidRateNotFoundException(String message) { super(message);}
    
    public DidRateNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
