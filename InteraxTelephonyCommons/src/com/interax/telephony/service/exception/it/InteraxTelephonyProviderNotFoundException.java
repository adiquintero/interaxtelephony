package com.interax.telephony.service.exception.it;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public class InteraxTelephonyProviderNotFoundException extends InteraxTelephonyException {

	private static final long serialVersionUID = 1L;
	public  InteraxTelephonyProviderNotFoundException(String message) {
		super(message);
	}
	
	public  InteraxTelephonyProviderNotFoundException(Exception e) {
		super(e);
	}
	
	public  InteraxTelephonyProviderNotFoundException(String message, StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}


}
