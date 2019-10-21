package com.interax.telephony.service.exception.interactivevoiceresponse;


public class InteractiveVoiceResponseOverdueInvoiceException extends InteractiveVoiceResponseException {
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseOverdueInvoiceException(String message) {
		super(message);
	}
	
	public InteractiveVoiceResponseOverdueInvoiceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
