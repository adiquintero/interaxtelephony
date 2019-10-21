package com.interax.telephony.service.remote.balancerecharge.exception;

import com.interax.telephony.service.exception.InteraxTelephonyException;

/**
 *
 * @author abustamante
 */
public class GeneralException extends InteraxTelephonyException {

    private static final long serialVersionUID = 1L;

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
