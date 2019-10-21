package com.interax.telephony.service.exception.pickdialing;


public class PickDialingMaxConcurrentCallException extends PickDialingException {
	private static final long serialVersionUID = 1L;

	public PickDialingMaxConcurrentCallException(String message) {
		super(message);
	}
	
	public PickDialingMaxConcurrentCallException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
