package com.interax.telephony.service.remote.pickdialing;

import java.util.List;

import javax.ejb.Remote;

import com.interax.telephony.service.exception.pickdialing.PickDialingException;
import com.interax.telephony.service.pickdialing.data.PickDialingTransactionalRequest;
import com.interax.telephony.service.pickdialing.data.PickDialingTransactionalResponse;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;


@Remote
public interface PickDialingTransactionalController extends InteraxTelephonyGenericEjb
{
	/* Atomic Simple Customer Management Methods */
	
	public PickDialingTransactionalResponse loadCustomer(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> loadCustomers(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse addCustomer(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> addCustomers(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse updateCustomer(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> updateCustomers(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse removeCustomer(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> removeCustomers(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	

	/* Transactional Simple Customer Management Methods */
	
	public PickDialingTransactionalResponse loadCustomer(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> loadCustomers(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse addCustomer(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> addCustomers(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse updateCustomer(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> updateCustomers(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse removeCustomer(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> removeCustomers(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	
	/* Atomic Complete Customer Management Methods */
	
	public PickDialingTransactionalResponse loadCompleteCustomer(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> loadCompleteCustomers(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse addCompleteCustomer(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> addCompleteCustomers(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse updateCompleteCustomer(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> updateCompleteCustomers(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse removeCompleteCustomer(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> removeCompleteCustomers(List<PickDialingTransactionalRequest> requests) throws PickDialingException;

	
	/* Transactional Complete Customer Management Methods */
	
	public PickDialingTransactionalResponse loadCompleteCustomer(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> loadCompleteCustomers(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse addCompleteCustomer(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> addCompleteCustomers(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse updateCompleteCustomer(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> updateCompleteCustomers(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse removeCompleteCustomer(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> removeCompleteCustomers(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;

	/* Atomic Simple Ani Management Methods */
	
	public PickDialingTransactionalResponse loadAni(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> loadAnis(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse addAni(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> addAnis(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse updateAni(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> updateAnis(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	public PickDialingTransactionalResponse removeAni(PickDialingTransactionalRequest request) throws PickDialingException;
	public List<PickDialingTransactionalResponse> removeAnis(List<PickDialingTransactionalRequest> requests) throws PickDialingException;
	
	/* Transactional Simple Ani Management Methods */
	
	public PickDialingTransactionalResponse loadAni(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> loadAnis(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse addAni(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> addAnis(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse updateAni(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> updateAnis(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	public PickDialingTransactionalResponse removeAni(PickDialingTransactionalRequest request, Long transactionId) throws PickDialingException;
	public List<PickDialingTransactionalResponse> removeAnis(List<PickDialingTransactionalRequest> requests, Long transactionId) throws PickDialingException;
	
	/* Transaction Management Methods */
	
	public Long beginTransaction() throws Exception;
	public void commitTransaction(Long transactionId) throws Exception;
	public void rollbackTransaction(Long transactionId) throws Exception;
	
	
}

