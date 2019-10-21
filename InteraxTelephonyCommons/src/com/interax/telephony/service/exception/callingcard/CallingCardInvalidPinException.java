package com.interax.telephony.service.exception.callingcard;

public class CallingCardInvalidPinException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardInvalidPinException(String message) { super(message);}
    
    public CallingCardInvalidPinException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}