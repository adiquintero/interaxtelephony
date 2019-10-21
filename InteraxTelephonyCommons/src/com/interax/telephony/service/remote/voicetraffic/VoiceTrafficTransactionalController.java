package com.interax.telephony.service.remote.voicetraffic;

import java.util.List;

import javax.ejb.Remote;

import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficException;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficTransactionalRequest;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficTransactionalResponse;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;


@Remote
public interface VoiceTrafficTransactionalController extends InteraxTelephonyGenericEjb
{
	/* Atomic Simple Customer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	
	/* Atomic Simple Peer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadPeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadPeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addPeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addPeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updatePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updatePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	
	/* Atomic Simple Did Management Methods */
	
	public VoiceTrafficTransactionalResponse loadDid(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadDids(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addDid(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addDids(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateDid(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateDids(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeDid(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeDids(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	
	/* Atomic Simple VirtualOffshoreNumber Management Methods */
	
	public VoiceTrafficTransactionalResponse loadVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	
	
	/* Transaction Management Methods */
	
	public Long beginTransaction() throws Exception;
	public void commitTransaction(Long transactionId) throws Exception;
	public void rollbackTransaction(Long transactionId) throws Exception;

	
	/* Transactional Simple Customer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	
	/* Transactional Simple Peer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadPeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadPeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addPeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addPeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updatePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updatePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	
	/* Transactional Simple Did Management Methods */
	
	public VoiceTrafficTransactionalResponse loadDid(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadDids(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addDid(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addDids(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateDid(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateDids(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeDid(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeDids(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	
	/* Transactional Simple VirtualOffshoreNumber Management Methods */
	
	public VoiceTrafficTransactionalResponse loadVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	
	/* Atomic Complete Customer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadCompleteCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addCompleteCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateCompleteCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeCompleteCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;

	/* Atomic Complete Peer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadCompletePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadCompletePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addCompletePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addCompletePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateCompletePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateCompletePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeCompletePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeCompletePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;

	/* Atomic Complete VirtualOffshoreNumber Management Methods */
	
	public VoiceTrafficTransactionalResponse loadCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException;

	
	/* Transactional Complete Customer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadCompleteCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addCompleteCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateCompleteCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeCompleteCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;

	/* Transactional Complete Peer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadCompletePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadCompletePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addCompletePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addCompletePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateCompletePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateCompletePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeCompletePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeCompletePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	
	/* Transactional Complete Customer Management Methods */
	
	public VoiceTrafficTransactionalResponse loadCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> loadCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse addCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> addCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse updateCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> updateCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;
	public VoiceTrafficTransactionalResponse removeCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException;
	public List<VoiceTrafficTransactionalResponse> removeCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException;

	
}

