package com.interax.telephony.service.exception.did;

import com.interax.telephony.service.exception.did.DIDException;

public class DidOverdueInvoiceException extends DIDException {
	private static final long serialVersionUID = 1L;

	public DidOverdueInvoiceException(String message) {
		super(message);
	}
	
	public DidOverdueInvoiceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}

}
