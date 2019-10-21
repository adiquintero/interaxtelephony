package com.interax.telephony.service.remote.balancerecharge.exception;

import com.interax.telephony.service.exception.InteraxTelephonyException;

public class PinNotFoundException extends InteraxTelephonyException{

    private static final long serialVersionUID = 1L;

    public PinNotFoundException(String message) {
        super(message);
    }

    public PinNotFoundException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
}
