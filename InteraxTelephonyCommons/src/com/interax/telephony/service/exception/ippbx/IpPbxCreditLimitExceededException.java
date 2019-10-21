package com.interax.telephony.service.exception.ippbx;


public class IpPbxCreditLimitExceededException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxCreditLimitExceededException(String message) { super(message);}
    
    public IpPbxCreditLimitExceededException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}