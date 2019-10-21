package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidNotEnoughBalanceException extends DIDException {
	private static final long serialVersionUID = 1L;

	public DidNotEnoughBalanceException(String message) {
		super(message);
	}
	
	public DidNotEnoughBalanceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}

}
