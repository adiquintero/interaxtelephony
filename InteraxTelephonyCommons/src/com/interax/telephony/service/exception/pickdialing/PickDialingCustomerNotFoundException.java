package com.interax.telephony.service.exception.pickdialing;


public class PickDialingCustomerNotFoundException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingCustomerNotFoundException(String message) { super(message);}
    
    public PickDialingCustomerNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}