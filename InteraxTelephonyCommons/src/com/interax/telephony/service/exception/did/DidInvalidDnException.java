package com.interax.telephony.service.exception.did;

public class DidInvalidDnException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidInvalidDnException(String message) { super(message);}
    
    public DidInvalidDnException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
