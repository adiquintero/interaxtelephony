package com.interax.telephony.service.exception.pickdialing;


public class PickDialingCreditLimitExceededException extends PickDialingException{
	private static final long serialVersionUID = 1L;

	public PickDialingCreditLimitExceededException(String message) { super(message);}
    
    public PickDialingCreditLimitExceededException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}