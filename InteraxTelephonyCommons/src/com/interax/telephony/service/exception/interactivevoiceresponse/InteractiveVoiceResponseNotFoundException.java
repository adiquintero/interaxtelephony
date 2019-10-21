package com.interax.telephony.service.exception.interactivevoiceresponse;

public class InteractiveVoiceResponseNotFoundException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseNotFoundException(String message) { super(message);}
    
    public InteractiveVoiceResponseNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}