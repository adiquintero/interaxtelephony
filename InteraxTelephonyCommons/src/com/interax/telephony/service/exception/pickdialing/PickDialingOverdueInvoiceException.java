package com.interax.telephony.service.exception.pickdialing;


public class PickDialingOverdueInvoiceException extends PickDialingException {
	private static final long serialVersionUID = 1L;

	public PickDialingOverdueInvoiceException(String message) {
		super(message);
	}
	
	public PickDialingOverdueInvoiceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
