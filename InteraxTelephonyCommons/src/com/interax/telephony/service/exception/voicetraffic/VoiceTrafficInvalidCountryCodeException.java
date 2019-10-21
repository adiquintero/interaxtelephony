package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficInvalidCountryCodeException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficInvalidCountryCodeException(String message) { super(message);}
    
    public VoiceTrafficInvalidCountryCodeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}