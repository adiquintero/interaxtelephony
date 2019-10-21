package com.interax.telephony.service.exception.interactivevoiceresponse;


public class InteractiveVoiceResponseAccessTypeException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseAccessTypeException(String message) { super(message);}
    
    public InteractiveVoiceResponseAccessTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}