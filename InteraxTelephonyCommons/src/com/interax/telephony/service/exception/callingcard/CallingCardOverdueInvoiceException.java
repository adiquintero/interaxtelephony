package com.interax.telephony.service.exception.callingcard;

public class CallingCardOverdueInvoiceException extends CallingCardException {

    private static final long serialVersionUID = 1L;

    public CallingCardOverdueInvoiceException(String message) {
        super(message);
    }

    public CallingCardOverdueInvoiceException(String message,
            StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}
