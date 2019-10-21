package com.interax.telephony.service.pickdialing.agi.outgoing;

public enum PickDialingOutgoingAgiStep {

	INIT,
	INIT_RESOURCES,
	HANGUP,
	PROCESS_CALL_DATA,
	VALIDATE_DESTINY,
	MAKE_CALL,
	STOP
}
