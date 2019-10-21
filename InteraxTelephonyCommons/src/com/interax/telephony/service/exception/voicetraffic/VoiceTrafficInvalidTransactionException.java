package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficInvalidTransactionException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficInvalidTransactionException(String message) { super(message);}
    
    public VoiceTrafficInvalidTransactionException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}