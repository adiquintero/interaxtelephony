package com.interax.telephony.service.exception.it;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public class InteraxTelephonyOutgoingRouteStepNotFoundException extends InteraxTelephonyException {
	
	private static final long serialVersionUID = 1L;
	public  InteraxTelephonyOutgoingRouteStepNotFoundException(String message) {
		super(message);
	}
	
	public  InteraxTelephonyOutgoingRouteStepNotFoundException(Exception e) {
		super(e);
	}
	
	public  InteraxTelephonyOutgoingRouteStepNotFoundException(String message, StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}

}
