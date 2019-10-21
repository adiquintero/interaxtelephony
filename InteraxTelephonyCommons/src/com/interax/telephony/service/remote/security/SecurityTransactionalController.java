package com.interax.telephony.service.remote.security;

import com.interax.telephony.service.exception.security.ITSecurityException;
import com.interax.telephony.service.security.data.ITSecurityTransactionalRequest;
import com.interax.telephony.service.security.data.ITSecurityTransactionalResponse;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SecurityTransactionalController extends InteraxTelephonyGenericEjb{
	public static final String CURRENT_Did_ACTIVATION_ATTEMPTS_KEY = "CURRENT_Did_ACTIVATION_ATTEMPTS";
	public static final String MAX_Did_ACTIVATION_ATTEMPTS_KEY = "MAX_Did_ACTIVATION_ATTEMPTS";
	public static final String Did_ACTIVATION_CODE_LENGTH_KEY = "Did_ACTIVATION_CODE_LENGTH_KEY";

	/* Atomic Simple ITSeRegisterIntent Management Methods */

	public ITSecurityTransactionalResponse loadITSeRegisterIntent(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> loadITSeRegisterIntents(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> loadITSeRegisterIntentss(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public ITSecurityTransactionalResponse addITSeRegisterIntent(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> addITSeRegisterIntents(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public ITSecurityTransactionalResponse updateITSeRegisterIntent(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> updateITSeRegisterIntents(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public ITSecurityTransactionalResponse removeITSeRegisterIntent(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> removeITSeRegisterIntents(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;

	/* Atomic Simple ITSeRegisterDetails Management Methods */

	public ITSecurityTransactionalResponse loadITSeRegisterDetails(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> loadITSeRegisterDetailss(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public ITSecurityTransactionalResponse addITSeRegisterDetails(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> addITSeRegisterDetailss(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public ITSecurityTransactionalResponse updateITSeRegisterDetails(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> updateITSeRegisterDetailss(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public ITSecurityTransactionalResponse removeITSeRegisterDetails(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> removeITSeRegisterDetailss(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;

	/* Atomic Simple ITIPBlackList Management Methods */

	public ITSecurityTransactionalResponse loadITSeIpBlackList(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> loadITSeIpBlackLists(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public ITSecurityTransactionalResponse addITSeIpBlackList(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> addITSeIpBlackLists(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public ITSecurityTransactionalResponse updateITSeIpBlackList(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> updateITSeIpBlackLists(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;
	public ITSecurityTransactionalResponse removeITSeIpBlackList(ITSecurityTransactionalRequest request) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> removeITSeIpBlackLists(List<ITSecurityTransactionalRequest> requests) throws ITSecurityException;

	/* Transaction Management Methods */

	public Long beginTransaction() throws Exception;
	public void commitTransaction(Long transactionId) throws Exception;
	public void rollbackTransaction(Long transactionId) throws Exception;


	/* Transactional Simple ITSERegisterIntent Management Methods */

	public ITSecurityTransactionalResponse loadITSeRegisterIntent(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> loadITSeRegisterIntents(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse addITSeRegisterIntent(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> addITSeRegisterIntents(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse updateITSeRegisterIntent(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> updateITSeRegisterIntents(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse removeITSeRegisterIntent(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> removeITSeRegisterIntents(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;

	
	/* Transactional Simple ITSeRegisterDetails Management Methods */

	public ITSecurityTransactionalResponse loadITSeRegisterDetails(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> loadITSeRegisterDetailss(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse addITSeRegisterDetails(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> addITSeRegisterDetailss(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse updateITSeRegisterDetails(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> updateITSeRegisterDetailss(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse removeITSeRegisterDetails(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> removeITSeRegisterDetailss(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;

	

	/* Transactional Simple ITSeIpBlackList Management Methods */

	public ITSecurityTransactionalResponse loadITSeIpBlackList(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> loadITSeIpBlackLists(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse addITSeIpBlackList(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> addITSeIpBlackLists(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse updateITSeIpBlackList(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> updateITSeIpBlackLists(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;
	public ITSecurityTransactionalResponse removeITSeIpBlackList(ITSecurityTransactionalRequest request, Long transactionId) throws ITSecurityException;
	public List<ITSecurityTransactionalResponse> removeITSeIpBlackLists(List<ITSecurityTransactionalRequest> requests, Long transactionId) throws ITSecurityException;

}
