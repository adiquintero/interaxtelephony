package com.interax.telephony.service.remote.balancerecharge.exception;

import com.interax.telephony.service.exception.InteraxTelephonyException;

/**
 *
 * @author abustamante
 */
public class ContractNotFoundException extends InteraxTelephonyException {
	private static final long serialVersionUID = 1L;

	public ContractNotFoundException(String message) {
        super(message);
    }

    public ContractNotFoundException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
