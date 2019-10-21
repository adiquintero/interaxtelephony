package com.interax.telephony.service.exception.ippbx;


public class IpPbxInvalidTransactionIdException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxInvalidTransactionIdException(String message) { super(message);}
    
    public IpPbxInvalidTransactionIdException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}