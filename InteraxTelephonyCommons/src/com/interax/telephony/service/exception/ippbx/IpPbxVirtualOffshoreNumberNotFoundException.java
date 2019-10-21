package com.interax.telephony.service.exception.ippbx;


public class IpPbxVirtualOffshoreNumberNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxVirtualOffshoreNumberNotFoundException(String message) { super(message);}
    
    public IpPbxVirtualOffshoreNumberNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}