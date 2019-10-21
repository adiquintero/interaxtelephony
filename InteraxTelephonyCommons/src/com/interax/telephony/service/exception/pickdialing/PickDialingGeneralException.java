package com.interax.telephony.service.exception.pickdialing;


public class PickDialingGeneralException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingGeneralException(String message) {
		super(message);
	}
    
    public PickDialingGeneralException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public PickDialingGeneralException(Exception e) {
    	super(e);
    }
}