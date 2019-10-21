package com.interax.telephony.service.exception.interactivevoiceresponse;

public class InteractiveVoiceResponseRateNotFoundException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseRateNotFoundException(String message) { super(message);}
    
    public InteractiveVoiceResponseRateNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}