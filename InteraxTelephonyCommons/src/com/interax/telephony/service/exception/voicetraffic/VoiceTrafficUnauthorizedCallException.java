package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficUnauthorizedCallException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficUnauthorizedCallException(String message) { super(message);}
    
    public VoiceTrafficUnauthorizedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}