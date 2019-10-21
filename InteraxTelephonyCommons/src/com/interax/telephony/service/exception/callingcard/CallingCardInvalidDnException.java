package com.interax.telephony.service.exception.callingcard;

public class CallingCardInvalidDnException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardInvalidDnException(String message) { super(message);}
    
    public CallingCardInvalidDnException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}