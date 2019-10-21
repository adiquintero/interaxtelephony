package com.interax.telephony.service.exception.ippbx;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public abstract class IpPbxException extends InteraxTelephonyException{
	private static final long serialVersionUID = 1L;

	public IpPbxException(String message) {
		super(message);
	}
    
    public IpPbxException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public IpPbxException(Exception e) {
    	super(e);
    }
}