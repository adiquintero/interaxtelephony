package com.interax.telephony.service.exception.pickdialing;


public class PickDialingAniNotFoundException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingAniNotFoundException(String message) { super(message);}
    
    public PickDialingAniNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}