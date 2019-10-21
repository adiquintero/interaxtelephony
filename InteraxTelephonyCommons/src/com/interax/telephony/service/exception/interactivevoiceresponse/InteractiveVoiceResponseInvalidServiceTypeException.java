package com.interax.telephony.service.exception.interactivevoiceresponse;


public class InteractiveVoiceResponseInvalidServiceTypeException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseInvalidServiceTypeException(String message) { super(message);}
    
    public InteractiveVoiceResponseInvalidServiceTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}