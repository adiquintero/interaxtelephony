package com.interax.telephony.service.exception.ippbx;


public class IpPbxTransactionException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxTransactionException(String message) { super(message);}
    
    public IpPbxTransactionException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}