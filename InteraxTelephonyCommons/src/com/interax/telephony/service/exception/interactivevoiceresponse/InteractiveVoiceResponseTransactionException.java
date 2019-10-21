package com.interax.telephony.service.exception.interactivevoiceresponse;


public class InteractiveVoiceResponseTransactionException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseTransactionException(String message) { super(message);}
    
    public InteractiveVoiceResponseTransactionException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}