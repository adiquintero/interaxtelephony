package com.interax.telephony.service.remote.it;

import java.util.List;

import javax.ejb.Remote;

import com.interax.telephony.service.exception.it.InteraxTelephonyGeneralException;
import com.interax.telephony.service.data.ItTransactionalRequest;
import com.interax.telephony.service.data.ItTransactionalResponse;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;

@Remote
public interface InteraxTelephonyTransactionalController extends InteraxTelephonyGenericEjb {
 
	/* Atomic Simple Provider Management Methods */
	
	public ItTransactionalResponse loadProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	
	/* Atomic Simple ProviderPeer Management Methods */
	
	public ItTransactionalResponse loadProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;

	
   /* Atomic Simple  ItOutgoingRoute Methods "Falta" */
	
	public ItTransactionalResponse loadOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	
    /* Atomic Simple ItOutgoingRouteStep Management Methods "Falta" */
	
	public ItTransactionalResponse loadOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	
	
	
	/* Transaction  Management Methods */
	
	public Long beginTransaction() throws Exception;
	public void commitTransaction(Long transactionId) throws Exception;
	public void rollbackTransaction(Long transactionId) throws Exception;

	
	/* Transactional Simple OutgoingRoute Management Methods */
	
	public ItTransactionalResponse loadOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	
	/* Transactional Simple OutgoingRouteStep Management Methods */
	
	public ItTransactionalResponse loadOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;

	
	
	/* Transactional Simple Provider Management Methods */
	
	public ItTransactionalResponse loadProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	
	/* Transactional Simple ProviderPeer Management Methods */
	
	public ItTransactionalResponse loadProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;

	
	/* Atomic Complete Provider Management Methods */
	
	public ItTransactionalResponse loadCompleteProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadCompleteProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addCompleteProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addCompleteProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateCompleteProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateCompleteProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeCompleteProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeCompleteProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;

	/* Atomic Complete ProviderPeer Management Methods */
	
	public ItTransactionalResponse loadCompleteProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadCompleteProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addCompleteProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addCompleteProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateCompleteProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateCompleteProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeCompleteProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeCompleteProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;

	
	/* Atomic Complete OutgoingRoute Management Methods */
	
	public ItTransactionalResponse loadCompleteOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadCompleteOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addCompleteOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addCompleteOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateCompleteOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateCompleteOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeCompleteOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeCompleteOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;

	
	
	
	
	/* Atomic Complete OutgoingRouteStep Management Methods */
	
	public ItTransactionalResponse loadCompleteOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addCompleteOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateCompleteOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeCompleteOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException;

	
	
	/* Transactional Complete Provider Management Methods */
	
	public ItTransactionalResponse loadCompleteProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadCompleteProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addCompleteProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addCompleteProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateCompleteProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateCompleteProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeCompleteProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeCompleteProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;

	/* Transactional Complete ProviderPeer Management Methods */
	
	public ItTransactionalResponse loadCompleteProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadCompleteProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addCompleteProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addCompleteProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateCompleteProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateCompleteProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeCompleteProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeCompleteProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException;
	

	
	/* Transactional Complete  OutgoingRoute Management Methods */
	
	public ItTransactionalResponse loadCompleteOutgoingRoute(ItTransactionalRequest request , Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadCompleteOutgoingRoutes(List<ItTransactionalRequest> requests , Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addCompleteOutgoingRoute(ItTransactionalRequest request , Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addCompleteOutgoingRoutes(List<ItTransactionalRequest> requests , Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateCompleteOutgoingRoute(ItTransactionalRequest request , Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateCompleteOutgoingRoutes(List<ItTransactionalRequest> requests , Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeCompleteOutgoingRoute(ItTransactionalRequest request , Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeCompleteOutgoingRoutes(List<ItTransactionalRequest> requests , Long transactionId) throws InteraxTelephonyGeneralException;


	/*  Transactional Complete  OutgoingRouteStep Management Methods */
	
	public ItTransactionalResponse loadCompleteOutgoingRouteStep(ItTransactionalRequest request , Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> loadCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests , Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse addCompleteOutgoingRouteStep(ItTransactionalRequest request , Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> addCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests , Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse updateCompleteOutgoingRouteStep(ItTransactionalRequest request , Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> updateCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests , Long transactionId) throws InteraxTelephonyGeneralException;
	public ItTransactionalResponse removeCompleteOutgoingRouteStep(ItTransactionalRequest request , Long transactionId) throws InteraxTelephonyGeneralException;
	public List<ItTransactionalResponse> removeCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests , Long transactionId) throws InteraxTelephonyGeneralException;

	



}
