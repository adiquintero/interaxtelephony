package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficVirtualOffshoreNumberNotFoundException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficVirtualOffshoreNumberNotFoundException(String message) { super(message);}
    
    public VoiceTrafficVirtualOffshoreNumberNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}