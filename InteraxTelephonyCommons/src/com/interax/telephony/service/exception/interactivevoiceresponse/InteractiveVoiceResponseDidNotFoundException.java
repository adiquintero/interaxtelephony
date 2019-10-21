package com.interax.telephony.service.exception.interactivevoiceresponse;

public class InteractiveVoiceResponseDidNotFoundException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseDidNotFoundException(String message) { super(message);}
    
    public InteractiveVoiceResponseDidNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}