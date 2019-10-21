package com.interax.telephony.service.remote;

import com.interax.telephony.service.data.ServiceCancelData;
import com.interax.telephony.service.data.ServiceCommitData;
import com.interax.telephony.service.data.ServiceRequestData;
import com.interax.telephony.service.data.ServiceReservation;
import com.interax.telephony.service.exception.InteraxTelephonyException;
import com.interax.telephony.service.exception.NoOpenReservationException;
import com.interax.telephony.service.exception.ReservationNotFoundException;
import com.interax.telephony.service.exception.callingcard.CallingCardCreditLimitExceededException;
import com.interax.telephony.service.exception.callingcard.CallingCardGeneralException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidAccessTypeException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidCountryCodeException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidDnException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidPinException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidServiceTypeException;
import com.interax.telephony.service.exception.callingcard.CallingCardNotEnoughBalanceException;
import com.interax.telephony.service.exception.callingcard.CallingCardRateNotFoundException;
import com.interax.telephony.service.exception.callingcard.CallingCardRestrictedCallException;
import com.interax.telephony.service.exception.ippbx.IpPbxCreditLimitExceededException;
import com.interax.telephony.service.exception.ippbx.IpPbxGeneralException;
import com.interax.telephony.service.exception.ippbx.IpPbxInvalidAccessTypeException;
import com.interax.telephony.service.exception.ippbx.IpPbxInvalidCountryCodeException;
import com.interax.telephony.service.exception.ippbx.IpPbxInvalidServiceTypeException;
import com.interax.telephony.service.exception.ippbx.IpPbxNotEnoughBalanceException;
import com.interax.telephony.service.exception.ippbx.IpPbxRateNotFoundException;
import com.interax.telephony.service.exception.ippbx.IpPbxRestrictedCallException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficGeneralException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficNotEnoughBalanceException;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;

public interface RaterEJB extends InteraxTelephonyGenericEjb 
{
	/**
	 * @param serviceRequestData
	 * @return
	 * @throws CallingCardInvalidPinException
	 * @throws CallingCardInvalidServiceTypeException
	 * @throws CallingCardInvalidAccessTypeException
	 * @throws CallingCardInvalidCountryCodeException
	 * @throws CallingCardInvalidDnException
	 * @throws CallingCardNotEnoughBalanceException
	 * @throws CallingCardRestrictedCallException
	 * @throws CallingCardCreditLimitExceededException
	 * @throws CallingCardRateNotFoundException
	 * @throws CallingCardGeneralException
	 * 
	 * @throws IpPbxInvalidServiceTypeException
	 * @throws IpPbxInvalidAccessTypeException
	 * @throws IpPbxInvalidCountryCodeException
	 * @throws IpPbxRestrictedCallException
	 * @throws IpPbxCreditLimitExceededException
	 * @throws IpPbxRateNotFoundException
	 * @throws IpPbxNotEnoughBalanceException
	 * @throws IpPbxGeneralException
	 * @throws VoiceTrafficNotEnoughBalanceException
	 * @throws VoiceTrafficGeneralException
	 */
	
	public ServiceReservation createReservation(ServiceRequestData serviceRequestData) throws InteraxTelephonyException;
	

	/**
	 * @param reservationId
	 * @param callingCardCancelData
	 * @return
	 * @throws NoOpenReservationException
	 * @throws ReservationNotFoundException
	 * @throws InteraxTelephonyException
	 */
	public boolean cancelReservation(Long reservationId, ServiceCancelData cancelData) 
	throws NoOpenReservationException, ReservationNotFoundException, InteraxTelephonyException;
	
	/**
	 * @param reservationId
	 * @param callingCardCommitData
	 * @return
	 * @throws NoOpenReservationException
	 * @throws ReservationNotFoundException
	 * @throws InteraxTelephonyException
	 */
	public boolean	commitReservation(Long reservationId, ServiceCommitData commitData) 
	throws NoOpenReservationException, ReservationNotFoundException, InteraxTelephonyException;
	
	/**
	 * @param reservationId
	 * @return
	 * @throws NoOpenReservationException
	 * @throws ReservationNotFoundException
	 * @throws InteraxTelephonyException
	 */
	public Integer	getBlock(Long reservationId) 
	throws NoOpenReservationException, ReservationNotFoundException, InteraxTelephonyException;
	
	/**
	 * @param reservationId
	 * @param blocks
	 * @return
	 * @throws NoOpenReservationException
	 * @throws ReservationNotFoundException
	 */
	public boolean getBlock(Long reservationId, Long blocks) 
	throws NoOpenReservationException, ReservationNotFoundException,InteraxTelephonyException;
}
