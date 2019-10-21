package com.interax.telephony.service.exception;


public class NullParameterException extends InteraxTelephonyException {
	private static final long serialVersionUID = 1L;

	public NullParameterException(String message) {
		super(message);
	}
	
	public NullParameterException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
