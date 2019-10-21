package com.interax.telephony.service.data;

import java.io.Serializable;
import java.util.Calendar;

import com.interax.telephony.service.callingcard.data.CallingCardAccessType;
import com.interax.telephony.service.callingcard.data.CallingCardCommitData;
import com.interax.telephony.service.callingcard.data.CallingCardServiceType;
import com.interax.telephony.util.GeneralUtils;


public abstract class ServiceData implements Serializable {
	
	protected int serviceFamily;
	
	public ServiceFamily getServiceFamily() {
		return ServiceFamily.values()[this.serviceFamily];
	}
	public void setServiceFamily(ServiceFamily serviceFamily) {
		this.serviceFamily = serviceFamily.ordinal();
	}
	public void setServiceFamily(String serviceFamily) {
		this.serviceFamily = ServiceFamily.valueOf(serviceFamily).ordinal();
	}	

	@Override
	public String toString() {
		return this.toString("\n", ": ");
	}
	
	public String toString(String fieldSeparator, String valueSeparator) {
		return this.toString(fieldSeparator, valueSeparator, false);
	}

	public String toString(String fieldSeparator, String valueSeparator, boolean calendarInMillis) {
		return GeneralUtils.toText(this, fieldSeparator, valueSeparator, calendarInMillis);
	}

	public static void main(String[] args) {
		CallingCardCommitData callingCardCommitData = new CallingCardCommitData();
		callingCardCommitData.setAccessType(CallingCardAccessType.IVR_PIN_FREE);
		callingCardCommitData.setAnswerTime(Calendar.getInstance());
		callingCardCommitData.setBillSeconds(10);
		callingCardCommitData.setCallDuration(23);
		callingCardCommitData.setCdrId("1223-45");
		callingCardCommitData.setDialStatus(ServiceDialStatus.ANSWER);
		callingCardCommitData.setHangupCause(16);
		callingCardCommitData.setReservationId(123456L);
		callingCardCommitData.setServiceFamily(ServiceFamily.CALLING_CARD);
		callingCardCommitData.setServiceType(CallingCardServiceType.CALLING_CARD_PLATINUM);
		callingCardCommitData.setStartTime(Calendar.getInstance());
		callingCardCommitData.setStopTime(Calendar.getInstance());
		
		String text = GeneralUtils.toText(callingCardCommitData);
		//		String text = callingCardCommitData.toText();

		
		System.out.println(callingCardCommitData);
		System.out.println("-----------------------------------");
		System.out.println(text);
		System.out.println("-----------------------------------");
		// ANTES: System.out.println(ServiceData.fromText(text));
		System.out.println(GeneralUtils.fromText(text));
		
//		CallingCardCommitData callingCardCommitData2 = new CallingCardCommitData();
//		callingCardCommitData2.fromText(text);
//		
//		String text2 = callingCardCommitData.toText();
//		System.out.println(text2);

	}

}
