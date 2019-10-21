package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ServiceReservation;

public class InteractiveVoiceResponseReservation extends ServiceReservation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "InteractiveVoiceResponseReservation{id="+this.getId()+",availableSeconds="+this.getAvailableSeconds()+",baseUnit="+this.getBaseUnit()+",reservationStatus="+this.getReservationStatus()+"}";
	}
}
