package com.interax.telephony.service.voicetraffic.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ServiceReservation;

public class VoiceTrafficReservation extends ServiceReservation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "VoiceTrafficReservation{id="+this.getId()+",availableSeconds="+this.getAvailableSeconds()+",baseUnit="+this.getBaseUnit()+",reservationStatus="+this.getReservationStatus()+"}";
	}
}
