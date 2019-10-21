package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class InteractiveVoiceResponseScriptSchedule implements Serializable{
	private static final long serialVersionUID = 1L;

	private Timestamp beginingHour;
	private Timestamp endingHour;
	private int dayOfWeek;
	private int priority;
	
	public void setBeginingHour(Timestamp beginingHour) {
		this.beginingHour = beginingHour;
	}
	public Timestamp getBeginingHour() {
		return beginingHour;
	}
	public void setEndingHour(Timestamp endingHour) {
		this.endingHour = endingHour;
	}
	public Timestamp getEndingHour() {
		return endingHour;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getPriority() {
		return priority;
	}
	
}