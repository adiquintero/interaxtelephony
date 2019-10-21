package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficMissingDidException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficMissingDidException(String message) { super(message);}
    
    public VoiceTrafficMissingDidException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}