package com.interax.telephony.service.exception.ippbx;


public class IpPbxInvalidEquipmentActivationCodeException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxInvalidEquipmentActivationCodeException(String message) { super(message);}
    
    public IpPbxInvalidEquipmentActivationCodeException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}