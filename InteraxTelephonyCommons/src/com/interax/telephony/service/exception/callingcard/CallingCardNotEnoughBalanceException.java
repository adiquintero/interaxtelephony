package com.interax.telephony.service.exception.callingcard;


public class CallingCardNotEnoughBalanceException extends CallingCardException {
	private static final long serialVersionUID = 1L;

	public CallingCardNotEnoughBalanceException(String message) {
		super(message);
	}
	
	public CallingCardNotEnoughBalanceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
