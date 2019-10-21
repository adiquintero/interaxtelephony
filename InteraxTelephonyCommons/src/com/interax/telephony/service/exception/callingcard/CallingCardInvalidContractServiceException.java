package com.interax.telephony.service.exception.callingcard;


public class CallingCardInvalidContractServiceException extends CallingCardException{
	private static final long serialVersionUID = 1L;

	public CallingCardInvalidContractServiceException(String message) { super(message);}
    
    public CallingCardInvalidContractServiceException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}