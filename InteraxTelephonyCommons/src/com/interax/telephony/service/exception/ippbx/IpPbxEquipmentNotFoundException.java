package com.interax.telephony.service.exception.ippbx;


public class IpPbxEquipmentNotFoundException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxEquipmentNotFoundException(String message) { super(message);}
    
    public IpPbxEquipmentNotFoundException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}