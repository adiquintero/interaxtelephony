package com.interax.telephony.service.exception.conference;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class ConferenceNotEnoughBalanceException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public ConferenceNotEnoughBalanceException(String message) {
		super(message);
	}
    
    public ConferenceNotEnoughBalanceException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceNotEnoughBalanceException(Exception e) {
    	super(e);
    }
}