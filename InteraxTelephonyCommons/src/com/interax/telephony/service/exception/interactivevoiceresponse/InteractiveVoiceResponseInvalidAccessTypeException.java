package com.interax.telephony.service.exception.interactivevoiceresponse;


public class InteractiveVoiceResponseInvalidAccessTypeException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseInvalidAccessTypeException(String message) { super(message);}
    
    public InteractiveVoiceResponseInvalidAccessTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}