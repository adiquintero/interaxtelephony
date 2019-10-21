package com.interax.telephony.service.exception.callingcard;

public class CallingCardRateNotFoundException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardRateNotFoundException(String message) { super(message);}
    
    public CallingCardRateNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}