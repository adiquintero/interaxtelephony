package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficCustomerNotFoundException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficCustomerNotFoundException(String message) { super(message);}
    
    public VoiceTrafficCustomerNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}