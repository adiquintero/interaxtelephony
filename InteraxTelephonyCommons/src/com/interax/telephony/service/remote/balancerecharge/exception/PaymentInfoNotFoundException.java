package com.interax.telephony.service.remote.balancerecharge.exception;

import com.interax.telephony.service.exception.InteraxTelephonyException;

/**
 *
 * @author abustamante
 */
public class PaymentInfoNotFoundException extends InteraxTelephonyException {
	private static final long serialVersionUID = 1L;

	public PaymentInfoNotFoundException(String message) {
        super(message);
    }

    public PaymentInfoNotFoundException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
