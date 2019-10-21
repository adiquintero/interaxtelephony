package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

public class InteractiveVoiceResponseDialog implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long scriptId; 			
	private String phrase;
	private String title;
	private String type;
	private String action;
	private String actionData;
	private List<InteractiveVoiceResponseOptionAnswer> optionAnswers; 
	private List<InteractiveVoiceResponseOption> options;
	private String speech;
	
	public InteractiveVoiceResponseDialogType getType() {
		return InteractiveVoiceResponseDialogType.valueOf(type);
	}
	public void setType(InteractiveVoiceResponseDialogType type) {
		this.type = type.name();
	}
	public void setType(String type) {
		this.type = type;
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
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setScriptId(Long scriptId) {
		this.scriptId = scriptId;
	}
	public Long getScriptId() {
		return scriptId;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public String getPhrase() {
		return phrase;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}

	public void setOptionAnswers(List<InteractiveVoiceResponseOptionAnswer> optionAnswers) {
		this.optionAnswers = optionAnswers;
	}
	public List<InteractiveVoiceResponseOptionAnswer> getOptionAnswers() {
		return optionAnswers;
	}
	public void setOptions(List<InteractiveVoiceResponseOption> options) {
		this.options = options;
	}
	public List<InteractiveVoiceResponseOption> getOptions() {
		return options;
	}
	public void setActionData(String actionData) {
		this.actionData = actionData;
	}
	public String getActionData() {
		return actionData;
	}
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}	
	public InteractiveVoiceResponseOption getOptionByAnswer(String o) {
		System.out.println( "En ivrDialog " + o);
		ListIterator<InteractiveVoiceResponseOption> options = this.getOptions().listIterator();
		switch (this.getType()) {
			case OPEN:
				int value = Integer.parseInt((o));
	              while(options.hasNext())
	               {
	            	InteractiveVoiceResponseOption option = options.next();
	                if(option.getMinLimit()<= value && option.getMaxLimit()>= value) return option;
	               }        
			break;

			case CLOSED:
			    
				char dtmf =  o.charAt(0);
	            while(options.hasNext())
	               {
	            	InteractiveVoiceResponseOption option = options.next();
	                if(option.getDtmf() == dtmf) return option;
	               }
			break;
		}
		
		return null;
	}
	
	
	
}