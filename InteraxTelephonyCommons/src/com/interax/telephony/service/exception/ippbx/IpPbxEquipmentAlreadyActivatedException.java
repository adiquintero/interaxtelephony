package com.interax.telephony.service.exception.ippbx;


public class IpPbxEquipmentAlreadyActivatedException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxEquipmentAlreadyActivatedException(String message) { super(message);}
    
    public IpPbxEquipmentAlreadyActivatedException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}