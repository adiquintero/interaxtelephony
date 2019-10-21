package com.interax.telephony.service.exception.ippbx;

public class IpPbxExtensionHasForwardNotFoundException  extends IpPbxException
{
	
	private static final long serialVersionUID = 1L;

	public IpPbxExtensionHasForwardNotFoundException(String message) { super(message);}
    
    public IpPbxExtensionHasForwardNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
