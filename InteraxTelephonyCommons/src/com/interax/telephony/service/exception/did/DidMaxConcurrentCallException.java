package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidMaxConcurrentCallException extends DIDException {
	private static final long serialVersionUID = 1L;

	public DidMaxConcurrentCallException(String message) {
		super(message);
	}
	
	public DidMaxConcurrentCallException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}

}
