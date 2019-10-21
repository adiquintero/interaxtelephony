package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ServiceReservation;

public class IpPbxReservation extends ServiceReservation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "IpPbxReservation{id="+this.getId()+",availableSeconds="+this.getAvailableSeconds()+",baseUnit="+this.getBaseUnit()+",reservationStatus="+this.getReservationStatus()+"}";
	}
}
