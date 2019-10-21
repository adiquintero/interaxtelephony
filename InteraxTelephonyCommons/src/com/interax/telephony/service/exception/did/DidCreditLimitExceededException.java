package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidCreditLimitExceededException extends DIDException{
	private static final long serialVersionUID = 1L;

	public DidCreditLimitExceededException(String message) { super(message);}
    
    public DidCreditLimitExceededException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
