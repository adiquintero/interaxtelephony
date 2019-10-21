package com.interax.telephony.service.exception.conference;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class ConferenceCreditLimitExceededException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public ConferenceCreditLimitExceededException(String message) {
		super(message);
	}
    
    public ConferenceCreditLimitExceededException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceCreditLimitExceededException(Exception e) {
    	super(e);
    }
}