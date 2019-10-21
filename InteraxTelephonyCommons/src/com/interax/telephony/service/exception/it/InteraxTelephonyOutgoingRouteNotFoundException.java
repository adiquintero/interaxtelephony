package com.interax.telephony.service.exception.it;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public class InteraxTelephonyOutgoingRouteNotFoundException extends InteraxTelephonyException {
	
	private static final long serialVersionUID = 1L;
	public  InteraxTelephonyOutgoingRouteNotFoundException(String message) {
		super(message);
	}
	
	public  InteraxTelephonyOutgoingRouteNotFoundException(Exception e) {
		super(e);
	}
	
	public  InteraxTelephonyOutgoingRouteNotFoundException(String message, StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}

}
