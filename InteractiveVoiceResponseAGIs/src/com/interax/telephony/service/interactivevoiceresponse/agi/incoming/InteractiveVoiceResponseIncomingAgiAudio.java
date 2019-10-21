package com.interax.telephony.service.interactivevoiceresponse.agi.incoming;

import com.interax.telephony.audio.InteraxTelephonyAudio;


public class InteractiveVoiceResponseIncomingAgiAudio extends InteraxTelephonyAudio {
	
	private static final String BASE_DIR = "ivr" + PATH_SEPARATOR + "incoming_ivr" + PATH_SEPARATOR;

	public static final String CALL_TECH_PROBLEM = BASE_DIR + "tech-problem";
	public static final String CALL_INVALID_DESTINY = BASE_DIR + "invalid-dni";
	public static final String CALL_RESTRICTED = BASE_DIR + "restricted-call";
	public static final String CALL_RATE_NOT_FOUND = BASE_DIR + "rate-not-found";
	public static final String CALL_NO_ENOUGH_BALANCE = BASE_DIR + "no-enough-balance";
	public static final String CALL_BUSY = BASE_DIR + "line-busy";
	public static final String CALL_NOANSWER = BASE_DIR + "no-answer";
	
	public static final String CREDIT_LIMIT_EXCEEDED = BASE_DIR + "credit-limit-exceeded";

	public static final String CALL_UNATHORIZED = BASE_DIR + "reorder";
    public static final String OVERDUE_INVOICE_LIMIT_EXCEEDED = BASE_DIR + "reorder";
    public static final String MAX_CONCURRENT_CALL_LIMIT_EXCEEDED = BASE_DIR + "reorder";
}
