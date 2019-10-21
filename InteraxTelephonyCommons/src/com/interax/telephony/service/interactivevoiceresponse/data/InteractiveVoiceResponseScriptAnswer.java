package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;
import java.util.List;

public class InteractiveVoiceResponseScriptAnswer implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long callId;
	private List<InteractiveVoiceResponseOptionAnswer> optionAnswers;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setCallId(Long callId) {
		this.callId = callId;
	}
	public Long getCallId() {
		return callId;
	}
	public void setOptionAnswers(List<InteractiveVoiceResponseOptionAnswer> optionAnswers) {
		this.optionAnswers = optionAnswers;
	}
	public List<InteractiveVoiceResponseOptionAnswer> getOptionAnswers() {
		return optionAnswers;
	}
		
}