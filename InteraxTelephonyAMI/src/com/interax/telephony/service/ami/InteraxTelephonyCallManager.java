package com.interax.telephony.service.ami;

import org.asteriskjava.live.AsteriskChannel;

import com.interax.telephony.service.exception.InteraxTelephonyException;
import com.interax.telephony.service.remote.RaterEJB;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;


public abstract class InteraxTelephonyCallManager extends Thread {
	
	protected AsteriskChannel incomingChannel;
	protected AsteriskChannel outgoingChannel;
	protected boolean alive;
	protected boolean toHangup;
	protected int sleepTime;
	protected int safetySecondsToHangup;
	protected InteraxTelephonyGenericEjbCaller genericEjbCaller;
	
	public InteraxTelephonyCallManager(AsteriskChannel incomingChannel, AsteriskChannel outgoingChannel, int sleepTime, int safetySecondsToHangup, InteraxTelephonyGenericEjbCaller genericEjbCaller){
		this.incomingChannel = incomingChannel;
		this.outgoingChannel = outgoingChannel;
		this.alive = true;
		this.sleepTime = sleepTime;
		this.safetySecondsToHangup = safetySecondsToHangup;
		this.genericEjbCaller = genericEjbCaller;
	}
	
    public abstract void run();
	
	public Integer getBlock(long reservationId, String server, String port) throws InteraxTelephonyException {
		
		RaterEJB raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", server, port);
		return raterEJB.getBlock(reservationId);
	}
	
	public boolean getBlock(long reservationId, long blocks, String server, String port) throws InteraxTelephonyException {
		
		RaterEJB raterEJB = (RaterEJB)this.genericEjbCaller.getGenericController("ejb/RaterEJB", server, port);
		return (Boolean)raterEJB.getBlock(reservationId, blocks);
	}
}
