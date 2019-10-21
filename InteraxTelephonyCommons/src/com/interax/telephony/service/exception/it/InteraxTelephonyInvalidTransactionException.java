package com.interax.telephony.service.exception.it;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public class InteraxTelephonyInvalidTransactionException extends InteraxTelephonyException{

	private static final long serialVersionUID = 1L;
	public  InteraxTelephonyInvalidTransactionException(String message) {
		super(message);
	}
	
	public  InteraxTelephonyInvalidTransactionException(Exception e) {
		super(e);
	}
	
	public  InteraxTelephonyInvalidTransactionException(String message, StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}

}
