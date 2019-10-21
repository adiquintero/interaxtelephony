package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficGeneralException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficGeneralException(String message) {
		super(message);
	}
    
    public VoiceTrafficGeneralException(String message, StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
    
    public VoiceTrafficGeneralException(Exception e) {
    	super(e);
    }
}