package com.interax.telephony.service.did.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ServiceReservation;

public class DidReservation extends ServiceReservation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "DidReservation{id="+this.getId()+",availableSeconds="+this.getAvailableSeconds()+",baseUnit="+this.getBaseUnit()+",reservationStatus="+this.getReservationStatus()+"}";
	}

}
