package com.interax.telephony.service.exception.ippbx;


public class IpPbxGeneralException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxGeneralException(String message) {
		super(message);
	}
    
    public IpPbxGeneralException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public IpPbxGeneralException(Exception e) {
    	super(e);
    }
}