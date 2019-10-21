package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.List;

public class InteractiveVoiceResponseScript implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String scriptTitle;
	private List<InteractiveVoiceResponseDialog> dialogs;
	private Long initialDialogId;
	private long callId;
	private String incorrectPhrase;
	private String timeoutPhrase;
	

	private HashMap<Long,InteractiveVoiceResponseDialog> dialogsInHash;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getCallId() {
		return callId;
	}
	public void setCallId(long callId) {
		this.callId = callId;
	}
	public void setScriptTitle(String scriptTitle) {
		this.scriptTitle = scriptTitle;
	}
	public String getScriptTitle() {
		return scriptTitle;
	}
	public void setInitialDialogId(Long initialDialogId) {
		this.initialDialogId = initialDialogId;
	}
	public Long getInitialDialogId() {
		return initialDialogId;
	}
	public void setDialogs(List<InteractiveVoiceResponseDialog> dialogs) {
		this.dialogs = dialogs;
	}
	public List<InteractiveVoiceResponseDialog> getDialogs() {
		return dialogs;
	}
	public InteractiveVoiceResponseDialog getDialog(long dialogId){
		
		if(dialogsInHash == null){
			dialogsInHash = new HashMap<Long, InteractiveVoiceResponseDialog>();
			for (InteractiveVoiceResponseDialog dialog : dialogs) {
				dialogsInHash.put(dialog.getId(), dialog);
			}
		}
		return dialogsInHash.get(dialogId);		
	}
	public void setIncorrectPhrase(String incorrectPhrase) {
		this.incorrectPhrase = incorrectPhrase;
	}
	public String getIncorrectPhrase() {
		return incorrectPhrase;
	}
	public void setTimeoutPhrase(String timeoutPhrase) {
		this.timeoutPhrase = timeoutPhrase;
	}
	public String getTimeoutPhrase() {
		return timeoutPhrase;
	}
	
}