package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficInvalidAccessTypeException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficInvalidAccessTypeException(String message) { super(message);}
    
    public VoiceTrafficInvalidAccessTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}