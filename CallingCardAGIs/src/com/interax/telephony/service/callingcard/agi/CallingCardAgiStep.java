package com.interax.telephony.service.callingcard.agi;

public enum CallingCardAgiStep {

	INIT,
	ANSWER,
	INIT_RESOURCES,
	PROCESS_CALL_DATA,
	ASK_LANGUAGE,
	AUTHENTICATE,
        PLAY_ALTERNATIVE_DN_AUDIO,
	ASK_PASSWORD,
	AUTHORIZE,
	SPECIAL_MENU,
	CHANGE_PASSWORD,
	VALIDATE_DESTINY,
	MAKE_CALL,
	HANGUP,
	STOP
}