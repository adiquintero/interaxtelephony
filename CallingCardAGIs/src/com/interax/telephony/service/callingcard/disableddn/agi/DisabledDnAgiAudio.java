package com.interax.telephony.service.callingcard.disableddn.agi;

import com.interax.telephony.audio.InteraxTelephonyAudio;


public class DisabledDnAgiAudio extends InteraxTelephonyAudio {
	
	private static final String BASE_DIR = "callingcard" + PATH_SEPARATOR;

	public static final String WELCOME = BASE_DIR + "cc-welcome";
	
	public static final String NO_INPUT = BASE_DIR + "cc-no-input";

	public static final String CALL_TECH_PROBLEM = BASE_DIR + "cc-tech-problem";

        public static final String PLEASE_DIAL_FOLLOWING_NUMBER = BASE_DIR + "cc-dial-following-number";

        public static final String CALL_TRANSFERENCE = BASE_DIR + "cc-transfer-atc";

        public static final String UNAVAILABLE = BASE_DIR + "cc-unavailable-number";

        public static final String CALL_TRANSFERENCE_OPTIONS_EXTENDED = BASE_DIR + "cc-transference-options-extended";

        public static final String CALL_TRANSFERENCE_OPTIONS = BASE_DIR + "cc-transference-options";

}
