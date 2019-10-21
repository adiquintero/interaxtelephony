package com.interax.telephony.service.data;

import java.io.Serializable;


public abstract class ServiceCommitData extends ServiceData implements Serializable {
	private static final long serialVersionUID = 1L;
	protected long reservationId;


	public long getReservationId() {
		return reservationId;
	}
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
}
