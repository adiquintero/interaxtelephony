package com.interax.telephony.service.exception.ippbx;

public class IpPbxMaxConcurrentCallException extends IpPbxException {

    private static final long serialVersionUID = 1L;

    public IpPbxMaxConcurrentCallException(String message) {
        super(message);
    }

    public IpPbxMaxConcurrentCallException(String message,
            StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}
