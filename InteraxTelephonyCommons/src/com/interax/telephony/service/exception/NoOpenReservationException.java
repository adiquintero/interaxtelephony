package com.interax.telephony.service.exception;


public class NoOpenReservationException extends InteraxTelephonyException {
	private static final long serialVersionUID = 1L;

	public NoOpenReservationException(String message) {
		super(message);
	}
	
	public NoOpenReservationException(String message, 
		StackTraceElement[] stackTrace) {
		super(message);
		setStackTrace(stackTrace);
	}
}
