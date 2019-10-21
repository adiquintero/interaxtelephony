package com.interax.telephony.service.exception.ippbx;


public class IpPbxVoiceMailBoxNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxVoiceMailBoxNotFoundException(String message) { super(message);}
    
    public IpPbxVoiceMailBoxNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}