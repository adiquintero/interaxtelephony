package com.interax.telephony.service.exception.ippbx;


public class IpPbxMaxEquipmentActivationAttemptsReachedException extends IpPbxException{
	private static final long serialVersionUID = 1L;

	public IpPbxMaxEquipmentActivationAttemptsReachedException(String message) { super(message);}
    
    public IpPbxMaxEquipmentActivationAttemptsReachedException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}