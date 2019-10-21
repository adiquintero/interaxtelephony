package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficRestrictedCallException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficRestrictedCallException(String message) { super(message);}
    
    public VoiceTrafficRestrictedCallException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}