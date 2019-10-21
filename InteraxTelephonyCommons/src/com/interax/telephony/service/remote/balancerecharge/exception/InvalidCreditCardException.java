package com.interax.telephony.service.remote.balancerecharge.exception;

import com.interax.telephony.service.exception.InteraxTelephonyException;

/**
 *
 * @author abustamante
 */
public class InvalidCreditCardException extends InteraxTelephonyException {
	private static final long serialVersionUID = 1L;

	public InvalidCreditCardException(String message) {
        super(message);
    }

    public InvalidCreditCardException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
