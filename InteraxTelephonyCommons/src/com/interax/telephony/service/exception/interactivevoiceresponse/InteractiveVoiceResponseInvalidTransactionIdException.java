package com.interax.telephony.service.exception.interactivevoiceresponse;

import com.interax.telephony.service.exception.interactivevoiceresponse.InteractiveVoiceResponseException;


public class InteractiveVoiceResponseInvalidTransactionIdException extends InteractiveVoiceResponseException{
	private static final long serialVersionUID = 1L;

	public InteractiveVoiceResponseInvalidTransactionIdException(String message) { super(message);}
    
    public InteractiveVoiceResponseInvalidTransactionIdException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}