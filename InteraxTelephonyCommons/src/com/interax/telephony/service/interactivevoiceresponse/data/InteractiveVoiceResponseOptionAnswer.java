package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;

public class InteractiveVoiceResponseOptionAnswer implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long dialogId;
	private String value;
	private Long optionId;
	private Long scriptAnswerId;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}
	public Long getDialogId() {
		return dialogId;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}
	public Long getOptionId() {
		return optionId;
	}
	public void setScriptAnswerId(Long scriptAnswerId) {
		this.scriptAnswerId = scriptAnswerId;
	}
	public Long getScriptAnswerId() {
		return scriptAnswerId;
	}
	
	
	
	
}