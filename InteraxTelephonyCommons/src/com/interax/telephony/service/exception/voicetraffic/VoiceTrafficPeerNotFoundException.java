package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficPeerNotFoundException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficPeerNotFoundException(String message) { super(message);}
    
    public VoiceTrafficPeerNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}