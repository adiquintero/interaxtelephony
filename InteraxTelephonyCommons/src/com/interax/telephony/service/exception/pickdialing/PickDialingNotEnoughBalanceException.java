package com.interax.telephony.service.exception.pickdialing;


public class PickDialingNotEnoughBalanceException extends PickDialingException {
	private static final long serialVersionUID = 1L;

	public PickDialingNotEnoughBalanceException(String message) {
		super(message);
	}
	
	public PickDialingNotEnoughBalanceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
