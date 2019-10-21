package com.interax.telephony.service.exception.it;

import com.interax.telephony.service.exception.InteraxTelephonyException;


public class InteraxTelephonyGeneralException extends InteraxTelephonyException{



	private static final long serialVersionUID = 1L;
	public  InteraxTelephonyGeneralException(String message) {
		super(message);
	}
	
	public  InteraxTelephonyGeneralException(Exception e) {
		super(e);
	}
	
	public  InteraxTelephonyGeneralException(String message, StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}

}
