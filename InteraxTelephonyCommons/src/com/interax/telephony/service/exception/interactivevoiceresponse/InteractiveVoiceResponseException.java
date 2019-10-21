package com.interax.telephony.service.exception.interactivevoiceresponse;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class InteractiveVoiceResponseException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseException(String message) {
		super(message);
	}
    
    public InteractiveVoiceResponseException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public InteractiveVoiceResponseException(Exception e) {
    	super(e);
    }
}