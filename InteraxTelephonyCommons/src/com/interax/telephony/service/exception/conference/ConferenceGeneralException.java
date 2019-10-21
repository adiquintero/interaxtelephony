package com.interax.telephony.service.exception.conference;


public class ConferenceGeneralException extends ConferenceException{
	private static final long serialVersionUID = 1L;

	public ConferenceGeneralException(String message) {
		super(message);
	}
    
    public ConferenceGeneralException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public ConferenceGeneralException(Exception e) {
    	super(e);
    }
}