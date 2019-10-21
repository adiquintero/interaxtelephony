package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficNotEnoughBalanceException extends VoiceTrafficException {
	private static final long serialVersionUID = 1L;

	public VoiceTrafficNotEnoughBalanceException(String message) {
		super(message);
	}
	
	public VoiceTrafficNotEnoughBalanceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
