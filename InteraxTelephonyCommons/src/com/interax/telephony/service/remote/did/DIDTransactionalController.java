package com.interax.telephony.service.remote.did;

import java.util.List;
import javax.ejb.Remote;


import com.interax.telephony.service.did.data.DIDTransactionalRequest;
import com.interax.telephony.service.did.data.DIDTransactionalResponse;
import com.interax.telephony.service.exception.did.DIDException;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;


@Remote
public interface DIDTransactionalController extends InteraxTelephonyGenericEjb
{
	public static final String CURRENT_EQUIPMENT_ACTIVATION_ATTEMPTS_KEY = "CURRENT_EQUIPMENT_ACTIVATION_ATTEMPTS";
	public static final String MAX_EQUIPMENT_ACTIVATION_ATTEMPTS_KEY = "MAX_EQUIPMENT_ACTIVATION_ATTEMPTS";
	public static final String EQUIPMENT_ACTIVATION_CODE_LENGTH_KEY = "EQUIPMENT_ACTIVATION_CODE_LENGTH_KEY";


	/* Atomic Simple didService Management Methods */

	public DIDTransactionalResponse loadDIDService(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> loadIpPbxs(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse addDID(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> addDIDs(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse updateDID(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> updateDIDs(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse removeIpPbx(DIDTransactionalRequest request) throws DIDException;
	public  List<DIDTransactionalResponse> removeIpPbxs(List<DIDTransactionalRequest> requests) throws DIDException;

/* Atomic Simple didDid Management Methods */
	
	public DIDTransactionalResponse loadDidDid(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> loadDidDids(List<DIDTransactionalRequest> requests) throws DIDException;	
	public DIDTransactionalResponse addDidDid(DIDTransactionalRequest request) throws DIDException;	
	public List<DIDTransactionalResponse> addDidDids(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse updateDidDid(DIDTransactionalRequest request) throws DIDException;	
	public List<DIDTransactionalResponse> updateDidDids(List<DIDTransactionalRequest> requests) throws DIDException;	
	public DIDTransactionalResponse removeDidDid(DIDTransactionalRequest request) throws DIDException;	
	public List<DIDTransactionalResponse> removeDidDids(List<DIDTransactionalRequest> requests) throws DIDException;
	
/* Atomic Complete DidService Methods */
	
	public DIDTransactionalResponse loadCompleteDID(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> loadCompleteDIDs(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse addCompleteDID(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> addCompleteDIDs(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse updateCompleteDID(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> updateCompleteDIDs(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse removeCompleteDID(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> removeCompleteDIDs(List<DIDTransactionalRequest> requests) throws DIDException;
	
/* Atomic Complete Extension Methods */
	
	public DIDTransactionalResponse loadCompleteDidDid(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> loadCompleteDidDids(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse addCompleteDidDid(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> addCompleteDidDids(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse updateCompleteDidDid(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> updateCompleteDidDids(List<DIDTransactionalRequest> requests) throws DIDException;
	public DIDTransactionalResponse removeCompleteDidDid(DIDTransactionalRequest request) throws DIDException;
	public List<DIDTransactionalResponse> removeCompleteDidDids(List<DIDTransactionalRequest> requests) throws DIDException;
	
/* Transaction Management Methods */

	public Long beginTransaction() throws Exception;
	public void commitTransaction(Long transactionId) throws Exception;
	public void rollbackTransaction(Long transactionId) throws Exception;

/* Transactional Simple DID Management Methods */

	public DIDTransactionalResponse loadDID(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> loadDIDs(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse addDID(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> addDIDs(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse updateDID(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> updateDIDs(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse removeDID(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> removeDIDs(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	
/* Transactional Simple DidDid Management Methods */
	
	public DIDTransactionalResponse loadDidDid(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> loadDidDids(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse addDidDid(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> addDidDids(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse updateDidDid(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> updateDidDids(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse removeDidDid(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> removeDidDids(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;

/* Transactional Complete DID Methods */

	public DIDTransactionalResponse loadCompleteDID(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> loadCompleteDIDs(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse addCompleteDID(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> addCompleteDIDs(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse updateCompleteDID(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> updateCompleteDIDs(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse removeCompleteDID(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> removeCompleteDIDs(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	
/* Transactional Complete Extension Methods */

	public DIDTransactionalResponse loadCompleteDidDid(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> loadCompleteDidDids(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse addCompleteDidDid(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> addCompleteDidDids(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse updateCompleteDidDid(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> updateCompleteDidDids(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	public DIDTransactionalResponse removeCompleteDidDid(DIDTransactionalRequest request, Long transactionId) throws DIDException;
	public List<DIDTransactionalResponse> removeCompleteDidDids(List<DIDTransactionalRequest> requests, Long transactionId) throws DIDException;
	
}

