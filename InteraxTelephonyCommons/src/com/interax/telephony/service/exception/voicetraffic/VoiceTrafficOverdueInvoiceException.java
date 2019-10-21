package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficOverdueInvoiceException extends VoiceTrafficException {
	private static final long serialVersionUID = 1L;

	public VoiceTrafficOverdueInvoiceException(String message) {
		super(message);
	}
	
	public VoiceTrafficOverdueInvoiceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
