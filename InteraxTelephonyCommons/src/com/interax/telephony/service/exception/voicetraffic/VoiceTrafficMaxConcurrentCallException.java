package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficMaxConcurrentCallException extends VoiceTrafficException {
	private static final long serialVersionUID = 1L;

	public VoiceTrafficMaxConcurrentCallException(String message) {
		super(message);
	}
	
	public VoiceTrafficMaxConcurrentCallException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
