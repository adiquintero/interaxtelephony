package com.interax.telephony.service.exception.callingcard;


public class CallingCardCreditLimitExceededException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardCreditLimitExceededException(String message) { super(message);}
    
    public CallingCardCreditLimitExceededException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}