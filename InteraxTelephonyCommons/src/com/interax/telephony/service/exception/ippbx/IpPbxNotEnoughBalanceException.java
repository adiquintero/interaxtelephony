package com.interax.telephony.service.exception.ippbx;


public class IpPbxNotEnoughBalanceException extends IpPbxException {
	private static final long serialVersionUID = 1L;

	public IpPbxNotEnoughBalanceException(String message) {
		super(message);
	}
	
	public IpPbxNotEnoughBalanceException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
