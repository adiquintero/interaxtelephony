package com.interax.telephony.service.exception.callingcard;


public class CallingCardGeneralException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardGeneralException(String message) {
		super(message);
	}
    
    public CallingCardGeneralException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public CallingCardGeneralException(Exception e) {
    	super(e);
    }
}