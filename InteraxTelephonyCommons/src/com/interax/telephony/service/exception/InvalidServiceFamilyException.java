package com.interax.telephony.service.exception;


public class InvalidServiceFamilyException extends InteraxTelephonyException {
	private static final long serialVersionUID = 1L;

	public InvalidServiceFamilyException(String message) {
		super(message);
	}
	
	public InvalidServiceFamilyException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
