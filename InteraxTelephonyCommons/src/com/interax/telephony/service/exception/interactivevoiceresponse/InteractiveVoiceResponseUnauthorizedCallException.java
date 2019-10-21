package com.interax.telephony.service.exception.interactivevoiceresponse;


public class InteractiveVoiceResponseUnauthorizedCallException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseUnauthorizedCallException(String message) { super(message);}
    
    public InteractiveVoiceResponseUnauthorizedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}