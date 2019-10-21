package com.interax.telephony.service.callingcard.agi;

import com.interax.telephony.audio.InteraxTelephonyAudio;


public class CallingCardAgiAudio extends InteraxTelephonyAudio {
	
	private static final String BASE_DIR = "callingcard" + PATH_SEPARATOR;

	public static final String WELCOME = BASE_DIR + "cc-welcome";
	public static final String ENTER_LANGUAGE = BASE_DIR + "cc-enter-language";
	
	public static final String ENTER_SECRET = BASE_DIR + "cc-enter-secret";
	public static final String INVALID_SECRET = BASE_DIR + "cc-invalid-pin";
	public static final String ENTER_PASSWORD = BASE_DIR + "cc-enter-password";
	public static final String INVALID_PASSWORD = BASE_DIR + "cc-invalid-password";
	public static final String NO_INPUT = BASE_DIR + "cc-no-input";

	public static final String DISABLED_PIN = BASE_DIR + "cc-disabled-pin";
	public static final String EXPIRED_PIN = BASE_DIR + "cc-expired-pin";
	public static final String BALANCE_IS = BASE_DIR + "cc-your-balance";
	public static final String BALANCE_UNITS = BASE_DIR + "cc-units-";
	public static final String BALANCE_AND = BASE_DIR + "cc-balance-and";
	public static final String BALANCE_CENTS = BASE_DIR + "cc-cents-";
	
	public static final String SPECIAL_MENU_OPTIONS = BASE_DIR + "cc-special-menu";
	public static final String INVALID_OPTION = BASE_DIR + "cc-invalid-option";
	
	public static final String ENTER_NEW_PASSWORD = BASE_DIR + "cc-enter-new-password";
	public static final String CONFIRM_NEW_PASSWORD = BASE_DIR + "cc-confirm-new-password";
	public static final String NEW_PASSWORD_MISMATCH = BASE_DIR + "cc-new-password-mismatch";
	public static final String PASSWORD_CHANGE_SUCCESS = BASE_DIR + "cc-password-change-success";
	public static final String PASSWORD_CHANGE_ERROR = BASE_DIR + "cc-password-change-error";

	public static final String ENTER_DESTINY = BASE_DIR + "cc-enter-dni";
	public static final String CALL_NO_ENOUGH_BALANCE = BASE_DIR + "cc-no-enough-balance";
	public static final String CALL_TIME_IS = BASE_DIR + "cc-call-time-is";
	public static final String CC_PROCESSING_CALL =  BASE_DIR +  "cc-processing-call";
	

	public static final String CALL_BUSY = BASE_DIR + "cc-line-busy";
	public static final String CALL_NOANSWER = BASE_DIR + "cc-no-answer";
	public static final String CALL_INVALID_DESTINY = BASE_DIR + "cc-invalid-dni";
	public static final String CALL_TECH_PROBLEM = BASE_DIR + "cc-tech-problem";
	public static final String CALL_RESTRICTED = BASE_DIR + "cc-restricted-call";
	public static final String CALL_MIN_BALANCE = BASE_DIR + "cc-not-min-balance";
	public static final String CALL_RATE_NOT_FOUND = BASE_DIR + "cc-rate-not-found";
	
	public static final String MINUTE = BASE_DIR + "cc-minute";
	public static final String MINUTES = BASE_DIR + "cc-minutes";
	
	public static final String AND = BASE_DIR + "cc-and";
	
	public static final String SECOND = BASE_DIR + "cc-second";
	public static final String SECONDS = BASE_DIR + "cc-seconds";
	
	public static final String CREDIT_LIMIT_EXCEEDED = BASE_DIR + "cc-credit-limit-exceeded";

	public static final String CALL_UNATHORIZED = BASE_DIR + "reorder";
        public static final String OVERDUE_INVOICE_LIMIT_EXCEEDED = BASE_DIR + "reorder";
        public static final String MAX_CONCURRENT_CALL_LIMIT_EXCEEDED = BASE_DIR + "reorder";

        public static final String RESTRICTED_DN_1 = BASE_DIR + "cc-restricted-dn1"; //Estimado usuario de Alodiga, le informamos que el número de acceso según su código de área es el
        public static final String RESTRICTED_DN_2 = BASE_DIR + "cc-restricted-dn2"; //le repetimos el
        public static final String RESTRICTED_DN_3 = BASE_DIR + "cc-restricted-dn3"; //Si tiene alguna pregunta favor llamar a Atención al Cliente al
        public static final String RESTRICTED_DN_4 = BASE_DIR + "cc-restricted-dn4"; //Muchas Gracias por usar Alodiga.

		
        
}
