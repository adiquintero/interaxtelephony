package com.interax.telephony.service.exception.ippbx;

/**
 *
 * @author Interaxmedia
 */
public class IpPbxNullParameterException extends IpPbxException {

    private static final long serialVersionUID = 1L;

    public IpPbxNullParameterException(String message) {
        super(message);
    }

    public IpPbxNullParameterException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }

    public IpPbxNullParameterException(Exception e) {
    	super(e);
    }

}
