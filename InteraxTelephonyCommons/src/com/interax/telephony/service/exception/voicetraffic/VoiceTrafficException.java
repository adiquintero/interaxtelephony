package com.interax.telephony.service.exception.voicetraffic;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class VoiceTrafficException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficException(String message) {
		super(message);
	}
    
    public VoiceTrafficException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public VoiceTrafficException(Exception e) {
    	super(e);
    }
}