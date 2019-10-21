package com.interax.telephony.service.exception.ippbx;


public class IpPbxUnknownEquipmentException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxUnknownEquipmentException(String message) { super(message);}
    
    public IpPbxUnknownEquipmentException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}