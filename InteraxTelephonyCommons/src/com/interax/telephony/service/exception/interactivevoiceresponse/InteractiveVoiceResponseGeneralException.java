package com.interax.telephony.service.exception.interactivevoiceresponse;

import com.interax.telephony.service.exception.interactivevoiceresponse.InteractiveVoiceResponseException;


public class InteractiveVoiceResponseGeneralException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseGeneralException(String message) {
		super(message);
	}
    
    public InteractiveVoiceResponseGeneralException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public InteractiveVoiceResponseGeneralException(Exception e) {
    	super(e);
    }
}