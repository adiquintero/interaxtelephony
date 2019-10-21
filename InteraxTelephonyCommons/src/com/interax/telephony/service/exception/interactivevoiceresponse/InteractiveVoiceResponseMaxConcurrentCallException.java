package com.interax.telephony.service.exception.interactivevoiceresponse;


public class InteractiveVoiceResponseMaxConcurrentCallException extends InteractiveVoiceResponseException {
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseMaxConcurrentCallException(String message) {
		super(message);
	}
	
	public InteractiveVoiceResponseMaxConcurrentCallException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
