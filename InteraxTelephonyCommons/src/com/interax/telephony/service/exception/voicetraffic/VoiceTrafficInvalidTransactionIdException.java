package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficInvalidTransactionIdException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficInvalidTransactionIdException(String message) { super(message);}
    
    public VoiceTrafficInvalidTransactionIdException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}