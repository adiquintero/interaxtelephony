package com.interax.telephony.service.voicetraffic.agi.outgoing;

public enum VoiceTrafficOutgoingAgiStep {

	INIT,
	ANSWER,
	INIT_RESOURCES,
	PROCESS_CALL_DATA,
	VALIDATE_DESTINY,
	MAKE_CALL,
	HANGUP,
	STOP
}
