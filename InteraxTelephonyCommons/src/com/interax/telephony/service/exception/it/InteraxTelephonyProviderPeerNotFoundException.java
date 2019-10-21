package com.interax.telephony.service.exception.it;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public class InteraxTelephonyProviderPeerNotFoundException extends InteraxTelephonyException {

	private static final long serialVersionUID = 1L;
	public  InteraxTelephonyProviderPeerNotFoundException(String message) {
		super(message);
	}
	
	public  InteraxTelephonyProviderPeerNotFoundException(Exception e) {
		super(e);
	}
	
	public  InteraxTelephonyProviderPeerNotFoundException(String message, StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}

}
