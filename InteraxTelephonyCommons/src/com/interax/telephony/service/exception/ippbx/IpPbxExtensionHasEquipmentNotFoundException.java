package com.interax.telephony.service.exception.ippbx;


public class IpPbxExtensionHasEquipmentNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxExtensionHasEquipmentNotFoundException(String message) { super(message);}
    
    public IpPbxExtensionHasEquipmentNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}