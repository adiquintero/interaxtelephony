package com.interax.telephony.service.exception;


public class ReservationNotFoundException extends InteraxTelephonyException {
	private static final long serialVersionUID = 1L;

	public ReservationNotFoundException(String message) {
		super(message);
	}
	
	public ReservationNotFoundException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
