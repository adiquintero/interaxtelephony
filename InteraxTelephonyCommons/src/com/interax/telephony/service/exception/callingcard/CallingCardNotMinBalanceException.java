package com.interax.telephony.service.exception.callingcard;


public class CallingCardNotMinBalanceException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardNotMinBalanceException(String message) { super(message);}
    
    public CallingCardNotMinBalanceException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}