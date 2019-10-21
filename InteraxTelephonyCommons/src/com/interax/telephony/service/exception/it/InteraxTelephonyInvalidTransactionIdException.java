package com.interax.telephony.service.exception.it;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public class InteraxTelephonyInvalidTransactionIdException extends InteraxTelephonyException {

	
	private static final long serialVersionUID = 1L;
	public  InteraxTelephonyInvalidTransactionIdException(String message) {
		super(message);
	}
	
	public  InteraxTelephonyInvalidTransactionIdException(Exception e) {
		super(e);
	}
	
	public  InteraxTelephonyInvalidTransactionIdException(String message, StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}


}
