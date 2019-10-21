package com.interax.telephony.service.exception.callingcard;

public class CallingCardInvalidDnByLocationException extends CallingCardException  {

    private static final long serialVersionUID = 1L;

    public CallingCardInvalidDnByLocationException(String message) {
        super(message);
    }

    public CallingCardInvalidDnByLocationException(String message,StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

}
