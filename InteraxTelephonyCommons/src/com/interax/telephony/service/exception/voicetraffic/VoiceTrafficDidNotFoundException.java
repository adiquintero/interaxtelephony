package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficDidNotFoundException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficDidNotFoundException(String message) { super(message);}
    
    public VoiceTrafficDidNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}