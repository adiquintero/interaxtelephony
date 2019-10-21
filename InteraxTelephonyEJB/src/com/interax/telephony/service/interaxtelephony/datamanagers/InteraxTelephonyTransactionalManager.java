package com.interax.telephony.service.interaxtelephony.datamanagers;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
//import java.util.Hashtable;
import java.util.List;
import java.util.Random;
//import java.util.Vector;
import com.interax.persistence.datamanagers.NullDataManager;
import com.interax.telephony.service.datamanagers.ItProviderManager;
import com.interax.telephony.service.datamanagers.ItProviderPeerManager;
import com.interax.telephony.service.datamanagers.ItOutgoingRouteManager;
import com.interax.telephony.service.datamanagers.ItOutgoingRouteStepManager;
import com.interax.telephony.service.datamanagers.RtExtensionManager;
import com.interax.telephony.service.datamanagers.RtIaxPeerManager;
import com.interax.telephony.service.datamanagers.RtSipPeerManager;
import com.interax.telephony.service.exception.it.InteraxTelephonyGeneralException;
import com.interax.telephony.service.exception.it.InteraxTelephonyInvalidTransactionException;
import com.interax.telephony.service.exception.it.InteraxTelephonyInvalidTransactionIdException;
import com.interax.telephony.service.exception.it.InteraxTelephonyProviderNotFoundException;
import com.interax.telephony.service.exception.it.InteraxTelephonyProviderPeerNotFoundException;
import com.interax.telephony.service.exception.it.InteraxTelephonyOutgoingRouteNotFoundException;
import com.interax.telephony.service.exception.it.InteraxTelephonyOutgoingRouteStepNotFoundException;
import com.interax.telephony.service.data.ItOutgoingRoute;
import com.interax.telephony.service.data.ItOutgoingRouteStep;
import com.interax.telephony.service.data.ItTransactionalRequest;
import com.interax.telephony.service.data.ItTransactionalResponse;
import com.interax.telephony.service.data.ItProvider;
import com.interax.telephony.service.data.ItProviderPeer;
import com.interax.telephony.service.data.RtExtension;
import com.interax.telephony.service.data.RtIaxPeer;
import com.interax.telephony.service.data.RtSipPeer;
import java.util.ArrayList;


public class InteraxTelephonyTransactionalManager extends NullDataManager{

	private String dataSourceName;

	private static final String IT_PROVIDER_PREFIX = "it-pv-";
	private static final String IT_PROVIDER_IAX_PEER_PREFIX = IT_PROVIDER_PREFIX + "iax-";
	private static final String IT_PROVIDER_SIP_PEER_PREFIX = IT_PROVIDER_PREFIX + "sip-";
	private static final String IT_PROVIDER_TYPE_PEER = "IT_PROVIDER";
	private static final String IT_PROVIDER_BASE_CONTEXT_PREFIX = "rt_it_outgoing_";
	private static final String IT_PROVIDER_DISABLED_CONTEXT_PREFIX = IT_PROVIDER_BASE_CONTEXT_PREFIX + "disabled_";
	private static final String IT_PROVIDER_INCOMING_CONTEXT = "it_incoming"; //FIXME

	private static final String CORRELATIVE_IDS_KEY = "correlativeIds";

	//private static final TimeZone baseTimeZone = TimeZone.getTimeZone("GMT+0");

	//FIXME VALOR CABLEADO
	private static final int PEER_SECRET_ALPHA_LENGTH = 6;
	private static final int PEER_SECRET_NUMERIC_LENGTH = 4;



	private static Long nextTransactionId;
	private static HashMap<Long, Connection> transactionalConnections;

	public InteraxTelephonyTransactionalManager(String dataSourceName) {
		super(dataSourceName);
		this.dataSourceName = dataSourceName;

		if(transactionalConnections == null){
			nextTransactionId = 1L;
			transactionalConnections = new HashMap<Long, Connection>();
		}
	}

	/*
	 *
	 *
	 *
	 *	BEGIN PUBLIC LAYER
	 *
	 *
	 * 
	 * 
	 */	

	/*
	 *
	 * BEGIN PUBLIC SIMPLE NON TRANSACCIONAL METHODS
	 * 
	 */	

	/* Atomic Simple Provider Management Methods */

