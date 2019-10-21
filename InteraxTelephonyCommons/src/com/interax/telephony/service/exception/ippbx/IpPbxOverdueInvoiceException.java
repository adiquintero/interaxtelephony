package com.interax.telephony.service.exception.ippbx;

public class IpPbxOverdueInvoiceException extends IpPbxException {

    private static final long serialVersionUID = 1L;

    public IpPbxOverdueInvoiceException(String message) {
        super(message);
    }

    public IpPbxOverdueInvoiceException(String message,
            StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}
