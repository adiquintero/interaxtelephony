package com.interax.telephony.service.exception.callingcard;

public class CallingCardMaxConcurrentCallException extends CallingCardException {

    private static final long serialVersionUID = 1L;

    public CallingCardMaxConcurrentCallException(String message) {
        super(message);
    }

    public CallingCardMaxConcurrentCallException(String message,
            StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}
