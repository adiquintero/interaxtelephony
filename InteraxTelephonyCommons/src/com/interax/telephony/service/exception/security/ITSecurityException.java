package com.interax.telephony.service.exception.security;

import com.interax.telephony.service.exception.InteraxTelephonyException;

	public abstract class ITSecurityException extends InteraxTelephonyException{
		private static final long serialVersionUID = 1L;

		public ITSecurityException(String message) {
			super(message);
		}
	    
	    public ITSecurityException(String message,StackTraceElement[] stackTrace) {
	        super(message);
	        setStackTrace(stackTrace);
	    }
	    
	    public ITSecurityException(Exception e) {
	    	super(e);
	    }
	}

	
