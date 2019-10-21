package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;
import java.util.List;

public class InteractiveVoiceResponseOption implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private char dtmf;
	private int minLimit;
	private int maxLimit;
	private String action;
	private String actionData;
	private Long dialogId;          
	private List<InteractiveVoiceResponseOptionAnswer> optionAnswers;
	private String title;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setDtmf(char dtmf) {
		this.dtmf = dtmf;
	}
	public char getDtmf() {
		return dtmf;
	}
	public void setMinLimit(int minLimit) {
		this.minLimit = minLimit;
	}
	public int getMinLimit() {
		return minLimit;
	}
	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}
	public int getMaxLimit() {
		return maxLimit;
	}
	public InteractiveVoiceResponseAction getAction() {
		return InteractiveVoiceResponseAction.valueOf(action);
	}
	public void setAction(InteractiveVoiceResponseAction action) {
		this.action = action.name();
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}
	public Long getDialogId() {
		return dialogId;
	}
	public void setOptionAnswers(List<InteractiveVoiceResponseOptionAnswer> optionAnswers) {
		this.optionAnswers = optionAnswers;
	}
	public List<InteractiveVoiceResponseOptionAnswer> getOptionAnswers() {
		return optionAnswers;
	}
	public void setActionData(String actionData) {
		this.actionData = actionData;
	}
	public String getActionData() {
		return actionData;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
		
}