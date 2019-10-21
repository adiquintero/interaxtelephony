package com.interax.telephony.service.exception.callingcard;


public class CallingCardDidNotFoundException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardDidNotFoundException(String message) { super(message);}
    
    public CallingCardDidNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}