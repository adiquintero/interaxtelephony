package com.interax.telephony.service.exception.it;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public class InteraxTelephonytPeerNotFoundException extends InteraxTelephonyException {

	private static final long serialVersionUID = 1L;
	public  InteraxTelephonytPeerNotFoundException(String message) {
		super(message);
	}
	
	public  InteraxTelephonytPeerNotFoundException(Exception e) {
		super(e);
	}
	
	public  InteraxTelephonytPeerNotFoundException(String message, StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}


}
