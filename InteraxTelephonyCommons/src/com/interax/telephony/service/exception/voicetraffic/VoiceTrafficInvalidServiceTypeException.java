package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficInvalidServiceTypeException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficInvalidServiceTypeException(String message) { super(message);}
    
    public VoiceTrafficInvalidServiceTypeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}