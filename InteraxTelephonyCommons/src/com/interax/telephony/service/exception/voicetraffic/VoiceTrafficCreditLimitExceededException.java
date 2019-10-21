package com.interax.telephony.service.exception.voicetraffic;


public class VoiceTrafficCreditLimitExceededException extends VoiceTrafficException{
	private static final long serialVersionUID = 1L;

	public VoiceTrafficCreditLimitExceededException(String message) { super(message);}
    
    public VoiceTrafficCreditLimitExceededException(String message,
        StackTraceElement[] stackTrace) {
        super(message);
        setStackTrace(stackTrace);
    }
}