	public ItTransactionalResponse loadProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.loadProvider(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.loadProvider(request, controlledConnection));
			}

		}catch(Exception e){
			throw this.manageException(e);
		}
		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse addProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.addProvider(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.addProvider(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse updateProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.updateProvider(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public  List<ItTransactionalResponse> updateProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.updateProvider(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse removeProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.removeProvider(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.removeProvider(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}



	/* Atomic Simple ProviderPeer Management Methods */

	public ItTransactionalResponse loadProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection nullConnection = null;
			return this.loadProviderPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.loadProviderPeer(request, controlledConnection));
			}
		}catch(Exception e){
			throw this.manageException(e);
		}
		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse addProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection nullConnection = null;
			return addProviderPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				responses.add(addProviderPeer(request, controlledConnection));
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse updateProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try {
			Connection nullConnection = null;
			return updateProviderPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				responses.add(updateProviderPeer(request, controlledConnection));
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse removeProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection nullConnection = null;
			return removeProviderPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;
		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(removeProviderPeer(request, controlledConnection));
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}
		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}



	/*
	 *
	 * END PUBLIC SIMPLE NON TRANSACCIONAL METHODS
	 * 
	 */	

	/*
	 *
	 * BEGIN PUBLIC COMPLETE NON TRANSACCIONAL METHODS
	 * 
	 */

	/* Atomic Complete ProviderPeer Management Methods */

	public ItTransactionalResponse loadCompleteProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.loadCompleteProviderPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadCompleteProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException {
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.loadCompleteProviderPeer(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse addCompleteProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.addCompleteProviderPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addCompleteProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException {
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.addCompleteProviderPeer(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse updateCompleteProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.updateCompleteProviderPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateCompleteProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException {
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.updateCompleteProviderPeer(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse removeCompleteProviderPeer(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.removeCompleteProviderPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeCompleteProviderPeers(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException {
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.removeCompleteProviderPeer(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	/* Atomic Complete Provider Management Methods */

	public ItTransactionalResponse loadCompleteProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.loadCompleteProvider(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadCompleteProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.loadCompleteProvider(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse addCompleteProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.addCompleteProvider(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addCompleteProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException {
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.addCompleteProvider(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse updateCompleteProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.updateCompleteProvider(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateCompleteProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.updateCompleteProvider(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse removeCompleteProvider(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.removeCompleteProvider(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeCompleteProviders(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.removeCompleteProvider(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}



	/*
	 *
	 * END PUBLIC COMPLETE NON TRANSACCIONAL METHODS
	 * 
	 */

	/*
	 *
	 * BEGIN PUBLIC SIMPLE TRANSACCIONAL METHODS
	 * 
	 */

	/* Transactional Simple Provider Management Methods */

	public ItTransactionalResponse loadProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.loadProvider(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				responses.add(this.loadProvider(request, getTransactionalConnection(transactionId)));
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse addProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.addProvider(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection =  this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = addProvider(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse updateProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.updateProvider(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = updateProvider(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse removeProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.removeProvider(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = removeProvider(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}



	
	/* Transactional Simple ProviderPeer Management Methods */

	public ItTransactionalResponse loadProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.loadProviderPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = loadProviderPeer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse addProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.addProviderPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = addProviderPeer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse updateProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.updateProviderPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = updateProviderPeer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse removeProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.removeProviderPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = removeProviderPeer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}




	/*
	 *
	 * END PUBLIC SIMPLE TRANSACCIONAL METHODS
	 * 
	 */

	/*
	 *
	 * BEGIN PUBLIC COMPLETE TRANSACCIONAL METHODS
	 * 
	 */

	/* Transactional Complete ItProvider Management Methods */ 

	public ItTransactionalResponse loadCompleteProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.loadCompleteProvider(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadCompleteProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = loadCompleteProvider(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse addCompleteProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.addCompleteProvider(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addCompleteProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = addCompleteProvider(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse updateCompleteProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.updateCompleteProvider(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateCompleteProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = updateCompleteProvider(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse removeCompleteProvider(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.removeCompleteProvider(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeCompleteProviders(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = removeCompleteProvider(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	/* Transactional Complete ItProviderPeer Management Methods */

	public ItTransactionalResponse loadCompleteProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.loadCompleteProviderPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadCompleteProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = loadCompleteProviderPeer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse addCompleteProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.addCompleteProviderPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addCompleteProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = addCompleteProviderPeer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse updateCompleteProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.updateCompleteProviderPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateCompleteProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = updateCompleteProviderPeer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse removeCompleteProviderPeer(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.removeCompleteProviderPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeCompleteProviderPeers(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = removeCompleteProviderPeer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}


	/*
	 *
	 * END PUBLIC COMPLETE TRANSACCIONAL METHODS
	 * 
	 */
	
	/*
	 *
	 *
	 *
	 *	END PUBLIC LAYER
	 *
	 *
	 * 
	 * 
	 */	

	/*
	 *
	 *
	 *
	 *	BEGIN PRIVATE LAYER
	 *
	 *
	 * 
	 * 
	 */	

	/*
	 *
	 * BEGIN PRIVATE SIMPLE METHODS
	 * 
	 */	

	/* Private Provider Management Atomic Methods */

	private ItTransactionalResponse loadProvider(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long itProviderId = null;
		itProviderId = (Long) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		ItProvider itProvider = this.loadProvider(itProviderId, transactionalConnection);
		response.setResponseData(itProvider);
		response.setResponseType(ItProvider.class);
		return response;
	}

	private ItTransactionalResponse addProvider(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItProvider itProvider = (ItProvider) request.getRequestData();
		Long itProviderId = this.addProvider(itProvider, transactionalConnection);
		itProvider.setId(itProviderId);
		ItTransactionalResponse response = new ItTransactionalResponse();
		response.setResponseData(itProvider);
		response.setResponseType(itProvider.getClass());

		return response;
	}

	private ItTransactionalResponse updateProvider(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItProvider itProvider = (ItProvider) request.getRequestData();
		this.updateProvider(itProvider, transactionalConnection);
		ItTransactionalResponse response = new ItTransactionalResponse();
		response.setResponseData(itProvider);
		response.setResponseType(itProvider.getClass());

		return response;
	}

	private ItTransactionalResponse removeProvider(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long itProviderId = (Long) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		Integer responseData = this.removeProvider(itProviderId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());

		return response;
	}


	/* Private ProviderPeer Management Atomic Methods */

	private ItTransactionalResponse loadProviderPeer(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long ItProviderPeerId = (Long) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		ItProviderPeer ItProviderPeer = this.loadProviderPeer(ItProviderPeerId, transactionalConnection);
		response.setResponseData(ItProviderPeer);
		response.setResponseType(ItProvider.class);

		return response;
	}

	private ItTransactionalResponse addProviderPeer(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItProviderPeer ItProviderPeer = (ItProviderPeer) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		Long ItProviderPeerId = this.addProviderPeer(ItProviderPeer, transactionalConnection);
		ItProviderPeer.setId(ItProviderPeerId);
		ItProviderPeer.setSecret(generateRandomProviderPeerSecret());
		response.setResponseData(ItProviderPeer);
		response.setResponseType(ItProviderPeer.getClass());

		return response;
	}

	private ItTransactionalResponse updateProviderPeer(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItProviderPeer ItProviderPeer = (ItProviderPeer) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		this.updateProviderPeer(ItProviderPeer, transactionalConnection);
		response.setResponseData(ItProviderPeer);
		response.setResponseType(ItProviderPeer.getClass());

		return response;
	}

	private ItTransactionalResponse removeProviderPeer(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long ItProviderPeerId = (Long) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		Integer responseData = this.removeProviderPeer(ItProviderPeerId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}


	/*
	 *
	 * END PRIVATE SIMPLE METHODS
	 * 
	 */	

	/*
	 *
	 * BEGIN PRIVATE COMPLETE METHODS
	 * 
	 */	

	/* Private Provider Management Complete Methods */

	private ItTransactionalResponse loadCompleteProvider(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		Long routeOutgoingId = (Long) request.getRequestData();
		ItProvider itProvider = this.loadCompleteProvider(routeOutgoingId, transactionalConnection);
		response.setResponseData(itProvider);
		response.setResponseType(itProvider.getClass());
		return response;
	}

	@SuppressWarnings("unchecked")
	private ItTransactionalResponse addCompleteProvider(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		ItProvider itProvider = (ItProvider) request.getRequestData();
		HashMap<String, Object> responseData = this.addCompleteProvider(itProvider, transactionalConnection);
		itProvider = (ItProvider)responseData.get(ItProvider.class.getSimpleName());
		response.setResponseData(itProvider);
		response.setResponseType(itProvider.getClass());
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
	}

	@SuppressWarnings("unchecked")
	private ItTransactionalResponse updateCompleteProvider(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		ItProvider itProvider = (ItProvider) request.getRequestData();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		HashMap<String, Object> responseData = this.updateCompleteProvider(itProvider, transactionalConnection);
		itProvider = (ItProvider)responseData.get(ItProvider.class.getSimpleName());
		response.setResponseData(itProvider);
		response.setResponseType(itProvider.getClass());
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
	}

	private ItTransactionalResponse removeCompleteProvider(ItTransactionalRequest request, Connection transactionalConnection) throws Exception {
		ItTransactionalResponse response = new ItTransactionalResponse();
		Long itProviderId = (Long) request.getRequestData();
		Integer responseData = this.removeCompleteProvider(itProviderId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}


	/* Private ProviderPeer Management Complete Methods */

	private ItTransactionalResponse loadCompleteProviderPeer(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{
		ItTransactionalResponse response = new ItTransactionalResponse();
		Long ItProviderPeerId = (Long) request.getRequestData();
		ItProviderPeer ItProviderPeer = this.loadCompleteProviderPeer(ItProviderPeerId, transactionalConnection);
		response.setResponseData(ItProviderPeer);
		response.setResponseType(ItProviderPeer.getClass());
		return response;
	}

	private ItTransactionalResponse addCompleteProviderPeer(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{
		ItProviderPeer ItProviderPeer = (ItProviderPeer) request.getRequestData();
		ItProvider itProvider = this.loadProvider(ItProviderPeer.getProviderId(), transactionalConnection, false);
		ItProviderPeer vtNewProviderPeer = this.addCompleteProviderPeer(ItProviderPeer, itProvider, transactionalConnection);
		ItTransactionalResponse response = new ItTransactionalResponse();		
		response.setResponseData(vtNewProviderPeer);
		response.setResponseType(ItProviderPeer.getClass());
		return response;
	}

	private ItTransactionalResponse updateCompleteProviderPeer(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{
		ItTransactionalResponse response = new ItTransactionalResponse();
		ItProviderPeer ItProviderPeer = (ItProviderPeer) request.getRequestData();
		ItProvider itProvider = this.loadProvider(ItProviderPeer.getProviderId(), transactionalConnection, false);

		ItProviderPeer newItProviderPeer = this.updateCompleteProviderPeer(ItProviderPeer, itProvider, transactionalConnection);
		response.setResponseData(newItProviderPeer);
		response.setResponseType(newItProviderPeer.getClass());
		return response;
	}

	private ItTransactionalResponse removeCompleteProviderPeer(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{
		ItTransactionalResponse response = new ItTransactionalResponse();
		Long peerId = (Long) request.getRequestData();
		Integer removeProviderPeerResponse = this.removeCompleteProviderPeer(peerId, transactionalConnection);
		response.setResponseData(removeProviderPeerResponse);
		response.setResponseType(removeProviderPeerResponse.getClass());
		return response;
	}




	/*
	 *
	 * END PRIVATE COMPLETE METHODS
	 * 
	 */	


	/*
	 *
	 *  BEGIN PRIVATE SIMPLE DATA LAYER METHODS (CRUD's)
	 * 
	 */	

    /*CRUD PROVIDER METHOD*/
	private ItProvider loadProvider(Long itProviderId, Connection controlledConnection) throws Exception{
		return loadProvider(itProviderId, controlledConnection, true);
	}

	private ItProvider loadProvider(Long itProviderId, Connection controlledConnection, boolean isCrud) throws Exception{
		ItProvider itProviderLoadObject = null;
		ItProviderManager itProviderManager;

		if(controlledConnection!=null)
			itProviderManager= new ItProviderManager(controlledConnection);
		else
			itProviderManager= new ItProviderManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		itProviderLoadObject = (ItProvider)itProviderManager.load(itProviderId);

		if (itProviderLoadObject==null){
			throw new InteraxTelephonyProviderNotFoundException("Provider " + itProviderId + " does not exists");
		}
	
		
		return itProviderLoadObject;

	}

	private long addProvider(ItProvider itProvider, Connection controlledConnection) throws Exception{
		return addProvider(itProvider, controlledConnection, true);
	}

	private long addProvider(ItProvider itProvider, Connection controlledConnection, boolean isCrud) throws Exception{

		ItProviderManager itProviderManager;

		if(controlledConnection!=null)
			itProviderManager= new ItProviderManager(controlledConnection);
		else
			itProviderManager= new ItProviderManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		long itProviderId = itProviderManager.insertAutoIncrement(itProvider);
		
		//  addStep
		//FIXME PARA MODIFICAR EL RT_EXTENSION Y AGREGAR LOS STEP EJECUTAR  this.addDidStep
		//this.addDidStep("_X.",1,IT_PROVIDER_BASE_CONTEXT_PREFIX + itProviderId, controlledConnection);
		
		return itProviderId;
	}

	private int updateProvider(ItProvider itProvider, Connection controlledConnection) throws Exception {
		return updateProvider(itProvider, controlledConnection, true);
	}

	private int updateProvider(ItProvider itProvider, Connection controlledConnection, boolean isCrud) throws Exception {

		ItProviderManager itProviderManager;

		if(controlledConnection!=null)
			itProviderManager= new ItProviderManager(controlledConnection);
		else
			itProviderManager= new ItProviderManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int itProviderUpdateRowsAffected = itProviderManager.update(itProvider, itProvider.getId());
	

		if (itProviderUpdateRowsAffected == 0){
			throw new InteraxTelephonyProviderNotFoundException("Provider " + itProvider.getId() + " does not exists");
		}
		return itProviderUpdateRowsAffected;
	}

	private int removeProvider(Long itProviderId, Connection controlledConnection) throws Exception{
		return removeProvider(itProviderId, controlledConnection, true);
	}

	private int removeProvider(Long itProviderId, Connection controlledConnection, boolean isCrud) throws Exception{

		ItProviderManager itProviderManager;

		if(controlledConnection!=null)
			itProviderManager = new ItProviderManager(controlledConnection);
		else
			itProviderManager= new ItProviderManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}
		

		int itProviderDeleteRowsAffected = itProviderManager.delete(itProviderId);
		
		if(itProviderDeleteRowsAffected == 0){
			throw new InteraxTelephonyProviderNotFoundException("Provider " + itProviderId + " does not exists");
		}

		return itProviderDeleteRowsAffected;
	}

	/* CRUD ProviderPeer Methods */

	private ItProviderPeer loadProviderPeer(Long ItProviderPeerId, Connection controlledConnection) throws Exception{
		return loadProviderPeer(ItProviderPeerId, controlledConnection, true);
	}

	private ItProviderPeer loadProviderPeer(Long ItProviderPeerId, Connection controlledConnection, boolean isCrud) throws Exception{

		ItProviderPeerManager ItProviderPeerManager;

		if(controlledConnection!=null)
			ItProviderPeerManager= new ItProviderPeerManager(controlledConnection);
		else
			ItProviderPeerManager= new ItProviderPeerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		ItProviderPeer ItProviderPeerLoadObject = (ItProviderPeer)ItProviderPeerManager.load(ItProviderPeerId);
		if(ItProviderPeerLoadObject==null){
			throw new InteraxTelephonyProviderNotFoundException("ProviderPeer " + ItProviderPeerId + " does not exists");
		}
		return ItProviderPeerLoadObject;
	}

	private long addProviderPeer(ItProviderPeer ItProviderPeer, Connection controlledConnection) throws Exception {
		return addProviderPeer(ItProviderPeer, controlledConnection, true);
	}

	private long addProviderPeer(ItProviderPeer ItProviderPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		ItProviderPeerManager ItProviderPeerManager;

		if(controlledConnection!=null)
			ItProviderPeerManager= new ItProviderPeerManager(controlledConnection);
		else
			ItProviderPeerManager= new ItProviderPeerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		long ItProviderPeerId = ItProviderPeerManager.insertAutoIncrement(ItProviderPeer);
		return ItProviderPeerId;

	}

	private int updateProviderPeer(ItProviderPeer ItProviderPeer, Connection controlledConnection) throws Exception {
		return updateProviderPeer(ItProviderPeer, controlledConnection, true);
	}

	private int updateProviderPeer(ItProviderPeer ItProviderPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		ItProviderPeerManager ItProviderPeerManager;

		if(controlledConnection!=null)
			ItProviderPeerManager= new ItProviderPeerManager(controlledConnection);
		else
			ItProviderPeerManager= new ItProviderPeerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int ItProviderPeerUpdateRowsAffected = ItProviderPeerManager.update(ItProviderPeer, ItProviderPeer.getId());

		if (ItProviderPeerUpdateRowsAffected == 0){
			throw new InteraxTelephonyProviderNotFoundException("ProviderPeer " + ItProviderPeer.getId() + " does not exists");
		}

		return ItProviderPeerUpdateRowsAffected;

	}

	private int removeProviderPeer(Long ItProviderPeerId, Connection controlledConnection) throws Exception{
		return removeProviderPeer(ItProviderPeerId, controlledConnection, true);
	}

	private int removeProviderPeer(Long ItProviderPeerId, Connection controlledConnection, boolean isCrud) throws Exception{

		ItProviderPeerManager itProviderPeerManager;

		if(controlledConnection!=null)
			itProviderPeerManager= new ItProviderPeerManager(controlledConnection);
		else
			itProviderPeerManager= new ItProviderPeerManager(this.dataSourceName);

		
		ItProviderPeer itProviderPeer = this.loadCompleteProviderPeer(ItProviderPeerId, controlledConnection);
		
		int ItProviderPeerDeleteRowsAffected = itProviderPeerManager.delete(ItProviderPeerId);
		
		if(itProviderPeer.getHasIax()){
			this.removeIaxPeer(ItProviderPeerId, controlledConnection, false);
		}

		if(itProviderPeer.getHasSip()){
			this.removeSipPeer(ItProviderPeerId, controlledConnection, false);
		}
		

		if(!isCrud){
			// Additional non CRUD code
		}

		if(ItProviderPeerDeleteRowsAffected==0){
			throw new InteraxTelephonyProviderNotFoundException("ProviderPeer " + ItProviderPeerId + " does not exists");
		}

		return ItProviderPeerDeleteRowsAffected;
	}

	
	/* CRUD IAXProviderPeer y SIPProviderPeer Methods */

	private long addIaxPeer(ItProvider itProvider, ItProviderPeer itProviderPeer, Connection controlledConnection) throws Exception {
		return addIaxPeer(itProvider, itProviderPeer, controlledConnection, true);
	}

	private long addIaxPeer(ItProvider itProvider, ItProviderPeer itProviderPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		RtIaxPeerManager rtIaxPeerManager = new RtIaxPeerManager(controlledConnection);
		RtIaxPeer rtIaxPeer = createIaxPeer(itProvider, itProviderPeer);

		if(!isCrud){
			// Additional non CRUD code
		}

		long newIaxPeerId = rtIaxPeerManager.insertAutoIncrement(rtIaxPeer);
		rtIaxPeer.setId(newIaxPeerId);
		return newIaxPeerId;
	}

	private int updateIaxPeer(ItProvider itProvider, ItProviderPeer itProviderPeer, Connection controlledConnection) throws Exception {
		return updateIaxPeer(itProvider, itProviderPeer, controlledConnection, true);
	}

	private int updateIaxPeer(ItProvider itProvider, ItProviderPeer itProviderPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		RtIaxPeerManager rtIaxPeerManager = new RtIaxPeerManager(controlledConnection);
		if(!isCrud){
			//		return rtSipPeerManager.update(createSipPeer(itProvider, ItProviderPeer), "itProviderPeerId=" + ItProviderPeer.getId() + " AND itProviderPeerType='" + ItProviderPeerType.VT_PEER.name() + "'", false);
					return  rtIaxPeerManager.update(createIaxPeer(itProvider, itProviderPeer), "itPeerId=" + itProviderPeer.getId(), false);
				}
				else{
			//		return rtSipPeerManager.update(createSipPeer(itProvider, ItProviderPeer), "itProviderPeerId=" + ItProviderPeer.getId() + " AND itProviderPeerType='" + ItProviderPeerType.VT_PEER.name() + "'");
					 return rtIaxPeerManager.update(createIaxPeer(itProvider, itProviderPeer), "itPeerId=" + itProviderPeer.getId() +  " AND itPeerType='" + IT_PROVIDER_TYPE_PEER + "'");
				}
	}

	private int removeIaxPeer(long itProviderPeerId, Connection controlledConnection) throws Exception {
		return removeIaxPeer(itProviderPeerId, controlledConnection, true);
	}

	private int removeIaxPeer(long itProviderPeerId, Connection controlledConnection , boolean isCrud) throws Exception {

		RtIaxPeerManager rtIaxPeerManager = new RtIaxPeerManager(controlledConnection);
		if(!isCrud){
			// Additional non CRUD code
		}
		//return rtIaxPeerManager.delete("itProviderPeerId=" + ItProviderPeerId + " AND itProviderPeerType='" + ItProviderPeerType.VT_PEER.name() + "'");
		String data = "itPeerId=" + itProviderPeerId + "AND itPeerType='" + IT_PROVIDER_TYPE_PEER + "'";
	
		return rtIaxPeerManager.delete("itPeerId=" + itProviderPeerId + " AND itPeerType='" + IT_PROVIDER_TYPE_PEER + "'");

	}

	private RtIaxPeer createIaxPeer(ItProvider itProvider, ItProviderPeer ItProviderPeer) throws Exception {
		return createIaxPeer(itProvider, ItProviderPeer, true);
	}

	private RtIaxPeer createIaxPeer(ItProvider itProvider, ItProviderPeer ItProviderPeer, boolean isCrud) throws Exception {

		RtIaxPeer rtIaxPeer = new RtIaxPeer();
    	rtIaxPeer.setAccountcode(itProvider.getName());
		rtIaxPeer.setAllow("g729");
		if(ItProviderPeer.getEnabled()){
			rtIaxPeer.setContext(IT_PROVIDER_BASE_CONTEXT_PREFIX + itProvider.getId()); 
		}else{
			rtIaxPeer.setContext(IT_PROVIDER_DISABLED_CONTEXT_PREFIX  + itProvider.getId()); 
		}

		rtIaxPeer.setDisallow("all");
		rtIaxPeer.setItPeerId(ItProviderPeer.getId());
    	rtIaxPeer.setItPeerType(IT_PROVIDER_TYPE_PEER);
//FIXME	rtIaxPeer.setLanguage(ItProviderPeer.getLanguage());
		rtIaxPeer.setName(IT_PROVIDER_IAX_PEER_PREFIX + ItProviderPeer.getId()); 
		rtIaxPeer.setQualify("no");
		rtIaxPeer.setSecret(ItProviderPeer.getSecret());
		rtIaxPeer.setType("peer");
		rtIaxPeer.setUsername(IT_PROVIDER_IAX_PEER_PREFIX + ItProviderPeer.getId()); 
    	rtIaxPeer.setUserfield("" + ItProviderPeer.getId()); 
		//TODO ServiceType

		// default fields
		rtIaxPeer.setAmaflags(null);
		rtIaxPeer.setAuth(null);
		rtIaxPeer.setDbsecret(null);
		rtIaxPeer.setDefaultip(null);
		rtIaxPeer.setDeny(null);
		rtIaxPeer.setHost(ItProviderPeer.getHost());
		rtIaxPeer.setInkeys(null);
		rtIaxPeer.setIpaddr(null);
		rtIaxPeer.setMailbox(null);
		rtIaxPeer.setMd5secret(null);
		rtIaxPeer.setNotransfer(null);
		rtIaxPeer.setOutkey(null);
		rtIaxPeer.setPermit(null);
		rtIaxPeer.setPort(0);
		rtIaxPeer.setRegseconds(0);

		if(!isCrud){
			// Additional non CRUD code
		}

		return rtIaxPeer;
	}

	private long addSipPeer(ItProvider itProvider, ItProviderPeer ItProviderPeer, Connection controlledConnection) throws Exception {
		return addSipPeer(itProvider, ItProviderPeer, controlledConnection, true);
	}

	private long addSipPeer(ItProvider itProvider, ItProviderPeer ItProviderPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		RtSipPeerManager rtSipPeerManager = new RtSipPeerManager(controlledConnection);
		RtSipPeer rtSipPeer = createSipPeer(itProvider, ItProviderPeer);

		if(!isCrud){
			// Additional non CRUD code
		}

		long newSipPeerId = rtSipPeerManager.insertAutoIncrement(rtSipPeer);
		rtSipPeer.setId(newSipPeerId);
		return newSipPeerId;
	}

	private long updateSipPeer(ItProvider itProvider, ItProviderPeer ItProviderPeer, Connection controlledConnection) throws Exception {
		return updateSipPeer(itProvider, ItProviderPeer, controlledConnection, true);
	}

	private long updateSipPeer(ItProvider itProvider, ItProviderPeer itProviderPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		RtSipPeerManager rtSipPeerManager = new RtSipPeerManager(controlledConnection);

		if(!isCrud){
	//		return rtSipPeerManager.update(createSipPeer(itProvider, ItProviderPeer), "itProviderPeerId=" + ItProviderPeer.getId() + " AND itProviderPeerType='" + ItProviderPeerType.VT_PEER.name() + "'", false);
			return rtSipPeerManager.update(createSipPeer(itProvider, itProviderPeer), "itPeerId=" + itProviderPeer.getId(), false);
		}
		else{
	//		return rtSipPeerManager.update(createSipPeer(itProvider, ItProviderPeer), "itProviderPeerId=" + ItProviderPeer.getId() + " AND itProviderPeerType='" + ItProviderPeerType.VT_PEER.name() + "'");
			return rtSipPeerManager.update(createSipPeer(itProvider, itProviderPeer), "itPeerId=" + itProviderPeer.getId());
		}

	}

	private int removeSipPeer(long itProviderPeerId, Connection controlledConnection) throws Exception {
		return removeSipPeer(itProviderPeerId, controlledConnection, true);
	}

	private int removeSipPeer(long itProviderPeerId, Connection controlledConnection, boolean isCrud) throws Exception {

		RtSipPeerManager rtSipPeerManager = new RtSipPeerManager(controlledConnection);
		if(!isCrud){
			// Additional non CRUD code
		}
		return rtSipPeerManager.delete("itPeerId=" + itProviderPeerId + " AND itPeerType='" + IT_PROVIDER_TYPE_PEER + "'");
//FIXME return rtSipPeerManager.delete("itProviderPeerId=" + ItProviderPeerId + " AND itProviderPeerType='" + ItProviderPeerType.VT_PEER.name() + "'");

	}

	private RtSipPeer createSipPeer(ItProvider itProvider, ItProviderPeer itProviderPeer){
		return createSipPeer(itProvider, itProviderPeer, true);
	}

	private RtSipPeer createSipPeer(ItProvider itProvider, ItProviderPeer itProviderPeer, boolean isCrud){
		RtSipPeer rtSipPeer = new RtSipPeer();

		rtSipPeer.setAccountcode(itProvider.getName());
		rtSipPeer.setAllow("g729");
        rtSipPeer.setCallerid(itProviderPeer.getId() + "<" + itProvider.getName() + ">");
		rtSipPeer.setCallLimit(0);
		if(itProviderPeer.getEnabled()){
			rtSipPeer.setContext(IT_PROVIDER_BASE_CONTEXT_PREFIX + itProvider.getId()); 
		}else{
			rtSipPeer.setContext(IT_PROVIDER_DISABLED_CONTEXT_PREFIX + itProvider.getId()); 
		}
		rtSipPeer.setDisallow("all");
		rtSipPeer.setHost(itProviderPeer.getHost());
		rtSipPeer.setItPeerId(itProviderPeer.getId());
    	rtSipPeer.setItPeerType(IT_PROVIDER_TYPE_PEER);
//FIXME	rtSipPeer.setLanguage(ItProviderPeer.getLanguage());
		rtSipPeer.setName(IT_PROVIDER_SIP_PEER_PREFIX + itProviderPeer.getId());
		rtSipPeer.setPort(5060);
		rtSipPeer.setQualify("no");
		rtSipPeer.setRegseconds(0);
		rtSipPeer.setSecret(itProviderPeer.getSecret());
		rtSipPeer.setType("peer");
		rtSipPeer.setUsername(IT_PROVIDER_SIP_PEER_PREFIX + itProviderPeer.getId());
    	rtSipPeer.setUserfield("" + itProviderPeer.getId()); 

		// default fields
		rtSipPeer.setDtmfmode("rfc2833");
		rtSipPeer.setInsecure("very");
		rtSipPeer.setNat("no");

		rtSipPeer.setAmaflags(null);
		rtSipPeer.setCallgroup(null);
		rtSipPeer.setCancallforward(null);
		rtSipPeer.setCanreinvite("no");
		rtSipPeer.setDeny(null);
		rtSipPeer.setDefaultip(null);
		rtSipPeer.setDefaultuser(null);
		rtSipPeer.setFromuser(null);
		rtSipPeer.setFromdomain(null);
		rtSipPeer.setFullcontact(null);
		rtSipPeer.setMailbox(null);
		rtSipPeer.setMask(null);
		rtSipPeer.setMd5secret(null);
		rtSipPeer.setMusiconhold(null);
		rtSipPeer.setPermit(null);
		rtSipPeer.setPickupgroup(null);
		rtSipPeer.setRegexten(null);
		rtSipPeer.setRegserver(null);
		rtSipPeer.setRestrictcid(null);
		rtSipPeer.setRtptimeout(null);
		rtSipPeer.setRtpholdtimeout(null);
		rtSipPeer.setSetvar(null);
		rtSipPeer.setIpaddr(null);
		rtSipPeer.setSubscribecontext(null);

		if(!isCrud){
			// Additional non CRUD code
		}

		return rtSipPeer;
	}

	/*
	 *
	 *  END PRIVATE SIMPLE DATA LAYER METHODS (CRUD's)
	 * 
	 */	

	/*
	 *
	 * BEGIN PRIVATE COMPLETE DATA LAYER METHODS (CRUD Complete)
	 * 
	 */	




	/* Complete Provider Methods */

	private ItProvider loadCompleteProvider(Long routeOutgoingId, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		Connection controlledConnection = null;
		boolean transactionalCall = (transactionalConnection != null);

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			// Load the Provider

			ItProvider itProvider = loadProvider(routeOutgoingId, controlledConnection, false);

			List<ItProviderPeer> ItProviderPeers = loadCompleteProviderPeers(routeOutgoingId, controlledConnection);

		

			itProvider.setProviderPeers(ItProviderPeers);
			if(!transactionalCall) 
				controlledConnection.commit();  
			
		
			return itProvider;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}

	}


	private HashMap<String, Object> addCompleteProvider(ItProvider itProvider, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			
			// Add the Provider
			Long newProviderId = this.addProvider(itProvider, controlledConnection, false);
			itProvider.setId(newProviderId);
			

			ItProviderManager itProviderManager = new ItProviderManager(controlledConnection);
			//OJO FIXME INDAGAR QUE HACE EL KEY PAIRS
//			HashMap<String, Object> keyPairs = new HashMap<String, Object>();
//			
//			itProviderManager.update(keyPairs, newProviderId);

			// Add the peers
			List<ItProviderPeer> ItProviderPeers = itProvider.getProviderPeers();
			HashMap<Long, Long> ItProviderPeersIdsTable = new HashMap<Long,Long>();
			correlativeIds.put(ItProviderPeer.class.getSimpleName(), ItProviderPeersIdsTable);

			for (ItProviderPeer ItProviderPeer : ItProviderPeers) {
				long oldItProviderPeerId = ItProviderPeer.getId();
				ItProviderPeer.setProviderId(newProviderId);
				ItProviderPeer ItProviderPeerNew = this.addCompleteProviderPeer(ItProviderPeer, itProvider, controlledConnection);
				long newItProviderPeerId = ItProviderPeerNew.getId();
				ItProviderPeersIdsTable.put(oldItProviderPeerId, newItProviderPeerId);
			}
			
			
			
			response.put(itProvider.getClass().getSimpleName(), itProvider);
			response.put(CORRELATIVE_IDS_KEY, correlativeIds);

			if(!transactionalCall)
				controlledConnection.commit();

			return response;

		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}

	private HashMap<String, Object> updateCompleteProvider(ItProvider itProvider, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			// Update Provider
			this.updateProvider(itProvider, controlledConnection, false);
			
			// CorrelativeIds
			HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
			
			// Update the peers
			HashMap<Long, Long> peerIdsTable = new HashMap<Long,Long>();
			correlativeIds.put(ItProviderPeer.class.getSimpleName(), peerIdsTable);

			ItProvider itProviderOld = this.loadCompleteProvider(itProvider.getId(), controlledConnection); 
			
			// add step rt_extension
			
			
			
			
			ItProvider itProviderNew = itProvider;			
			List<Long> itProviderOldIds = new ArrayList<Long>();

			for (ItProviderPeer ItProviderPeerOld : itProviderOld.getProviderPeers()) {
				itProviderOldIds.add(ItProviderPeerOld.getId());
			}

			for (ItProviderPeer ItProviderPeerNew : itProviderNew.getProviderPeers()) {
				if(itProviderOldIds.contains(ItProviderPeerNew.getId())){
					this.updateCompleteProviderPeer(ItProviderPeerNew, itProviderNew, controlledConnection);
					itProviderOldIds.remove(ItProviderPeerNew.getId()); //REMOVE ID
				}
				else{
					if(ItProviderPeerNew.getId()>0){
						throw new InteraxTelephonyProviderPeerNotFoundException("ProviderPeer " + ItProviderPeerNew.getId() + " not found in the DataBase");
					}
					this.addCompleteProviderPeer(ItProviderPeerNew, itProviderNew, controlledConnection); 
				}
			}

			for (Long ItProviderPeerId : itProviderOldIds) {
				this.removeCompleteProviderPeer(ItProviderPeerId, controlledConnection);
			}

			


			response.put(ItProvider.class.getSimpleName(), itProvider);
			response.put(CORRELATIVE_IDS_KEY, correlativeIds);

			if(!transactionalCall)
				controlledConnection.commit();

			return response;
		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}

	private int removeCompleteProvider(Long itProviderId, Connection transactionalConnection) throws InteraxTelephonyGeneralException {

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			ItProviderManager itProviderManager =  new ItProviderManager(controlledConnection);

			ItProvider itProvider = (ItProvider) this.loadCompleteProvider(itProviderId, controlledConnection);

			if(itProvider==null){
				throw new InteraxTelephonyProviderNotFoundException("Provider " + itProviderId + " not found");
			}
			
			//DELETE Provider
			HashMap<String, Object> keyPairs = new HashMap<String, Object>();
			keyPairs.put("deleted", true);
			int routeOutgoingRows = itProviderManager.update(keyPairs, itProviderId);
			if(routeOutgoingRows > 0){
				this.deleteDidStep(IT_PROVIDER_BASE_CONTEXT_PREFIX + itProviderId, controlledConnection);
			}

			//DELETE ProviderPeers
			List<ItProviderPeer> ItProviderPeers = itProvider.getProviderPeers();

			for (ItProviderPeer ItProviderPeer : ItProviderPeers) {
				this.removeCompleteProviderPeer(ItProviderPeer.getId(), controlledConnection);
			}


			if(!transactionalCall) controlledConnection.commit();

			return routeOutgoingRows;

		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}




	/* Complete ProviderPeer Methods */

	private ItProviderPeer loadCompleteProviderPeer(Long ItProviderPeerId, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			ItProviderPeer ItProviderPeer = loadProviderPeer(ItProviderPeerId, controlledConnection, false);
			
			if(!transactionalCall) 
				controlledConnection.commit();  
			return ItProviderPeer;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}

	private List<ItProviderPeer> loadCompleteProviderPeers(Long providerId, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			List<ItProviderPeer> peers = new ArrayList<ItProviderPeer>();

			ItProviderPeerManager itProviderPeerManager = new ItProviderPeerManager(controlledConnection);;
			List<Object> peersObjectIds = itProviderPeerManager.listField("id", "providerId=" + providerId);

			for (Object object : peersObjectIds) {
				BigInteger bigInteger = (BigInteger) object;
				long peerId = bigInteger.longValue();
				ItProviderPeer peer = loadProviderPeer(peerId, controlledConnection, false);
				peers.add(peer);
			}

			return peers;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}


	private ItProviderPeer addCompleteProviderPeer(ItProviderPeer itProviderPeer, ItProvider itProvider, Connection transactionalConnection) throws InteraxTelephonyGeneralException {
		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			itProviderPeer.setSecret(generateRandomProviderPeerSecret());
			long newProviderPeerId = addProviderPeer(itProviderPeer, controlledConnection, false);
			itProviderPeer.setId(newProviderPeerId);

			if(itProviderPeer.getHasIax()){
				this.addIaxPeer(itProvider, itProviderPeer, controlledConnection, false);
			}

			if(itProviderPeer.getHasSip()){
				this.addSipPeer(itProvider, itProviderPeer, controlledConnection, false);
			}

			if(!transactionalCall) controlledConnection.commit();

			return itProviderPeer;
		} 
		catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}

	private ItProviderPeer updateCompleteProviderPeer(ItProviderPeer itProviderPeer, ItProvider itProvider, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			ItProviderPeer oldItProviderPeer = this.loadProviderPeer(itProviderPeer.getId(), controlledConnection, false); 

			if (oldItProviderPeer == null)
				throw new InteraxTelephonyProviderPeerNotFoundException("ProviderPeer " +itProviderPeer.getId()+ "does not exist" );

			//itProviderPeer.setSecret(oldItProviderPeer.getSecret());			

			this.updateProviderPeer(itProviderPeer, controlledConnection, false);

			if(oldItProviderPeer.getHasIax() == itProviderPeer.getHasIax()){
				this.updateIaxPeer(itProvider, itProviderPeer, controlledConnection, false);
			}else if(itProviderPeer.getHasIax()){
				this.addIaxPeer(itProvider, itProviderPeer, controlledConnection, false);
			}else{
				this.removeIaxPeer(oldItProviderPeer.getId(), controlledConnection, false);
			}

			if(oldItProviderPeer.getHasSip() == itProviderPeer.getHasSip()){
				this.updateSipPeer(itProvider, itProviderPeer, controlledConnection, false);
			}else if(itProviderPeer.getHasSip()){
				this.addSipPeer(itProvider, itProviderPeer, controlledConnection, false);
			}else{
				this.removeSipPeer(oldItProviderPeer.getId(), controlledConnection, false);
			}

			if(!transactionalCall)
				controlledConnection.commit();

			return itProviderPeer;
		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}


	private int removeCompleteProviderPeer(Long peerId, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			ItProviderPeerManager ItProviderPeerManager =  new ItProviderPeerManager(controlledConnection);

			ItProviderPeer ItProviderPeer = (ItProviderPeer) ItProviderPeerManager.load(peerId);
			if(ItProviderPeer==null){
				throw new InteraxTelephonyProviderPeerNotFoundException("ProviderPeer " + peerId + " not found");
			}

			HashMap<String, Object> keyPairs = new HashMap<String, Object>();
			keyPairs.put("deleted", true);
			int peerRows = ItProviderPeerManager.update(keyPairs, peerId);

			if(ItProviderPeer.getHasIax()){
				this.removeIaxPeer(peerId, controlledConnection, false);
			}

			if(ItProviderPeer.getHasSip()){
				this.removeSipPeer(peerId, controlledConnection, false);
			}

			if(!transactionalCall) controlledConnection.commit();

			return peerRows;
		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}			
	}




	

	/*
	 *
	 * END PRIVATE COMPLETE DATA LAYER METHODS
	 * 
	 */	


	/*
	 *
	 *
	 *
	 *	END PRIVATE LAYER
	 *
	 *
	 * 
	 * 
	 */	


	/* Transaction Management Methods */

	public synchronized Long beginTransaction() throws Exception {
		Long transactionId = nextTransactionId++;

		Connection transactionalConnection = this.dataSource.getConnection();
		transactionalConnection.setAutoCommit(false);
		transactionalConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

		transactionalConnections.put(transactionId, transactionalConnection);

		return transactionId;
	}

	public void commitTransaction(Long transactionId) throws Exception {
		Connection transactionalConnection = this.getTransactionalConnection(transactionId); 
		transactionalConnection.commit();
		transactionalConnection.close();
		transactionalConnections.remove(transactionId); 
	}

	public void rollbackTransaction(Long transactionId) throws Exception {
		Connection transactionalConnection = this.getTransactionalConnection(transactionId); 
		transactionalConnection.rollback();
		transactionalConnection.close();
		transactionalConnections.remove(transactionId);
	}


	/*Private Transaction Cache Methods */

	private Connection getTransactionalConnection(long transactionId) throws Exception{

		if(transactionalConnections.containsKey(transactionId)){
			Connection transactionalConnection = transactionalConnections.get(transactionId);
			if(transactionalConnection!=null){
				return transactionalConnection;
			}
			else{
				throw new InteraxTelephonyInvalidTransactionException("Transaction " + transactionId + " is invalid");
			}
		}
		else{
			throw new InteraxTelephonyInvalidTransactionIdException("Transaction " + transactionId + " does not exists");
		}
	} 

	/* Private conecction/exception helpers */

	private Connection createControlledConnection() throws SQLException {
		Connection controlledConnection = this.dataSource.getConnection();
		controlledConnection.setAutoCommit(false);
		controlledConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		return controlledConnection;
	}

	private InteraxTelephonyGeneralException manageException(Exception e){
		if (e instanceof InteraxTelephonyGeneralException) {
			return (InteraxTelephonyGeneralException) e;
		}
		else{
			InteraxTelephonyGeneralException itException = new InteraxTelephonyGeneralException(e);
			return itException;
		}
	}

	private InteraxTelephonyGeneralException manageExceptionAndConnection(Exception e, Connection controlledConnection){
		try{
			if(controlledConnection!=null) controlledConnection.rollback();
		}
		catch(SQLException sqlException){
			return new InteraxTelephonyGeneralException(sqlException.getMessage());
		}
		return this.manageException(e);
	}

	private void closeConecction(Connection controlledConnection) throws InteraxTelephonyGeneralException{
		try{
			if(controlledConnection!=null) controlledConnection.close();
		}catch(SQLException sqlException){
			throw new InteraxTelephonyGeneralException(sqlException.getMessage());
		}
	}


	/*Private Random Methods */

	private static String generateRandomProviderPeerSecret(){
		String randomNumber = generateRandomNumber(PEER_SECRET_NUMERIC_LENGTH);
		String randomAlpha = generateRandomAlpha(PEER_SECRET_ALPHA_LENGTH);
		return randomAlpha + randomNumber;
	}

	private static String generateRandomNumber(int length){
		Random random = new Random();
		Integer randomNumber;
		int lowerLimit = (int) Math.pow(10L, length-1);
		int upperLimit = (int) Math.pow(10L, length);
		do{
			Double randomDouble = random.nextDouble();
			randomNumber = (int) (randomDouble * upperLimit);
		}
		while(randomNumber<lowerLimit || randomNumber>=upperLimit);
		return randomNumber.toString();
	}

	private static String generateRandomAlpha(int length){
		Random random = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		StringBuffer randomAlpha = new StringBuffer();
		while(randomAlpha.length()<length){
			int position = random.nextInt(characters.length());
			randomAlpha.append(characters.charAt(position));
		}

		return randomAlpha.toString();
	}
	
	
	private long addDidStep(String didNumber,int stepNumber,String context, Connection controlledConnection) throws Exception {

			RtExtensionManager rtExtensionManager;
			if(controlledConnection!=null)
				rtExtensionManager= new RtExtensionManager(controlledConnection);
			else
				rtExtensionManager= new RtExtensionManager(this.dataSourceName);

			RtExtension rtExtension = new RtExtension();

			rtExtension.setContext(context);
			rtExtension.setExten(didNumber);
			rtExtension.setPriority(stepNumber);
			rtExtension.setApp("Macro");
			//Goto(it_pre_incoming|${EXTEN:4}|1)
			rtExtension.setAppdata("IT_PREPROCESS_PROVIDER_INCOMIG|${EXTEN}");
			return rtExtensionManager.insertAutoIncrement(rtExtension);
			
	}	
	
	private int deleteDidStep(String context, Connection controlledConnection){
			RtExtensionManager rtExtensionManager;
			try {
			
			if(controlledConnection!=null)
				rtExtensionManager= new RtExtensionManager(controlledConnection);
			else
				rtExtensionManager= new RtExtensionManager(this.dataSourceName);			
				return rtExtensionManager.delete("context ='" + context +  "'");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
	}
	
	
//NEW 
	
	
	/* BEGIN SIMPLE  ATOMIC  METHOD NO TRANSACTIONAL */
	
	/* Atomic Simple OutgoingRoute Management Methods */

	public ItTransactionalResponse loadOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.loadOutgoingRoute(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.loadOutgoingRoute(request, controlledConnection));
			}

		}catch(Exception e){
			throw this.manageException(e);
		}
		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse addOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.addOutgoingRoute(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.addOutgoingRoute(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse updateOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.updateOutgoingRoute(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public  List<ItTransactionalResponse> updateOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.updateOutgoingRoute(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse removeOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.removeOutgoingRoute(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.removeOutgoingRoute(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}
	
	/* Atomic Simple OutgoingRouteStep Management Methods */

	public ItTransactionalResponse loadOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.loadOutgoingRouteStep(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.loadOutgoingRouteStep(request, controlledConnection));
			}

		}catch(Exception e){
			throw this.manageException(e);
		}
		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse addOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.addOutgoingRouteStep(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.addOutgoingRouteStep(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse updateOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.updateOutgoingRouteStep(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public  List<ItTransactionalResponse> updateOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.updateOutgoingRouteStep(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse removeOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException{
		try{
			Connection controlledConnection = null;
			return this.removeOutgoingRouteStep(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (ItTransactionalRequest request : requests) {
				responses.add(this.removeOutgoingRouteStep(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}
	
	
	/* Atomic Complete OutgoingRoute Management Methods */

	public ItTransactionalResponse loadCompleteOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.loadCompleteOutgoingRoute(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadCompleteOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.loadCompleteOutgoingRoute(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse addCompleteOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.addCompleteOutgoingRoute(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addCompleteOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException {
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.addCompleteOutgoingRoute(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse updateCompleteOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.updateCompleteOutgoingRoute(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateCompleteOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.updateCompleteOutgoingRoute(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse removeCompleteOutgoingRoute(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.removeCompleteOutgoingRoute(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeCompleteOutgoingRoutes(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.removeCompleteOutgoingRoute(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	
	
	/* Atomic Complete OutgoingRouteStep Management Methods */

	public ItTransactionalResponse loadCompleteOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.loadCompleteOutgoingRouteStep(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.loadCompleteOutgoingRouteStep(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse addCompleteOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.addCompleteOutgoingRouteStep(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException {
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.addCompleteOutgoingRouteStep(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse updateCompleteOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.updateCompleteOutgoingRouteStep(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}



	public List<ItTransactionalResponse> updateCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.updateCompleteOutgoingRouteStep(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public ItTransactionalResponse removeCompleteOutgoingRouteStep(ItTransactionalRequest request) throws InteraxTelephonyGeneralException {
		try {
			Connection nullConnection = null;
			return this.removeCompleteOutgoingRouteStep(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests) throws InteraxTelephonyGeneralException{
		List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = this.removeCompleteOutgoingRouteStep(request, controlledConnection);
				responses.add(response);
			}
			controlledConnection.commit();
		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	/*
	 * BEGIN PUBLIC SIMPLE TRANSACCIONAL METHODS
	 * 
	 */

	/* Transactional Simple OutgoingRoute Management Methods */

	public ItTransactionalResponse loadOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.loadOutgoingRoute(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				responses.add(this.loadOutgoingRoute(request, getTransactionalConnection(transactionId)));
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse addOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.addOutgoingRoute(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection =  this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = addOutgoingRoute(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse updateOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.updateOutgoingRoute(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = updateOutgoingRoute(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse removeOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.removeOutgoingRoute(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = removeOutgoingRoute(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}



	
	/* Transactional Simple OutgoingRouteStep Management Methods */

	public ItTransactionalResponse loadOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.loadOutgoingRouteStep(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = loadOutgoingRouteStep(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse addOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.addOutgoingRouteStep(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = addOutgoingRouteStep(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse updateOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.updateOutgoingRouteStep(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = updateOutgoingRouteStep(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse removeOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			return this.removeOutgoingRouteStep(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = removeOutgoingRouteStep(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}




	/*
	 *
	 * END PUBLIC SIMPLE TRANSACCIONAL METHODS
	 * 
	 */

	
	/*
	 *
	 * BEGIN PUBLIC COMPLETE TRANSACCIONAL METHODS
	 * 
	 */

	/* Transactional Complete ItOutgoingRoute Management Methods */ 

	public ItTransactionalResponse loadCompleteOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.loadCompleteOutgoingRoute(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadCompleteOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = loadCompleteOutgoingRoute(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse addCompleteOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.addCompleteOutgoingRoute(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addCompleteOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = addCompleteOutgoingRoute(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse updateCompleteOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.updateCompleteOutgoingRoute(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateCompleteOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = updateCompleteOutgoingRoute(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse removeCompleteOutgoingRoute(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.removeCompleteOutgoingRoute(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeCompleteOutgoingRoutes(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = removeCompleteOutgoingRoute(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	/* Transactional Complete ItOutgoingRouteStep Management Methods */

	public ItTransactionalResponse loadCompleteOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.loadCompleteOutgoingRouteStep(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> loadCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = loadCompleteOutgoingRouteStep(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse addCompleteOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.addCompleteOutgoingRouteStep(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> addCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = addCompleteOutgoingRouteStep(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse updateCompleteOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.updateCompleteOutgoingRouteStep(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> updateCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = updateCompleteOutgoingRouteStep(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public ItTransactionalResponse removeCompleteOutgoingRouteStep(ItTransactionalRequest request, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			return this.removeCompleteOutgoingRouteStep(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<ItTransactionalResponse> removeCompleteOutgoingRouteSteps(List<ItTransactionalRequest> requests, Long transactionId) throws InteraxTelephonyGeneralException {
		try {
			List<ItTransactionalResponse> responses = new ArrayList<ItTransactionalResponse>();
			for (ItTransactionalRequest request : requests) {
				ItTransactionalResponse response = removeCompleteOutgoingRouteStep(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}


	/*
	 *
	 * END PUBLIC COMPLETE TRANSACCIONAL METHODS
	 * 
	 */
	
	/*
	 *
	 *
	 *
	 *	END PUBLIC LAYER
	 *
	 *
	 * 
	 * 
	 */	

	
	
	
	
	
	
	/* Private OutgoingRoute Management Atomic Methods */

	private ItTransactionalResponse loadOutgoingRoute(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long ItOutgoingRouteId = (Long) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		ItOutgoingRoute ItOutgoingRoute = this.loadOutgoingRoute(ItOutgoingRouteId, transactionalConnection);
		response.setResponseData(ItOutgoingRoute);
		response.setResponseType(ItProvider.class);

		return response;
	}

	private ItTransactionalResponse addOutgoingRoute(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItOutgoingRoute ItOutgoingRoute = (ItOutgoingRoute) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		Long ItOutgoingRouteId = this.addOutgoingRoute(ItOutgoingRoute, transactionalConnection);
		ItOutgoingRoute.setId(ItOutgoingRouteId);
	//	ItOutgoingRoute.setSecret(generateRandomOutgoingRouteSecret());
		response.setResponseData(ItOutgoingRoute);
		response.setResponseType(ItOutgoingRoute.getClass());

		return response;
	}

	private ItTransactionalResponse updateOutgoingRoute(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItOutgoingRoute ItOutgoingRoute = (ItOutgoingRoute) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		this.updateOutgoingRoute(ItOutgoingRoute, transactionalConnection);
		response.setResponseData(ItOutgoingRoute);
		response.setResponseType(ItOutgoingRoute.getClass());

		return response;
	}

	private ItTransactionalResponse removeOutgoingRoute(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long ItOutgoingRouteId = (Long) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		Integer responseData = this.removeOutgoingRoute(ItOutgoingRouteId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}

	private ItOutgoingRoute loadOutgoingRoute(Long itOutgoingRouteId, Connection controlledConnection) throws Exception{
		return loadOutgoingRoute(itOutgoingRouteId, controlledConnection, true);
	}

	private ItOutgoingRoute loadOutgoingRoute(Long itOutgoingRouteId, Connection controlledConnection, boolean isCrud) throws Exception{
		ItOutgoingRoute itOutgoingRouteLoadObject = null;
		ItOutgoingRouteManager itOutgoingRouteManager;

		if(controlledConnection!=null)
			itOutgoingRouteManager= new ItOutgoingRouteManager(controlledConnection);
		else
			itOutgoingRouteManager= new ItOutgoingRouteManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		itOutgoingRouteLoadObject = (ItOutgoingRoute)itOutgoingRouteManager.load(itOutgoingRouteId);

		if (itOutgoingRouteLoadObject==null){
			throw new InteraxTelephonyOutgoingRouteNotFoundException("OutgoingRoute " + itOutgoingRouteId + " does not exists");
		}
		return itOutgoingRouteLoadObject;

	}

	private long addOutgoingRoute(ItOutgoingRoute itOutgoingRoute, Connection controlledConnection) throws Exception{
		return addOutgoingRoute(itOutgoingRoute, controlledConnection, true);
	}

	private long addOutgoingRoute(ItOutgoingRoute itOutgoingRoute, Connection controlledConnection, boolean isCrud) throws Exception{

		ItOutgoingRouteManager itOutgoingRouteManager;

		if(controlledConnection!=null)
			itOutgoingRouteManager= new ItOutgoingRouteManager(controlledConnection);
		else
			itOutgoingRouteManager= new ItOutgoingRouteManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		long itOutgoingRouteId = itOutgoingRouteManager.insertAutoIncrement(itOutgoingRoute);
		
		//  addStep
		
		return itOutgoingRouteId;
	}

	private int updateOutgoingRoute(ItOutgoingRoute itOutgoingRoute, Connection controlledConnection) throws Exception {
		return updateOutgoingRoute(itOutgoingRoute, controlledConnection, true);
	}

	private int updateOutgoingRoute(ItOutgoingRoute itOutgoingRoute, Connection controlledConnection, boolean isCrud) throws Exception {

		ItOutgoingRouteManager itOutgoingRouteManager;

		if(controlledConnection!=null)
			itOutgoingRouteManager= new ItOutgoingRouteManager(controlledConnection);
		else
			itOutgoingRouteManager= new ItOutgoingRouteManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int itOutgoingRouteUpdateRowsAffected = itOutgoingRouteManager.update(itOutgoingRoute, itOutgoingRoute.getId());
	

		if (itOutgoingRouteUpdateRowsAffected == 0){
			throw new InteraxTelephonyOutgoingRouteNotFoundException("OutgoingRoute " + itOutgoingRoute.getId() + " does not exists");
		}
		return itOutgoingRouteUpdateRowsAffected;
	}

	private int removeOutgoingRoute(Long itOutgoingRouteId, Connection controlledConnection) throws Exception{
		return removeOutgoingRoute(itOutgoingRouteId, controlledConnection, true);
	}

	private int removeOutgoingRoute(Long itOutgoingRouteId, Connection controlledConnection, boolean isCrud) throws Exception{

		ItOutgoingRouteManager itOutgoingRouteManager;

		if(controlledConnection!=null)
			itOutgoingRouteManager = new ItOutgoingRouteManager(controlledConnection);
		else
			itOutgoingRouteManager= new ItOutgoingRouteManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}
		

		int itOutgoingRouteDeleteRowsAffected = itOutgoingRouteManager.delete(itOutgoingRouteId);
		
		if(itOutgoingRouteDeleteRowsAffected == 0){
			throw new InteraxTelephonyOutgoingRouteNotFoundException("OutgoingRoute " + itOutgoingRouteId + " does not exists");
		}

		return itOutgoingRouteDeleteRowsAffected;
	}

	

	/* Private OutgoingRouteStep Management Atomic Methods */

	private ItTransactionalResponse loadOutgoingRouteStep(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long ItOutgoingRouteStepId = (Long) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		ItOutgoingRouteStep ItOutgoingRouteStep = this.loadOutgoingRouteStep(ItOutgoingRouteStepId, transactionalConnection);
		response.setResponseData(ItOutgoingRouteStep);
		response.setResponseType(ItProvider.class);

		return response;
	}

	private ItTransactionalResponse addOutgoingRouteStep(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItOutgoingRouteStep ItOutgoingRouteStep = (ItOutgoingRouteStep) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		Long ItOutgoingRouteStepId = this.addOutgoingRouteStep(ItOutgoingRouteStep, transactionalConnection);
		ItOutgoingRouteStep.setId(ItOutgoingRouteStepId);
	//	ItOutgoingRouteStep.setSecret(generateRandomOutgoingRouteStepSecret());
		response.setResponseData(ItOutgoingRouteStep);
		response.setResponseType(ItOutgoingRouteStep.getClass());

		return response;
	}

	private ItTransactionalResponse updateOutgoingRouteStep(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItOutgoingRouteStep ItOutgoingRouteStep = (ItOutgoingRouteStep) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		this.updateOutgoingRouteStep(ItOutgoingRouteStep, transactionalConnection);
		response.setResponseData(ItOutgoingRouteStep);
		response.setResponseType(ItOutgoingRouteStep.getClass());

		return response;
	}

	private ItTransactionalResponse removeOutgoingRouteStep(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long ItOutgoingRouteStepId = (Long) request.getRequestData();
		ItTransactionalResponse response = new ItTransactionalResponse();
		Integer responseData = this.removeOutgoingRouteStep(ItOutgoingRouteStepId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}

	private ItOutgoingRouteStep loadOutgoingRouteStep(Long itOutgoingRouteStepId, Connection controlledConnection) throws Exception{
		return loadOutgoingRouteStep(itOutgoingRouteStepId, controlledConnection, true);
	}

	private ItOutgoingRouteStep loadOutgoingRouteStep(Long itOutgoingRouteStepId, Connection controlledConnection, boolean isCrud) throws Exception{
		ItOutgoingRouteStep itOutgoingRouteStepLoadObject = null;
		ItOutgoingRouteStepManager itOutgoingRouteStepManager;

		if(controlledConnection!=null)
			itOutgoingRouteStepManager= new ItOutgoingRouteStepManager(controlledConnection);
		else
			itOutgoingRouteStepManager= new ItOutgoingRouteStepManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		itOutgoingRouteStepLoadObject = (ItOutgoingRouteStep)itOutgoingRouteStepManager.load(itOutgoingRouteStepId);

		if (itOutgoingRouteStepLoadObject==null){
			throw new InteraxTelephonyOutgoingRouteStepNotFoundException("OutgoingRouteStep " + itOutgoingRouteStepId + " does not exists");
		}
		return itOutgoingRouteStepLoadObject;

	}

	private long addOutgoingRouteStep(ItOutgoingRouteStep itOutgoingRouteStep, Connection controlledConnection) throws Exception{
		return addOutgoingRouteStep(itOutgoingRouteStep, controlledConnection, true);
	}

	private long addOutgoingRouteStep(ItOutgoingRouteStep itOutgoingRouteStep, Connection controlledConnection, boolean isCrud) throws Exception{

		ItOutgoingRouteStepManager itOutgoingRouteStepManager;

		if(controlledConnection!=null)
			itOutgoingRouteStepManager= new ItOutgoingRouteStepManager(controlledConnection);
		else
			itOutgoingRouteStepManager= new ItOutgoingRouteStepManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		long itOutgoingRouteStepId = itOutgoingRouteStepManager.insertAutoIncrement(itOutgoingRouteStep);
		
		//  addStep
				
		return itOutgoingRouteStepId;
	}

	private int updateOutgoingRouteStep(ItOutgoingRouteStep itOutgoingRouteStep, Connection controlledConnection) throws Exception {
		return updateOutgoingRouteStep(itOutgoingRouteStep, controlledConnection, true);
	}

	private int updateOutgoingRouteStep(ItOutgoingRouteStep itOutgoingRouteStep, Connection controlledConnection, boolean isCrud) throws Exception {

		ItOutgoingRouteStepManager itOutgoingRouteStepManager;

		if(controlledConnection!=null)
			itOutgoingRouteStepManager= new ItOutgoingRouteStepManager(controlledConnection);
		else
			itOutgoingRouteStepManager= new ItOutgoingRouteStepManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int itOutgoingRouteStepUpdateRowsAffected = itOutgoingRouteStepManager.update(itOutgoingRouteStep, itOutgoingRouteStep.getId());
	

		if (itOutgoingRouteStepUpdateRowsAffected == 0){
			throw new InteraxTelephonyOutgoingRouteStepNotFoundException("OutgoingRouteStep " + itOutgoingRouteStep.getId() + " does not exists");
		}
		return itOutgoingRouteStepUpdateRowsAffected;
	}

	private int removeOutgoingRouteStep(Long itOutgoingRouteStepId, Connection controlledConnection) throws Exception{
		return removeOutgoingRouteStep(itOutgoingRouteStepId, controlledConnection, true);
	}

	private int removeOutgoingRouteStep(Long itOutgoingRouteStepId, Connection controlledConnection, boolean isCrud) throws Exception{

		ItOutgoingRouteStepManager itOutgoingRouteStepManager;

		if(controlledConnection!=null)
			itOutgoingRouteStepManager = new ItOutgoingRouteStepManager(controlledConnection);
		else
			itOutgoingRouteStepManager= new ItOutgoingRouteStepManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}
		

		int itOutgoingRouteStepDeleteRowsAffected = itOutgoingRouteStepManager.delete(itOutgoingRouteStepId);
		
		if(itOutgoingRouteStepDeleteRowsAffected == 0){
			throw new InteraxTelephonyOutgoingRouteStepNotFoundException("OutgoingRouteStep " + itOutgoingRouteStepId + " does not exists");
		}

		return itOutgoingRouteStepDeleteRowsAffected;
	}

	
	
	
	
	/*
	 *
	 * BEGIN PRIVATE COMPLETE METHODS
	 * 
	 */	

	/* Private OutgoingRoute Management Complete Methods */

	private ItTransactionalResponse loadCompleteOutgoingRoute(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		Long outgoingRouteId = (Long) request.getRequestData();
		ItOutgoingRoute itOutgoingRoute = this.loadCompleteOutgoingRoute(outgoingRouteId, transactionalConnection);
		response.setResponseData(itOutgoingRoute);
		response.setResponseType(itOutgoingRoute.getClass());
		return response;
	}

	@SuppressWarnings("unchecked")
	private ItTransactionalResponse addCompleteOutgoingRoute(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		ItOutgoingRoute itOutgoingRoute = (ItOutgoingRoute) request.getRequestData();
		HashMap<String, Object> responseData = this.addCompleteOutgoingRoute(itOutgoingRoute, transactionalConnection);
		itOutgoingRoute = (ItOutgoingRoute)responseData.get(ItOutgoingRoute.class.getSimpleName());
		response.setResponseData(itOutgoingRoute);
		response.setResponseType(itOutgoingRoute.getClass());
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
	}

	@SuppressWarnings("unchecked")
	private ItTransactionalResponse updateCompleteOutgoingRoute(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		ItOutgoingRoute itOutgoingRoute = (ItOutgoingRoute) request.getRequestData();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		HashMap<String, Object> responseData = this.updateCompleteOutgoingRoute(itOutgoingRoute, transactionalConnection);
		itOutgoingRoute = (ItOutgoingRoute)responseData.get(ItOutgoingRoute.class.getSimpleName());
		response.setResponseData(itOutgoingRoute);
		response.setResponseType(itOutgoingRoute.getClass());
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
	}

	private ItTransactionalResponse removeCompleteOutgoingRoute(ItTransactionalRequest request, Connection transactionalConnection) throws Exception {
		ItTransactionalResponse response = new ItTransactionalResponse();
		Long itOutgoingRouteId = (Long) request.getRequestData();
		Integer responseData = this.removeCompleteOutgoingRoute(itOutgoingRouteId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}


	
	
	
	

	/* Complete OutgoingRoute Methods */

	private ItOutgoingRoute loadCompleteOutgoingRoute(Long outgoingRouteId, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		Connection controlledConnection = null;
		boolean transactionalCall = (transactionalConnection != null);

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			// Load the OutgoingRoute

			ItOutgoingRoute itOutgoingRoute = loadOutgoingRoute(outgoingRouteId, controlledConnection, false);

			List<ItOutgoingRouteStep> itOutgoingRouteSteps = this.loadCompleteOutgoingRouteSteps(outgoingRouteId, controlledConnection);

		

			itOutgoingRoute.setItOutgoingRouteSteps(itOutgoingRouteSteps);

		
			return itOutgoingRoute;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}

	}


	private HashMap<String, Object> addCompleteOutgoingRoute(ItOutgoingRoute itOutgoingRoute, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			
			// Add the OutgoingRoute
			Long newOutgoingRouteId = this.addOutgoingRoute(itOutgoingRoute, controlledConnection, false);
			itOutgoingRoute.setId(newOutgoingRouteId);
			

			ItOutgoingRouteManager itOutgoingRouteManager = new ItOutgoingRouteManager(controlledConnection);
			//OJO FIXME INDAGAR QUE HACE EL KEY PAIRS
//			HashMap<String, Object> keyPairs = new HashMap<String, Object>();
//			
//			itOutgoingRouteManager.update(keyPairs, newOutgoingRouteId);

			// Add the peers
			List<ItOutgoingRouteStep> itOutgoingRouteSteps = itOutgoingRoute.getItOutgoingRouteSteps();
			HashMap<Long, Long> itOutgoingRouteStepsPeersIdsTable = new HashMap<Long,Long>();
			correlativeIds.put(ItOutgoingRouteStep.class.getSimpleName(), itOutgoingRouteStepsPeersIdsTable);

			for (ItOutgoingRouteStep itOutgoingRouteStep : itOutgoingRouteSteps) {
				long oldItOutgoingRouteStepId = itOutgoingRouteStep.getId();
				itOutgoingRouteStep.setRouteId(newOutgoingRouteId);
				long newItOutgoingRouteStepId =  this.addOutgoingRouteStep(itOutgoingRouteStep,controlledConnection);
				itOutgoingRouteStep.setId(newItOutgoingRouteStepId);
				itOutgoingRouteStepsPeersIdsTable.put(oldItOutgoingRouteStepId, newItOutgoingRouteStepId);
			}
			
			response.put(itOutgoingRoute.getClass().getSimpleName(), itOutgoingRoute);
			response.put(CORRELATIVE_IDS_KEY, correlativeIds);

			if(!transactionalCall)
				controlledConnection.commit();

			return response;

		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}

	private HashMap<String, Object> updateCompleteOutgoingRoute(ItOutgoingRoute itOutgoingRoute, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

		
			ItOutgoingRoute itOutgoingRouteOld = this.loadCompleteOutgoingRoute(itOutgoingRoute.getId(), controlledConnection); 
			// Update OutgoingRoute
			this.updateOutgoingRoute(itOutgoingRoute, controlledConnection, false);
			
			// CorrelativeIds
			HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
			
			// Update the peers
			HashMap<Long, Long> stepIdsTable = new HashMap<Long,Long>();
			correlativeIds.put(ItOutgoingRouteStep.class.getSimpleName(), stepIdsTable);

			
			
			// add step rt_extension
			
			
			
			
			ItOutgoingRoute itOutgoingRouteNew = itOutgoingRoute;			
			List<Long> itOutgoingRouteOldIds = new ArrayList<Long>();

			for (ItOutgoingRouteStep itOutgoingRouteStepOld : itOutgoingRouteOld.getItOutgoingRouteSteps()) {
				itOutgoingRouteOldIds.add(itOutgoingRouteStepOld.getId());
			}

			for (ItOutgoingRouteStep itOutgoingRouteStepNew : itOutgoingRouteNew.getItOutgoingRouteSteps()) {
				if(itOutgoingRouteOldIds.contains(itOutgoingRouteStepNew.getId())){
					this.updateCompleteOutgoingRouteStep(itOutgoingRouteStepNew, controlledConnection);
					itOutgoingRouteOldIds.remove(itOutgoingRouteStepNew.getId()); //REMOVE ID
				}
				else{
					if(itOutgoingRouteStepNew.getId()>0){
						throw new InteraxTelephonyOutgoingRouteStepNotFoundException("OutgoingRoutePeer " + itOutgoingRouteStepNew.getId() + " not found in the DataBase");
					}
					this.addCompleteOutgoingRouteStep(itOutgoingRouteStepNew,  controlledConnection); 
				}
			}

			for (Long itOutgoingRouteStepId : itOutgoingRouteOldIds) {
				this.removeCompleteOutgoingRouteStep(itOutgoingRouteStepId, controlledConnection);
			}

			


			response.put(ItOutgoingRoute.class.getSimpleName(), itOutgoingRoute);
			response.put(CORRELATIVE_IDS_KEY, correlativeIds);

			if(!transactionalCall)
				controlledConnection.commit();

			return response;
		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}

	private int removeCompleteOutgoingRoute(Long itOutgoingRouteId, Connection transactionalConnection) throws InteraxTelephonyGeneralException {

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			ItOutgoingRouteManager itOutgoingRouteManager =  new ItOutgoingRouteManager(controlledConnection);

			ItOutgoingRoute itOutgoingRoute = (ItOutgoingRoute) this.loadCompleteOutgoingRoute(itOutgoingRouteId, controlledConnection);

			if(itOutgoingRoute==null){
				throw new InteraxTelephonyOutgoingRouteNotFoundException("OutgoingRoute " + itOutgoingRouteId + " not found");
			}
			
			List<ItOutgoingRouteStep> itOutgoingRouteSteps = itOutgoingRoute.getItOutgoingRouteSteps();
				
			for(ItOutgoingRouteStep itOutgoingRouteStep : itOutgoingRouteSteps){
				this.removeCompleteOutgoingRouteStep(itOutgoingRouteStep.getId(), controlledConnection);
			}		
			int OutgoingRouteRows = itOutgoingRouteManager.delete(itOutgoingRoute.getId());
			//DELETE OutgoingRoute
			if(!transactionalCall) controlledConnection.commit();

			return OutgoingRouteRows;

		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}


	
	
	
	/****************************************************************************/
	
	
	
	
	
	
	/*
	 *
	 * BEGIN PRIVATE COMPLETE METHODS
	 * 
	 */	

	/* Private OutgoingRouteStep Management Complete Methods */

	private ItTransactionalResponse loadCompleteOutgoingRouteStep(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		Long outgoingRouteId = (Long) request.getRequestData();
		ItOutgoingRouteStep itOutgoingRouteStep = this.loadCompleteOutgoingRouteStep(outgoingRouteId, transactionalConnection);
		response.setResponseData(itOutgoingRouteStep);
		response.setResponseType(itOutgoingRouteStep.getClass());
		return response;
	}
	


	@SuppressWarnings("unchecked")
	private ItTransactionalResponse addCompleteOutgoingRouteStep(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		ItOutgoingRouteStep itOutgoingRouteStep = (ItOutgoingRouteStep) request.getRequestData();
		HashMap<String, Object> responseData = this.addCompleteOutgoingRouteStep(itOutgoingRouteStep, transactionalConnection);
		itOutgoingRouteStep = (ItOutgoingRouteStep)responseData.get(ItOutgoingRouteStep.class.getSimpleName());
		response.setResponseData(itOutgoingRouteStep);
		response.setResponseType(itOutgoingRouteStep.getClass());
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
	}

	@SuppressWarnings("unchecked")
	private ItTransactionalResponse updateCompleteOutgoingRouteStep(ItTransactionalRequest request, Connection transactionalConnection) throws Exception{

		ItTransactionalResponse response = new ItTransactionalResponse();
		ItOutgoingRouteStep itOutgoingRouteStep = (ItOutgoingRouteStep) request.getRequestData();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		HashMap<String, Object> responseData = this.updateCompleteOutgoingRouteStep(itOutgoingRouteStep, transactionalConnection);
		itOutgoingRouteStep = (ItOutgoingRouteStep)responseData.get(ItOutgoingRouteStep.class.getSimpleName());
		response.setResponseData(itOutgoingRouteStep);
		response.setResponseType(itOutgoingRouteStep.getClass());
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
	}



	private ItTransactionalResponse removeCompleteOutgoingRouteStep(ItTransactionalRequest request, Connection transactionalConnection) throws Exception {
		ItTransactionalResponse response = new ItTransactionalResponse();
		Long itOutgoingRouteStepId = (Long) request.getRequestData();
		Integer responseData = this.removeCompleteOutgoingRouteStep(itOutgoingRouteStepId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}


	
	
	
	

	/* Complete OutgoingRouteStep Methods */

	private ItOutgoingRouteStep loadCompleteOutgoingRouteStep(Long outgoingRouteStepId, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		Connection controlledConnection = null;
		boolean transactionalCall = (transactionalConnection != null);

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();


			ItOutgoingRouteStep itOutgoingRouteStep = loadOutgoingRouteStep(outgoingRouteStepId, controlledConnection, false);
	
			return itOutgoingRouteStep;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}

	}
	
	private List<ItOutgoingRouteStep> loadCompleteOutgoingRouteSteps(Long outgoingRouteId, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			List<ItOutgoingRouteStep> outgoingRouteSteps = new ArrayList<ItOutgoingRouteStep>();

			ItOutgoingRouteStepManager itOutgoingRouteStepManager = new ItOutgoingRouteStepManager(controlledConnection);;
			List<Object> stepsObjectIds = itOutgoingRouteStepManager.listField("id", "routeId=" + outgoingRouteId);

			for (Object object : stepsObjectIds) {
				BigInteger bigInteger = (BigInteger) object;
				long stepId = bigInteger.longValue();
				ItOutgoingRouteStep itOutgoingRouteStep = loadOutgoingRouteStep(stepId, controlledConnection, false);
				outgoingRouteSteps.add(itOutgoingRouteStep);
			}

			return outgoingRouteSteps;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}



	private HashMap<String, Object> addCompleteOutgoingRouteStep(ItOutgoingRouteStep itOutgoingRouteStep, Connection transactionalConnection) throws InteraxTelephonyGeneralException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			
			// Add the OutgoingRouteStep
			Long newOutgoingRouteStepId = this.addOutgoingRouteStep(itOutgoingRouteStep, controlledConnection, false);
			itOutgoingRouteStep.setId(newOutgoingRouteStepId);				
			response.put(itOutgoingRouteStep.getClass().getSimpleName(), itOutgoingRouteStep);
			
			if(!transactionalCall)
				controlledConnection.commit();
	
			return response;

		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}



	private int removeCompleteOutgoingRouteStep(Long itOutgoingRouteStepId, Connection transactionalConnection) throws InteraxTelephonyGeneralException {

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			ItOutgoingRouteStepManager itOutgoingRouteStepManager =  new ItOutgoingRouteStepManager(controlledConnection);

			ItOutgoingRouteStep itOutgoingRouteStep = (ItOutgoingRouteStep) this.loadCompleteOutgoingRouteStep(itOutgoingRouteStepId, controlledConnection);

			if(itOutgoingRouteStep==null){
				throw new InteraxTelephonyOutgoingRouteStepNotFoundException("OutgoingRouteStep " + itOutgoingRouteStepId + " not found");
			}
			int outgoingRouteStepRows = this.removeOutgoingRouteStep(itOutgoingRouteStep.getId(), controlledConnection, false);
	
			//DELETE OutgoingRouteStep
			if(!transactionalCall) controlledConnection.commit();

			return outgoingRouteStepRows;

		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}


	private HashMap<String, Object> updateCompleteOutgoingRouteStep(ItOutgoingRouteStep itOutgoingRouteStep, Connection transactionalConnection) throws InteraxTelephonyGeneralException {
		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			// Update Provider
			this.updateOutgoingRouteStep(itOutgoingRouteStep, controlledConnection, false);
			
		
			response.put(ItOutgoingRouteStep.class.getSimpleName(), itOutgoingRouteStep);
	

			if(!transactionalCall)
				controlledConnection.commit();

			return response;
		}catch (Exception e) {
			if(!transactionalCall)
				throw this.manageExceptionAndConnection(e, controlledConnection);
			else
				throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
		
	}
	
	
	/****************************************************************************/
	
	
	



	
	

}
