package com.interax.telephony.service.remote.balancerecharge.exception;

import com.interax.telephony.service.exception.InteraxTelephonyException;

/**
 *
 * @author abustamante
 */
public class RechargeOptionNotFoundException extends InteraxTelephonyException {
	private static final long serialVersionUID = 1L;

	public RechargeOptionNotFoundException(String message) {
        super(message);
    }

    public RechargeOptionNotFoundException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
