package com.interax.telephony.service.exception.voicetraffic;

public class VoiceTrafficRateNotFoundException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficRateNotFoundException(String message) { super(message);}
    
    public VoiceTrafficRateNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}