package com.interax.telephony.service.callingcard.disableddn.agi;

public enum DisabledDnAgiStep {

	INIT,
	ANSWER,
	INIT_RESOURCES,
	GET_ALTERNATIVE_DN,
        PLAY_CUSTOM_AUDIO,
        GET_CALL_TRANSFERENCE_INPUT,
        TRANSFER,
	HANGUP,
	STOP
}
