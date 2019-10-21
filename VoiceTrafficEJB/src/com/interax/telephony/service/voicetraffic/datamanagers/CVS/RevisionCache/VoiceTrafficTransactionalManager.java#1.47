package com.interax.telephony.service.voicetraffic.datamanagers;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
//import java.util.Hashtable;
import java.util.List;
import java.util.Random;
//import java.util.Vector;

import com.interax.persistence.datamanagers.NullDataManager;
import com.interax.telephony.cdr.InteraxTelephonyCdrFormater;
import com.interax.telephony.service.data.ItDid;
import com.interax.telephony.service.data.ItEnterprise;
import com.interax.telephony.service.data.ItPeerType;
import com.interax.telephony.service.data.RtExtension;
import com.interax.telephony.service.data.RtIaxPeer;
import com.interax.telephony.service.data.RtSipPeer;
import com.interax.telephony.service.data.ServiceFamily;
import com.interax.telephony.service.datamanagers.ItDidManager;
import com.interax.telephony.service.datamanagers.ItEnterpriseManager;
import com.interax.telephony.service.datamanagers.RtExtensionManager;
import com.interax.telephony.service.datamanagers.RtIaxPeerManager;
import com.interax.telephony.service.datamanagers.RtSipPeerManager;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficCustomerNotFoundException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficDidNotFoundException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficGeneralException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficInvalidTransactionException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficInvalidTransactionIdException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficPeerNotFoundException;
import com.interax.telephony.service.exception.voicetraffic.VoiceTrafficVirtualOffshoreNumberNotFoundException;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficAccessType;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficTransactionalRequest;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficTransactionalResponse;
import com.interax.telephony.service.voicetraffic.data.VtCustomer;
import com.interax.telephony.service.voicetraffic.data.VtDid;
import com.interax.telephony.service.voicetraffic.data.VtDidStep;
import com.interax.telephony.service.voicetraffic.data.VtDidType;
import com.interax.telephony.service.voicetraffic.data.VtPeer;
import com.interax.telephony.service.voicetraffic.data.VtVirtualOffshoreNumber;
import java.util.ArrayList;

public class VoiceTrafficTransactionalManager extends NullDataManager{

	private String dataSourceName;

	private static final String VOICETRAFFIC_PREFIX = "vt-";
	private static final String VOICETRAFFIC_IAX_PEER_PREFIX = VOICETRAFFIC_PREFIX + "iax-";
	private static final String VOICETRAFFIC_SIP_PEER_PREFIX = VOICETRAFFIC_PREFIX + "sip-";

	private static final String VOICETRAFFIC_BASE_CONTEXT_PREFIX = "rt_vt_";
	private static final String VOICETRAFFIC_DISABLED_CONTEXT_PREFIX = VOICETRAFFIC_BASE_CONTEXT_PREFIX + "disabled_";
	private static final String VOICETRAFFIC_INCOMING_CONTEXT = "vt_incoming";

	private static final String CORRELATIVE_IDS_KEY = "correlativeIds";

	//private static final TimeZone baseTimeZone = TimeZone.getTimeZone("GMT+0");

	//FIXME VALOR CABLEADO
	private static final int PEER_SECRET_ALPHA_LENGTH = 6;
	private static final int PEER_SECRET_NUMERIC_LENGTH = 4;

	private static Long nextTransactionId;
	private static HashMap<Long, Connection> transactionalConnections;

	public VoiceTrafficTransactionalManager(String dataSourceName) {
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

	/* Atomic Simple Customer Management Methods */

	public VoiceTrafficTransactionalResponse loadCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.loadCustomer(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.loadCustomer(request, controlledConnection));
			}

			controlledConnection.commit();
			
		}catch(Exception e){
			throw this.manageException(e);
		}
		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public VoiceTrafficTransactionalResponse addCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.addCustomer(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.addCustomer(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public VoiceTrafficTransactionalResponse updateCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.updateCustomer(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public  List<VoiceTrafficTransactionalResponse> updateCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.updateCustomer(request, controlledConnection));
			}
			controlledConnection.commit();

		}catch(Exception e){
			throw this.manageExceptionAndConnection(e, controlledConnection);
		}finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public VoiceTrafficTransactionalResponse removeCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.removeCustomer(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.removeCustomer(request, controlledConnection));
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

	/* Atomic Simple Did Management Methods */

	public VoiceTrafficTransactionalResponse loadDid(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.loadDid(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadDids(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.loadDid(request, controlledConnection));
			}
			
			controlledConnection.commit();
			
		}catch(Exception e){
			throw this.manageException(e);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public VoiceTrafficTransactionalResponse addDid(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.addDid(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addDids(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.addDid(request, controlledConnection));
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

	public VoiceTrafficTransactionalResponse updateDid(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.updateDid(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateDids(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.updateDid(request, controlledConnection));
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

	public VoiceTrafficTransactionalResponse removeDid(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.removeDid(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeDids(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.removeDid(request, controlledConnection));
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

	/* Atomic Simple Peer Management Methods */

	public VoiceTrafficTransactionalResponse loadPeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection nullConnection = null;
			return this.loadPeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadPeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.loadPeer(request, controlledConnection));
			}
			
			controlledConnection.commit();
			
		}catch(Exception e){
			throw this.manageException(e);
		}
		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public VoiceTrafficTransactionalResponse addPeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection nullConnection = null;
			return addPeer(request, nullConnection);
			
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addPeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(addPeer(request, controlledConnection));
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

	public VoiceTrafficTransactionalResponse updatePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try {
			Connection nullConnection = null;
			return updatePeer(request, nullConnection);
			
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updatePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(updatePeer(request, controlledConnection));
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

	public VoiceTrafficTransactionalResponse removePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection nullConnection = null;
			return removePeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;
		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(removePeer(request, controlledConnection));
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

	/* Atomic Simple VirtualOffshoreNumber Management Methods */

	public VoiceTrafficTransactionalResponse loadVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.loadVirtualOffshoreNumber(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.loadVirtualOffshoreNumber(request, controlledConnection));
			}
			
			controlledConnection.commit();
			
		}catch(Exception e){
			throw this.manageException(e);
		}

		finally{
			this.closeConecction(controlledConnection);
		}
		return responses;
	}

	public VoiceTrafficTransactionalResponse addVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.addVirtualOffshoreNumber(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.addVirtualOffshoreNumber(request, controlledConnection));
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

	public VoiceTrafficTransactionalResponse updateVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.updateVirtualOffshoreNumber(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public  List<VoiceTrafficTransactionalResponse> updateVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.updateVirtualOffshoreNumber(request, controlledConnection));
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

	public VoiceTrafficTransactionalResponse removeVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException{
		try{
			Connection controlledConnection = null;
			return this.removeVirtualOffshoreNumber(request, controlledConnection);

		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();

			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.removeVirtualOffshoreNumber(request, controlledConnection));
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

	/* Atomic Complete Peer Management Methods */

	public VoiceTrafficTransactionalResponse loadCompletePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.loadCompletePeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadCompletePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException {
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.loadCompletePeer(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse addCompletePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.addCompletePeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addCompletePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException {
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.addCompletePeer(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse updateCompletePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.updateCompletePeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateCompletePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException {
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.updateCompletePeer(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse removeCompletePeer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.removeCompletePeer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeCompletePeers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException {
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.removeCompletePeer(request, controlledConnection);
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

	/* Atomic Complete Customer Management Methods */

	public VoiceTrafficTransactionalResponse loadCompleteCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.loadCompleteCustomer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.loadCompleteCustomer(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse addCompleteCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.addCompleteCustomer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException {
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.addCompleteCustomer(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse updateCompleteCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.updateCompleteCustomer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.updateCompleteCustomer(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse removeCompleteCustomer(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.removeCompleteCustomer(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.removeCompleteCustomer(request, controlledConnection);
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

	/* Atomic Complete VirtualOffshoreNumber Management Methods */

	public VoiceTrafficTransactionalResponse loadCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.loadCompleteVirtualOffshoreNumber(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.loadCompleteVirtualOffshoreNumber(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse addCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.addCompleteVirtualOffshoreNumber(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException {
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.addCompleteVirtualOffshoreNumber(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse updateCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.updateCompleteVirtualOffshoreNumber(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.updateCompleteVirtualOffshoreNumber(request, controlledConnection);
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

	public VoiceTrafficTransactionalResponse removeCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request) throws VoiceTrafficException {
		try {
			Connection nullConnection = null;
			return this.removeCompleteVirtualOffshoreNumber(request, nullConnection);
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests) throws VoiceTrafficException{
		List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
		Connection controlledConnection = null;

		try{
			controlledConnection = this.createControlledConnection();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = this.removeCompleteVirtualOffshoreNumber(request, controlledConnection);
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

	/* Transactional Simple Customer Management Methods */

	public VoiceTrafficTransactionalResponse loadCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.loadCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.loadCustomer(request, getTransactionalConnection(transactionId)));
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse addCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.addCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection =  this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = addCustomer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse updateCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.updateCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = updateCustomer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse removeCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.removeCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = removeCustomer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	/* Transactional Simple Did Management Methods */

	public VoiceTrafficTransactionalResponse loadDid(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.loadDid(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadDids(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = loadDid(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse addDid(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.addDid(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addDids(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = addDid(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse updateDid(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.updateDid(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateDids(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = updateDid(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse removeDid(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.removeDid(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeDids(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = removeDid(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	/* Transactional Simple Peer Management Methods */

	public VoiceTrafficTransactionalResponse loadPeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.loadPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadPeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = loadPeer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse addPeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.addPeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addPeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = addPeer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse updatePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.updatePeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updatePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = updatePeer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse removePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.removePeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = removePeer(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	/* Transactional Simple VirtualOffshoreNumber Management Methods */

	public VoiceTrafficTransactionalResponse loadVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.loadVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				responses.add(this.loadVirtualOffshoreNumber(request, getTransactionalConnection(transactionId)));
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse addVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.addVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection =  this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = addVirtualOffshoreNumber(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse updateVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.updateCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = updateVirtualOffshoreNumber(request, transactionalConnection);
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse removeVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException{
		try {
			return this.removeVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException{
		try {
			Connection transactionalConnection = this.getTransactionalConnection(transactionId);
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = removeVirtualOffshoreNumber(request, transactionalConnection);
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

	/* Transactional Complete VtCustomer Management Methods */ 

	public VoiceTrafficTransactionalResponse loadCompleteCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.loadCompleteCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = loadCompleteCustomer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse addCompleteCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.addCompleteCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = addCompleteCustomer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse updateCompleteCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.updateCompleteCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = updateCompleteCustomer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse removeCompleteCustomer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.removeCompleteCustomer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeCompleteCustomers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = removeCompleteCustomer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	/* Transactional Complete vtPeer Management Methods */

	public VoiceTrafficTransactionalResponse loadCompletePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.loadCompletePeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadCompletePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = loadCompletePeer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse addCompletePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.addCompletePeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addCompletePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = addCompletePeer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse updateCompletePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.updateCompletePeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateCompletePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = updateCompletePeer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse removeCompletePeer(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.removeCompletePeer(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeCompletePeers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = removeCompletePeer(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	/* Transactional Complete VirtualOffshoreNumber Management Methods */ 

	public VoiceTrafficTransactionalResponse loadCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.loadCompleteVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> loadCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = loadCompleteVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse addCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.addCompleteVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> addCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = addCompleteVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse updateCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.updateCompleteVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> updateCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = updateCompleteVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
				responses.add(response);
			}
			return responses;
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public VoiceTrafficTransactionalResponse removeCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Long transactionId) throws VoiceTrafficException {
		try {
			return this.removeCompleteVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
		}catch(Exception e){
			throw this.manageException(e);
		}
	}

	public List<VoiceTrafficTransactionalResponse> removeCompleteVirtualOffshoreNumbers(List<VoiceTrafficTransactionalRequest> requests, Long transactionId) throws VoiceTrafficException {
		try {
			List<VoiceTrafficTransactionalResponse> responses = new ArrayList<VoiceTrafficTransactionalResponse>();
			for (VoiceTrafficTransactionalRequest request : requests) {
				VoiceTrafficTransactionalResponse response = removeCompleteVirtualOffshoreNumber(request, getTransactionalConnection(transactionId));
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

	/* Private Customer Management Atomic Methods */

	private VoiceTrafficTransactionalResponse loadCustomer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long vtCustomerId = null;
		vtCustomerId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		VtCustomer vtCustomer = this.loadCustomer(vtCustomerId, transactionalConnection);
		response.setResponseData(vtCustomer);
		response.setResponseType(VtCustomer.class);
		return response;
	}

	private VoiceTrafficTransactionalResponse addCustomer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VtCustomer vtCustomer = (VtCustomer) request.getRequestData();
		Long vtCustomerId = this.addCustomer(vtCustomer, transactionalConnection);
		vtCustomer.setId(vtCustomerId);
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		response.setResponseData(vtCustomer);
		response.setResponseType(vtCustomer.getClass());

		return response;
	}

	private VoiceTrafficTransactionalResponse updateCustomer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VtCustomer vtCustomer = (VtCustomer) request.getRequestData();
		this.updateCustomer(vtCustomer, transactionalConnection);
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		response.setResponseData(vtCustomer);
		response.setResponseType(vtCustomer.getClass());

		return response;
	}

	private VoiceTrafficTransactionalResponse removeCustomer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long vtCustomerId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Integer responseData = this.removeCustomer(vtCustomerId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());

		return response;
	}

	/* Private Did Management Atomic Methods */

	private VoiceTrafficTransactionalResponse loadDid(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{
		Long vtDidId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		VtDid vtDid = this.loadDid(vtDidId, transactionalConnection);
		response.setResponseData(vtDid);
		response.setResponseType(vtDid.getClass());
		return response;
	}

	private VoiceTrafficTransactionalResponse addDid(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{
		VtDid vtDid = (VtDid) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Long vtDidId = this.addDid(vtDid, transactionalConnection);
		vtDid.setId(vtDidId);
		response.setResponseData(vtDid);
		response.setResponseType(vtDid.getClass());
		return response;
	}

	private VoiceTrafficTransactionalResponse updateDid(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VtDid newVtDid = (VtDid) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		this.updateDid(newVtDid, transactionalConnection);
		response.setResponseData(newVtDid);
		response.setResponseType(newVtDid.getClass());

		return response;
	}

	private VoiceTrafficTransactionalResponse removeDid(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{
		Long removeVtDidId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Integer responseData = this.removeDid(removeVtDidId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}

	/* Private Peer Management Atomic Methods */

	private VoiceTrafficTransactionalResponse loadPeer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long vtPeerId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		VtPeer vtPeer = this.loadPeer(vtPeerId, transactionalConnection);
		response.setResponseData(vtPeer);
		response.setResponseType(VtCustomer.class);

		return response;
	}

	private VoiceTrafficTransactionalResponse addPeer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VtPeer vtPeer = (VtPeer) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Long vtPeerId = this.addPeer(vtPeer, transactionalConnection);
		vtPeer.setId(vtPeerId);
		vtPeer.setSecret(generateRandomPeerSecret());
		response.setResponseData(vtPeer);
		response.setResponseType(vtPeer.getClass());

		return response;
	}

	private VoiceTrafficTransactionalResponse updatePeer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VtPeer vtPeer = (VtPeer) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		this.updatePeer(vtPeer, transactionalConnection);
		response.setResponseData(vtPeer);
		response.setResponseType(vtPeer.getClass());

		return response;
	}

	private VoiceTrafficTransactionalResponse removePeer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long vtPeerId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Integer responseData = this.removePeer(vtPeerId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}

	/* Private VirtualOffshoreNumber Management Atomic Methods */

	private VoiceTrafficTransactionalResponse loadVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long vtVirtaulOffshoreNumberId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		VtVirtualOffshoreNumber vtVirtualOffshoreNumber = this.loadVirtualOffshoreNumber(vtVirtaulOffshoreNumberId, transactionalConnection);
		response.setResponseData(vtVirtualOffshoreNumber);
		response.setResponseType(vtVirtualOffshoreNumber.getClass());

		return response;
	}

	private VoiceTrafficTransactionalResponse addVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VtVirtualOffshoreNumber vtVirtaulOffshoreNumber = (VtVirtualOffshoreNumber) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		long vtVirtaulOffshoreNumberId = this.addVirtualOffshoreNumber(vtVirtaulOffshoreNumber, transactionalConnection);
		vtVirtaulOffshoreNumber.setId(vtVirtaulOffshoreNumberId);
		response.setResponseData(vtVirtaulOffshoreNumber);
		response.setResponseType(vtVirtaulOffshoreNumber.getClass());

		return response;
	}

	private VoiceTrafficTransactionalResponse updateVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VtVirtualOffshoreNumber vtVirtualOffshoreNumber = (VtVirtualOffshoreNumber) request.getRequestData();
		this.updateVirtualOffshoreNumber(vtVirtualOffshoreNumber, transactionalConnection);
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		response.setResponseData(vtVirtualOffshoreNumber);
		response.setResponseType(vtVirtualOffshoreNumber.getClass());

		return response;
	}

	private VoiceTrafficTransactionalResponse removeVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		Long vtVirtualOffshoreNumberId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Integer responseData = this.removeVirtualOffshoreNumber(vtVirtualOffshoreNumberId, transactionalConnection);
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

	/* Private Customer Management Complete Methods */

	private VoiceTrafficTransactionalResponse loadCompleteCustomer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Long customerId = (Long) request.getRequestData();
		VtCustomer vtCustomer = this.loadCompleteCustomer(customerId, transactionalConnection);
		response.setResponseData(vtCustomer);
		response.setResponseType(vtCustomer.getClass());
		return response;
	}

	@SuppressWarnings("unchecked")
	private VoiceTrafficTransactionalResponse addCompleteCustomer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

			VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
			HashMap<String, HashMap<Long, Long>> correlativeIds;
			VtCustomer vtCustomer = (VtCustomer) request.getRequestData();
			HashMap<String, Object> responseData = this.addCompleteCustomer(vtCustomer, transactionalConnection);
			vtCustomer = (VtCustomer)responseData.get(VtCustomer.class.getSimpleName());
			response.setResponseData(vtCustomer);
			response.setResponseType(vtCustomer.getClass());
			correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
			response.setCorrelativeIds(correlativeIds);
			return response;
	}

	@SuppressWarnings("unchecked")
	private VoiceTrafficTransactionalResponse updateCompleteCustomer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{

		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		VtCustomer vtCustomer = (VtCustomer) request.getRequestData();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		HashMap<String, Object> responseData = this.updateCompleteCustomer(vtCustomer, transactionalConnection);
		vtCustomer = (VtCustomer)responseData.get(VtCustomer.class.getSimpleName());
		response.setResponseData(vtCustomer);
		response.setResponseType(vtCustomer.getClass());
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
	}

	private VoiceTrafficTransactionalResponse removeCompleteCustomer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception {
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Long vtCustomerId = (Long) request.getRequestData();
		Integer responseData = this.removeCompleteCustomer(vtCustomerId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());
		return response;
	}


	/* Private Peer Management Complete Methods */

	private VoiceTrafficTransactionalResponse loadCompletePeer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Long vtPeerId = (Long) request.getRequestData();
		VtPeer vtPeer = this.loadCompletePeer(vtPeerId, transactionalConnection);
		response.setResponseData(vtPeer);
		response.setResponseType(vtPeer.getClass());
		return response;
	}

	@SuppressWarnings("unchecked")
	private VoiceTrafficTransactionalResponse addCompletePeer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		VtPeer vtPeer = (VtPeer) request.getRequestData();
		VtCustomer vtCustomer = this.loadCustomer(vtPeer.getCustomerId(), transactionalConnection, false);
		HashMap<String,Object> responseData = this.addCompletePeer(vtPeer, vtCustomer, transactionalConnection);
		vtPeer = (VtPeer)responseData.get(VtPeer.class.getSimpleName());
		response.setResponseData(vtPeer);
		response.setResponseType(VtPeer.class);
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
		
		
	}

	@SuppressWarnings("unchecked")
	private VoiceTrafficTransactionalResponse updateCompletePeer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		HashMap<String, HashMap<Long, Long>> correlativeIds;
		VtPeer vtPeer = (VtPeer) request.getRequestData();
		VtCustomer vtCustomer = this.loadCustomer(vtPeer.getCustomerId(), transactionalConnection, false);
		HashMap<String,Object> responseData = this.updateCompletePeer(vtPeer, vtCustomer, transactionalConnection);
		vtPeer = (VtPeer)responseData.get(VtPeer.class.getSimpleName());
		response.setResponseData(vtPeer);
		response.setResponseType(VtPeer.class);
		correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
		response.setCorrelativeIds(correlativeIds);
		return response;
	}

	private VoiceTrafficTransactionalResponse removeCompletePeer(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception{
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Long peerId = (Long) request.getRequestData();
		Integer removePeerResponse = this.removeCompletePeer(peerId, transactionalConnection);
		response.setResponseData(removePeerResponse);
		response.setResponseType(removePeerResponse.getClass());
		return response;
	}


	/* Private VirtaulOffshoreNumber Complete Methods */

	private VoiceTrafficTransactionalResponse loadCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception {
		// Load the Customer
		Long virtualOffShoreNumberId = (Long) request.getRequestData();
		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		HashMap<String, List> relatedEntities = new HashMap<String, List>();

		HashMap<String, Object> completeVon = this.loadCompleteVirtualOffshoreNumber(virtualOffShoreNumberId, transactionalConnection);

		List<Object> relatedEntitiesDid = new ArrayList<Object>();
		VtDid vtDid = (VtDid)completeVon.get(VtDid.class.getSimpleName());
		relatedEntitiesDid.add(vtDid);
		relatedEntities.put(VtDid.class.getSimpleName(), relatedEntitiesDid);
		VtVirtualOffshoreNumber vtVirtualOffshoreNumber = (VtVirtualOffshoreNumber)completeVon.get(VtVirtualOffshoreNumber.class.getSimpleName()); 
		response.setResponseData(vtVirtualOffshoreNumber);
		response.setResponseType(vtVirtualOffshoreNumber.getClass());
		response.setRelatedEntities(relatedEntities);
		return response;
	}

	private VoiceTrafficTransactionalResponse addCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception {

		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		HashMap<String, List> relatedEntities = request.getRelatedEntities();
		
		if(relatedEntities == null){
			throw new VoiceTrafficGeneralException("Missing Did in request");
		}

		VtDid vtDid = (VtDid)relatedEntities.get(VtDid.class.getSimpleName()).get(0);

		if(vtDid.getId()>0){
			//TODO: Tomar en cuenta este caso (loadDid)
			throw new VoiceTrafficGeneralException("This case is not yet developed");
		}

		VtVirtualOffshoreNumber vtVirtualOffshoreNumber = (VtVirtualOffshoreNumber) request.getRequestData();
		
		HashMap<String,Object> virtualOffshoreNumber = addCompleteVirtualOffshoreNumber(vtVirtualOffshoreNumber, vtDid, transactionalConnection);
		vtVirtualOffshoreNumber = (VtVirtualOffshoreNumber)virtualOffshoreNumber.get(VtVirtualOffshoreNumber.class.getSimpleName());
		vtDid = (VtDid)virtualOffshoreNumber.get(VtDid.class.getSimpleName());
		
		relatedEntities = new HashMap<String, List>();
		List<Object> vtDids = new ArrayList<Object>();
		vtDids.add(vtDid);
		relatedEntities.put(VtDid.class.getSimpleName(), vtDids);
		
		response.setRelatedEntities(relatedEntities);
		response.setCorrelativeIds((HashMap<String, HashMap<Long,Long>>) virtualOffshoreNumber.get(CORRELATIVE_IDS_KEY));
		response.setResponseData(vtVirtualOffshoreNumber);
		response.setResponseType(vtVirtualOffshoreNumber.getClass());
		
		return response;
	}

	@SuppressWarnings("unchecked")
	private VoiceTrafficTransactionalResponse updateCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception {

		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();

		HashMap<String, HashMap<Long, Long>> correlativeIds;
		HashMap<String, List> relatedEntities = request.getRelatedEntities();

		VtVirtualOffshoreNumber vtVirtualOffshoreNumber = (VtVirtualOffshoreNumber) request.getRequestData();
		
		if(vtVirtualOffshoreNumber.getDidId()<0){
			VtDid vtDid = (VtDid)relatedEntities.get(VtDid.class.getSimpleName()).get(0);

			HashMap<String,Object> responseData = updateCompleteVirtualOffshoreNumber(vtVirtualOffshoreNumber, vtDid, transactionalConnection);

			vtVirtualOffshoreNumber = (VtVirtualOffshoreNumber) responseData.get(VtVirtualOffshoreNumber.class.getSimpleName());
			response.setResponseData(vtVirtualOffshoreNumber);
			response.setResponseType(VtVirtualOffshoreNumber.class);
			correlativeIds = (HashMap<String, HashMap<Long,Long>>)responseData.get(CORRELATIVE_IDS_KEY);
			response.setCorrelativeIds(correlativeIds);
		}
		else{
			//TODO: Tomar en cuenta este caso (loadDid)
			throw new VoiceTrafficGeneralException("This case is not yet developed.");
		}

		return response;
	}

	private VoiceTrafficTransactionalResponse removeCompleteVirtualOffshoreNumber(VoiceTrafficTransactionalRequest request, Connection transactionalConnection) throws Exception {

		VoiceTrafficTransactionalResponse response = new VoiceTrafficTransactionalResponse();
		Long virtualOffshoreNumberId = (Long) request.getRequestData();
		Integer responseData = this.removeCompleteVirtualOffshoreNumber(virtualOffshoreNumberId, transactionalConnection);
		response.setResponseData(responseData);
		response.setResponseType(responseData.getClass());

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

	/* CRUD Dids Methods */


		private VtDid loadDid(Long vtDidId, Connection controlledConnection) throws Exception{
			return loadDid(vtDidId, controlledConnection, true);
		}

		private VtDid loadDid(Long vtDidId, Connection controlledConnection, boolean isCrud) throws Exception{
			VtDidManager vtDidManager;

			if(controlledConnection!=null)
				vtDidManager= new VtDidManager(controlledConnection);
			else
				vtDidManager= new VtDidManager(this.dataSourceName);

			VtDid vtDidLoadObject = (VtDid) vtDidManager.load(vtDidId);
			if (vtDidLoadObject==null){
				throw new VoiceTrafficDidNotFoundException("Did " + vtDidId + " does not exists");
			}

			if(!isCrud){
				vtDidLoadObject.setSteps((List<VtDidStep>)loadDidSteps(vtDidId, controlledConnection, false));
			}

			return vtDidLoadObject;
		}

		private long addDid(VtDid vtDid, Connection controlledConnection) throws Exception{
			return addDid(vtDid, null, controlledConnection, true);
		}

		private long addDid(VtDid vtDid, VtCustomer vtCustomer, Connection controlledConnection, boolean isCrud) throws Exception{
			VtDidManager vtDidManager;

			if(controlledConnection!=null)
				vtDidManager= new VtDidManager(controlledConnection);
			else
				vtDidManager= new VtDidManager(this.dataSourceName);

			long vtDidId = vtDidManager.insertAutoIncrement(vtDid);
			vtDid.setId(vtDidId);

			if(!isCrud){
				if(vtCustomer == null)
					vtCustomer = loadCustomer(vtDid.getCustomerId(), controlledConnection);

				ItDid itDid = new ItDid();
				itDid.setExternalNumber(vtDid.getExternalNumber());
				itDid.setServiceFamily(ServiceFamily.VOICE_TRAFFIC);
				itDid.setServiceType(vtCustomer.getType());
				itDid.setForeignId(vtDidId);
				itDid.setId(-1);
				long itDidId = addItDid(itDid, controlledConnection);
				itDid.setId(itDidId);

				int stepNumber = 1;
				List<VtDidStep> vtDidSteps = vtDid.getSteps();
				int vtDidStepCount = vtDidSteps.size();
				for(int i=0; i<vtDidStepCount; i++){
					VtDidStep vtDidStep = vtDidSteps.get(i);
					this.addDidStep(vtDid, vtDidStep, stepNumber++, controlledConnection, false);
				} 
			}

			return vtDidId;
		}
		private int updateDid(VtDid vtDid, Connection controlledConnection) throws Exception{
			return updateDid(vtDid, null, controlledConnection, true);
		}
		private int updateDid(VtDid vtDid, VtCustomer vtCustomer, Connection controlledConnection, boolean isCrud) throws Exception{
			VtDidManager vtDidManager;

			if(controlledConnection!=null)
				vtDidManager= new VtDidManager(controlledConnection);
			else
				vtDidManager= new VtDidManager(this.dataSourceName);

			if(!isCrud){

				VtDidStepManager vtDidStepManager;

				if(controlledConnection!=null)
					vtDidStepManager= new VtDidStepManager(controlledConnection);
				else
					vtDidStepManager= new VtDidStepManager(this.dataSourceName);

				vtDidStepManager.delete("didId=" + vtDid.getId());

				VtDid oldDid = this.loadDid(vtDid.getId(), controlledConnection);
				//Remove extension
				RtExtensionManager rtExtensionManager;

				if(controlledConnection!=null)
					rtExtensionManager = new RtExtensionManager(controlledConnection);
				else
					rtExtensionManager = new RtExtensionManager(this.dataSourceName);

				rtExtensionManager.delete("exten = '" + oldDid.getExternalNumber() + "'");

				if(vtCustomer == null)
					vtCustomer = loadCustomer(vtDid.getCustomerId(), controlledConnection);

				ItDid itDid = new ItDid();
				itDid.setExternalNumber(vtDid.getExternalNumber());
				itDid.setServiceFamily(ServiceFamily.IP_PBX);
				itDid.setServiceType(vtCustomer.getType());
				itDid.setForeignId(vtDid.getId());
				updateItDid(itDid, controlledConnection);

				int stepNumber = 1;
				List<VtDidStep> newsVtDidSteps = vtDid.getSteps();
				int newVtDidStepCount = newsVtDidSteps.size();
				for(int i=0; i<newVtDidStepCount; i++){
					VtDidStep newVtDidStep = newsVtDidSteps.get(i);
					this.addDidStep(vtDid, newVtDidStep, stepNumber++, controlledConnection, false);
				}

			}

			int vtDidRowsUpdated = vtDidManager.update(vtDid, vtDid.getId());
			if (vtDidRowsUpdated == 0){
				throw new VoiceTrafficDidNotFoundException("Did " + vtDid.getId() + " does not exists");
			}
			return vtDidRowsUpdated;
		}

		private int removeDid(Long vtDidId, Connection controlledConnection) throws Exception{
			return this.removeDid(vtDidId, controlledConnection, true);
		}
		private int removeDid(Long vtDidId, Connection controlledConnection, boolean isCrud) throws Exception{
			VtDidManager vtDidManager;

			if(controlledConnection!=null)
				vtDidManager = new VtDidManager(controlledConnection);
			else
				vtDidManager= new VtDidManager(this.dataSourceName);

			if(!isCrud){
				//Remove steps
				VtDidStepManager vtDidStepManager;
				if(controlledConnection!=null)
					vtDidStepManager = new VtDidStepManager(controlledConnection);
				else
					vtDidStepManager= new VtDidStepManager(this.dataSourceName);

				vtDidStepManager.delete("didId = '" + vtDidId + "'");

				removeItDid(vtDidId, controlledConnection);

				//Remove extension
				RtExtensionManager rtExtensionManager;

				if(controlledConnection!=null)
					rtExtensionManager = new RtExtensionManager(controlledConnection);
				else
					rtExtensionManager = new RtExtensionManager(this.dataSourceName);

				VtDid vtDid = (VtDid) vtDidManager.load(vtDidId);

				rtExtensionManager.delete("exten = '" + vtDid.getExternalNumber() + "'");

			}

			int vtDidRowsDeleted = vtDidManager.delete(vtDidId);
			if(vtDidRowsDeleted == 0){
				throw new VoiceTrafficDidNotFoundException("Did " + vtDidId + " does not exists");
			}

			return vtDidRowsDeleted;
		}

		/* Private addDidStep Method*/
		private List<VtDidStep> loadDidSteps(Long vtDidId, Connection controlledConnection) throws Exception {
			return loadDidSteps(vtDidId, controlledConnection, true);
		}

		private List<VtDidStep> loadDidSteps(Long vtDidId, Connection controlledConnection, boolean isCrud) throws Exception {

			List<VtDidStep> vtDidSteps = new ArrayList<VtDidStep>();
			VtDidStepManager vtDidStepManager;

			if(controlledConnection!=null)
				vtDidStepManager= new VtDidStepManager(controlledConnection);
			else
				vtDidStepManager= new VtDidStepManager(this.dataSourceName);

			if(!isCrud){

				//Aditional non CRUD Mode
			}

			List<Object> steps = vtDidStepManager.list("didId=" + vtDidId);

			for (Object object : steps) {
				VtDidStep vtDidStep = (VtDidStep)object;
				vtDidSteps.add(vtDidStep);
			}

			return vtDidSteps;
		}
		private long addDidStep(VtDid vtDid, VtDidStep vtDidStep, int stepNumber, Connection controlledConnection, boolean isCrud) throws Exception {

			VtDidStepManager vtDidStepManager;

			if(controlledConnection!=null)
				vtDidStepManager= new VtDidStepManager(controlledConnection);
			else
				vtDidStepManager= new VtDidStepManager(this.dataSourceName);

			if(!isCrud){

				RtExtensionManager rtExtensionManager;
				if(controlledConnection!=null)
					rtExtensionManager= new RtExtensionManager(controlledConnection);
				else
					rtExtensionManager= new RtExtensionManager(this.dataSourceName);

				RtExtension rtExtension = new RtExtension();

				rtExtension.setContext("rt_"+VOICETRAFFIC_INCOMING_CONTEXT);
				rtExtension.setExten("" + vtDid.getExternalNumber());
				rtExtension.setPriority(stepNumber);

				switch(vtDidStep.getAction()){

				case FORWARD:
					String virtualOffShoreNumber = vtDidStep.getData();
					rtExtension.setApp("Macro");
					//FIXME borrar despues de probar ----> rtExtension.setAppdata("VOICE_TRAFFIC_FORWARD_CALL|" + virtualOffShoreNumber + "|" + vtCustomer.getType().name() + "|" + vtDid.getCustomerId());
					rtExtension.setAppdata("VOICE_TRAFFIC_FORWARD_CALL|" + virtualOffShoreNumber);
					
					break;
					
				case VOIP_FORWARD:
					String peerId = vtDidStep.getData();
					rtExtension.setApp("Macro");
					//FIXME borrar despues de probar ----> rtExtension.setAppdata("VOICE_TRAFFIC_VOIP_FORWARD_CALL|" + peerId + "|" + vtCustomer.getType().name() + "|" + vtDid.getCustomerId());
					rtExtension.setAppdata("VOICE_TRAFFIC_VOIP_FORWARD_CALL|" + peerId);
					break;

				}
				rtExtensionManager.insertAutoIncrement(rtExtension);

			}

			vtDidStep.setDidId(vtDid.getId());
			long vtDidStepNewId = vtDidStepManager.insertAutoIncrement(vtDidStep);
			vtDidStep.setId(vtDidStepNewId);
			
			return vtDidStepNewId;
		}  
		/* Private itDid Management Atomic Methods */
		private long addItDid(ItDid itDid, Connection controlledConnection) throws Exception{
			return addItDid(itDid, controlledConnection, true);
		}
		private long addItDid(ItDid itDid, Connection controlledConnection, boolean isCrud) throws Exception{
			ItDidManager itDidManager;

			if(controlledConnection!=null)
				itDidManager = new ItDidManager(controlledConnection);
			else
				itDidManager = new ItDidManager(this.dataSourceName);

			if(!isCrud){
				// Additional non CRUD code
			}

			long itDidId = itDidManager.insertAutoIncrement(itDid);
			return itDidId;
		}

		private int updateItDid(ItDid itDid, Connection controlledConnection) throws Exception{
			return updateItDid(itDid, controlledConnection, true);
		}
		private int updateItDid(ItDid itDid, Connection controlledConnection, boolean isCrud) throws Exception{
			ItDidManager itDidManager;

			if(controlledConnection!=null)
				itDidManager = new ItDidManager(controlledConnection);
			else
				itDidManager = new ItDidManager(this.dataSourceName);

			if(!isCrud){
				// Additional non CRUD code
			}

			List<Object> itDidId = itDidManager.listField("id", "foreignId=" + itDid.getForeignId() + " AND serviceFamily = '" + ServiceFamily.VOICE_TRAFFIC.name() + "'");
			BigInteger bigInteger = (BigInteger)itDidId.get(0);
			long oldItDidId = bigInteger.longValue();
			itDid.setId(oldItDidId);
			int itDidRowsUpdated = itDidManager.update(itDid, "id='" + oldItDidId +"'");
			return itDidRowsUpdated;
		}

		private int removeItDid(long vtDidId, Connection controlledConnection) throws Exception{
			return removeItDid(vtDidId, controlledConnection, true);
		}

		private int removeItDid(long vtDidId, Connection controlledConnection, boolean isCrud) throws Exception{
			ItDidManager itDidManager;

			if(controlledConnection!=null)
				itDidManager = new ItDidManager(controlledConnection);
			else
				itDidManager = new ItDidManager(this.dataSourceName);

			if(!isCrud){
				// Additional non CRUD code
			}

			int itDidRowsDeleted = itDidManager.delete("foreignId=" + vtDidId + " AND serviceFamily = '" + ServiceFamily.VOICE_TRAFFIC.name() + "'");
			return itDidRowsDeleted;
		}


	/* CRUD Customer Methods */	

	private VtCustomer loadCustomer(Long vtCustomerId, Connection controlledConnection) throws Exception{
		return loadCustomer(vtCustomerId, controlledConnection, true);
	}

	private VtCustomer loadCustomer(Long vtCustomerId, Connection controlledConnection, boolean isCrud) throws Exception{
		VtCustomer vtCustomerLoadObject = null;
		VtCustomerManager vtCustomerManager;

		if(controlledConnection!=null)
			vtCustomerManager= new VtCustomerManager(controlledConnection);
		else
			vtCustomerManager= new VtCustomerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		vtCustomerLoadObject = (VtCustomer)vtCustomerManager.load(vtCustomerId);

		if (vtCustomerLoadObject==null){
			throw new VoiceTrafficCustomerNotFoundException("Customer " + vtCustomerId + " does not exists");
		}
		return vtCustomerLoadObject;

	}

	private long addCustomer(VtCustomer vtCustomer, Connection controlledConnection) throws Exception{
		return addCustomer(vtCustomer, controlledConnection, true);
	}

	private long addCustomer(VtCustomer vtCustomer, Connection controlledConnection, boolean isCrud) throws Exception{

		VtCustomerManager vtCustomerManager;

		if(controlledConnection!=null)
			vtCustomerManager= new VtCustomerManager(controlledConnection);
		else
			vtCustomerManager= new VtCustomerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		long vtCustomerId = vtCustomerManager.insertAutoIncrement(vtCustomer);
		return vtCustomerId;
	}

	private int updateCustomer(VtCustomer vtCustomer, Connection controlledConnection) throws Exception {
		return updateCustomer(vtCustomer, controlledConnection, true);
	}

	private int updateCustomer(VtCustomer vtCustomer, Connection controlledConnection, boolean isCrud) throws Exception {

		VtCustomerManager vtCustomerManager;

		if(controlledConnection!=null)
			vtCustomerManager= new VtCustomerManager(controlledConnection);
		else
			vtCustomerManager= new VtCustomerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int vtCustomerUpdateRowsAffected = vtCustomerManager.update(vtCustomer, vtCustomer.getId());

		if (vtCustomerUpdateRowsAffected == 0){
			throw new VoiceTrafficCustomerNotFoundException("Customer " + vtCustomer.getId() + " does not exists");
		}
		return vtCustomerUpdateRowsAffected;
	}

	private int removeCustomer(Long vtCustomerId, Connection controlledConnection) throws Exception{
		return removeCustomer(vtCustomerId, controlledConnection, true);
	}

	private int removeCustomer(Long vtCustomerId, Connection controlledConnection, boolean isCrud) throws Exception{

		VtCustomerManager vtCustomerManager;

		if(controlledConnection!=null)
			vtCustomerManager = new VtCustomerManager(controlledConnection);
		else
			vtCustomerManager= new VtCustomerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int vtCustomerDeleteRowsAffected = vtCustomerManager.delete(vtCustomerId);
		if(vtCustomerDeleteRowsAffected == 0){
			throw new VoiceTrafficCustomerNotFoundException("Customer " + vtCustomerId + " does not exists");
		}

		return vtCustomerDeleteRowsAffected;
	}

	/* CRUD Peer Methods */

	private VtPeer loadPeer(Long vtPeerId, Connection controlledConnection) throws Exception{
		return loadPeer(vtPeerId, controlledConnection, true);
	}

	private VtPeer loadPeer(Long vtPeerId, Connection controlledConnection, boolean isCrud) throws Exception{

		VtPeerManager vtPeerManager;

		if(controlledConnection!=null)
			vtPeerManager= new VtPeerManager(controlledConnection);
		else
			vtPeerManager= new VtPeerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		VtPeer vtPeerLoadObject = (VtPeer)vtPeerManager.load(vtPeerId);
		if(vtPeerLoadObject==null){
			throw new VoiceTrafficCustomerNotFoundException("Peer " + vtPeerId + " does not exists");
		}
		return vtPeerLoadObject;
	}

	private long addPeer(VtPeer vtPeer, Connection controlledConnection) throws Exception {
		return addPeer(vtPeer, controlledConnection, true);
	}

	private long addPeer(VtPeer vtPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		VtPeerManager vtPeerManager;

		if(controlledConnection!=null)
			vtPeerManager= new VtPeerManager(controlledConnection);
		else
			vtPeerManager= new VtPeerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		long vtPeerId = vtPeerManager.insertAutoIncrement(vtPeer);
		vtPeer.setId(vtPeerId);
		
		return vtPeerId;

	}

	private int updatePeer(VtPeer vtPeer, Connection controlledConnection) throws Exception {
		return updatePeer(vtPeer, controlledConnection, true);
	}

	private int updatePeer(VtPeer vtPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		VtPeerManager vtPeerManager;

		if(controlledConnection!=null)
			vtPeerManager= new VtPeerManager(controlledConnection);
		else
			vtPeerManager= new VtPeerManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int vtPeerUpdateRowsAffected = vtPeerManager.update(vtPeer, vtPeer.getId());

		if (vtPeerUpdateRowsAffected == 0){
			throw new VoiceTrafficCustomerNotFoundException("Peer " + vtPeer.getId() + " does not exists");
		}

		return vtPeerUpdateRowsAffected;

	}

	private int removePeer(Long vtPeerId, Connection controlledConnection) throws Exception{
		return removePeer(vtPeerId, controlledConnection, true);
	}

	private int removePeer(Long vtPeerId, Connection controlledConnection, boolean isCrud) throws Exception{

		VtPeerManager vtPeerManager;

		if(controlledConnection!=null)
			vtPeerManager= new VtPeerManager(controlledConnection);
		else
			vtPeerManager= new VtPeerManager(this.dataSourceName);

		int vtPeerDeleteRowsAffected = vtPeerManager.delete(vtPeerId);

		if(!isCrud){
			// Additional non CRUD code
		}

		if(vtPeerDeleteRowsAffected==0){
			throw new VoiceTrafficCustomerNotFoundException("Peer " + vtPeerId + " does not exists");
		}

		return vtPeerDeleteRowsAffected;
	}

	/* CRUD VirtualOffshoreNumber Methods */	

	private VtVirtualOffshoreNumber loadVirtualOffshoreNumber(Long vtVirtaulOffshoreNumberId, Connection controlledConnection) throws Exception{
		return loadVirtualOffshoreNumber(vtVirtaulOffshoreNumberId, controlledConnection, true);
	}

	private VtVirtualOffshoreNumber loadVirtualOffshoreNumber(Long vtVirtaulOffshoreNumberId, Connection controlledConnection, boolean isCrud) throws Exception{

		VtVirtualOffshoreNumber vtVirtualOffshoreNumber = null;
		VtVirtualOffshoreNumberManager vtVirtualOffshoreNumberManager;

		if(controlledConnection!=null)
			vtVirtualOffshoreNumberManager= new VtVirtualOffshoreNumberManager(controlledConnection);
		else
			vtVirtualOffshoreNumberManager= new VtVirtualOffshoreNumberManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		vtVirtualOffshoreNumber = (VtVirtualOffshoreNumber)vtVirtualOffshoreNumberManager.load(vtVirtaulOffshoreNumberId);

		if (vtVirtualOffshoreNumber==null){
			throw new VoiceTrafficCustomerNotFoundException("VirtualOffshoreNumber " + vtVirtaulOffshoreNumberId + " does not exists");
		}
		return vtVirtualOffshoreNumber;

	}

	private long addVirtualOffshoreNumber(VtVirtualOffshoreNumber vtVirtualOffshoreNumber, Connection controlledConnection) throws Exception {
		return addVirtualOffshoreNumber(vtVirtualOffshoreNumber, controlledConnection, true);
	}

	private long addVirtualOffshoreNumber(VtVirtualOffshoreNumber vtVirtualOffshoreNumber, Connection controlledConnection, boolean isCrud) throws Exception {

		VtVirtualOffshoreNumberManager vtVirtualOffshoreNumberManager;

		if(controlledConnection!=null)
			vtVirtualOffshoreNumberManager = new VtVirtualOffshoreNumberManager(controlledConnection);
		else
			vtVirtualOffshoreNumberManager = new VtVirtualOffshoreNumberManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		long vtVirtualOffshoreNumberId = vtVirtualOffshoreNumberManager.insertAutoIncrement(vtVirtualOffshoreNumber);
		vtVirtualOffshoreNumber.setId(vtVirtualOffshoreNumberId);
		
		return vtVirtualOffshoreNumberId;

	}

	private int updateVirtualOffshoreNumber(VtVirtualOffshoreNumber vtVirtaulOffshoreNumber, Connection controlledConnection) throws Exception {
		return updateVirtualOffshoreNumber(vtVirtaulOffshoreNumber, controlledConnection, true);
	}

	private int updateVirtualOffshoreNumber(VtVirtualOffshoreNumber vtVirtaulOffshoreNumber, Connection controlledConnection, boolean isCrud) throws Exception {

		VtVirtualOffshoreNumberManager vtVirtualOffshoreNumberManager;

		if(controlledConnection!=null)
			vtVirtualOffshoreNumberManager= new VtVirtualOffshoreNumberManager(controlledConnection);
		else
			vtVirtualOffshoreNumberManager= new VtVirtualOffshoreNumberManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int vtVirtualOffshoreNumber = vtVirtualOffshoreNumberManager.update(vtVirtaulOffshoreNumber, vtVirtaulOffshoreNumber.getId());

		if (vtVirtualOffshoreNumber == 0)
			throw new VoiceTrafficCustomerNotFoundException("VirtualOffshoreNumber " + vtVirtaulOffshoreNumber.getId() + " does not exists");
		
		return vtVirtualOffshoreNumber;
	}

	private int removeVirtualOffshoreNumber(Long vtVirtualOffshoreNumberId, Connection controlledConnection) throws Exception{
		return removeVirtualOffshoreNumber(vtVirtualOffshoreNumberId, controlledConnection, true);
	}

	private int removeVirtualOffshoreNumber(Long vtVirtualOffshoreNumberId, Connection controlledConnection, boolean isCrud) throws Exception{
		VtVirtualOffshoreNumberManager vtVirtualOffshoreNumberManager;

		if(controlledConnection!=null)
			vtVirtualOffshoreNumberManager = new VtVirtualOffshoreNumberManager(controlledConnection);
		else
			vtVirtualOffshoreNumberManager= new VtVirtualOffshoreNumberManager(this.dataSourceName);

		if(!isCrud){
			// Additional non CRUD code
		}

		int vtVirtualOffshoreNumberDelete = vtVirtualOffshoreNumberManager.delete(vtVirtualOffshoreNumberId);
		if(vtVirtualOffshoreNumberDelete == 0){
			throw new VoiceTrafficCustomerNotFoundException("VirtualOffshoreNumber " + vtVirtualOffshoreNumberId + " does not exists");
		}

		return vtVirtualOffshoreNumberDelete;
	}

	/* CRUD IAXPeer y SIPPeer Methods */

	private long addIaxPeer(VtCustomer vtCustomer, VtPeer vtPeer, Connection controlledConnection) throws Exception {
		return addIaxPeer(vtCustomer, vtPeer, controlledConnection, true);
	}

	private long addIaxPeer(VtCustomer vtCustomer, VtPeer vtPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		RtIaxPeerManager rtIaxPeerManager = new RtIaxPeerManager(controlledConnection);
		RtIaxPeer rtIaxPeer = createIaxPeer(vtCustomer, vtPeer);

		if(!isCrud){
			// Additional non CRUD code
		}

		long newIaxPeerId = rtIaxPeerManager.insertAutoIncrement(rtIaxPeer);
		rtIaxPeer.setId(newIaxPeerId);
		return newIaxPeerId;
	}

	private int updateIaxPeer(VtCustomer vtCustomer, VtPeer vtPeer, Connection controlledConnection) throws Exception {
		return updateIaxPeer(vtCustomer, vtPeer, controlledConnection, true);
	}

	private int updateIaxPeer(VtCustomer vtCustomer, VtPeer vtPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		RtIaxPeerManager rtIaxPeerManager = new RtIaxPeerManager(controlledConnection);
		if(!isCrud){
			// Additional non CRUD code
		}
		return rtIaxPeerManager.update(createIaxPeer(vtCustomer, vtPeer), "itPeerId=" + vtPeer.getId() + " AND itPeerType='" + ItPeerType.VT_PEER.name() + "'");
	}

	private int removeIaxPeer(long vtPeerId, Connection controlledConnection) throws Exception {
		return removeIaxPeer(vtPeerId, controlledConnection, true);
	}

	private int removeIaxPeer(long vtPeerId, Connection controlledConnection , boolean isCrud) throws Exception {

		RtIaxPeerManager rtIaxPeerManager = new RtIaxPeerManager(controlledConnection);
		if(!isCrud){
			// Additional non CRUD code
		}
		return rtIaxPeerManager.delete("itPeerId=" + vtPeerId + " AND itPeerType='" + ItPeerType.VT_PEER.name() + "'");

	}

	private RtIaxPeer createIaxPeer(VtCustomer vtCustomer, VtPeer vtPeer) throws Exception {
		return createIaxPeer(vtCustomer, vtPeer, true);
	}

	private RtIaxPeer createIaxPeer(VtCustomer vtCustomer, VtPeer vtPeer, boolean isCrud) throws Exception {

		RtIaxPeer rtIaxPeer = new RtIaxPeer();

		rtIaxPeer.setAccountcode(InteraxTelephonyCdrFormater.formatCdrAccountCode(ServiceFamily.VOICE_TRAFFIC, vtCustomer.getEnterpriseId()));
		rtIaxPeer.setAllow("g729");
		if(vtPeer.getEnabled()){
			rtIaxPeer.setContext(VOICETRAFFIC_BASE_CONTEXT_PREFIX + vtCustomer.getContextSuffix()); 
		}else{
			rtIaxPeer.setContext(VOICETRAFFIC_DISABLED_CONTEXT_PREFIX + vtCustomer.getContextSuffix()); 
		}

		rtIaxPeer.setDisallow("all");
		rtIaxPeer.setItPeerId(vtPeer.getId());
		rtIaxPeer.setItPeerType(ItPeerType.VT_PEER);
		rtIaxPeer.setLanguage(vtPeer.getLanguage());
		rtIaxPeer.setName(VOICETRAFFIC_IAX_PEER_PREFIX + vtPeer.getId()); 
		rtIaxPeer.setQualify("no");
		rtIaxPeer.setSecret(vtPeer.getSecret());
		rtIaxPeer.setType("peer");
		if(vtPeer.getLogin() != null){
				rtIaxPeer.setUsername(vtPeer.getLogin()); 
		}else{
				rtIaxPeer.setUsername(VOICETRAFFIC_IAX_PEER_PREFIX + vtPeer.getId()); 
		}
		
		rtIaxPeer.setUserfield(InteraxTelephonyCdrFormater.formatCdrUserField(VoiceTrafficAccessType.OUTGOING, vtCustomer.getType(), vtCustomer.getId(), vtPeer.getId(), "IAX")); 
		//TODO ServiceType

		// default fields
		rtIaxPeer.setAmaflags(null);
		rtIaxPeer.setAuth(null);
		rtIaxPeer.setDbsecret(null);
		rtIaxPeer.setDefaultip(null);
		rtIaxPeer.setDeny(null);
		rtIaxPeer.setHost(vtPeer.getHost());
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

	private long addSipPeer(VtCustomer vtCustomer, VtPeer vtPeer, Connection controlledConnection) throws Exception {
		return addSipPeer(vtCustomer, vtPeer, controlledConnection, true);
	}

	private long addSipPeer(VtCustomer vtCustomer, VtPeer vtPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		RtSipPeerManager rtSipPeerManager = new RtSipPeerManager(controlledConnection);
		RtSipPeer rtSipPeer = createSipPeer(vtCustomer, vtPeer);

		if(!isCrud){
			// Additional non CRUD code
		}

		long newSipPeerId = rtSipPeerManager.insertAutoIncrement(rtSipPeer);
		rtSipPeer.setId(newSipPeerId);
		return newSipPeerId;
	}

	private long updateSipPeer(VtCustomer vtCustomer, VtPeer vtPeer, Connection controlledConnection) throws Exception {
		return updateSipPeer(vtCustomer, vtPeer, controlledConnection, true);
	}

	private long updateSipPeer(VtCustomer vtCustomer, VtPeer vtPeer, Connection controlledConnection, boolean isCrud) throws Exception {

		RtSipPeerManager rtSipPeerManager = new RtSipPeerManager(controlledConnection);

		if(!isCrud){
			return rtSipPeerManager.update(createSipPeer(vtCustomer, vtPeer), vtPeer.getId() + "' AND itPeerType='" + ItPeerType.VT_PEER.name(), false);
		}
		else{
			return rtSipPeerManager.update(createSipPeer(vtCustomer, vtPeer), vtPeer.getId() + " AND itPeerType='" + ItPeerType.VT_PEER.name());
		}

	}

	private int removeSipPeer(long vtPeerId, Connection controlledConnection) throws Exception {
		return removeSipPeer(vtPeerId, controlledConnection, true);
	}

	private int removeSipPeer(long vtPeerId, Connection controlledConnection, boolean isCrud) throws Exception {

		RtSipPeerManager rtSipPeerManager = new RtSipPeerManager(controlledConnection);
		if(!isCrud){
			// Additional non CRUD code
		}
		return rtSipPeerManager.delete("itPeerId=" + vtPeerId + " AND itPeerType='" + ItPeerType.VT_PEER.name() + "'");

	}

	private RtSipPeer createSipPeer(VtCustomer vtCustomer, VtPeer vtPeer){
		return createSipPeer(vtCustomer, vtPeer, true);
	}

	private RtSipPeer createSipPeer(VtCustomer vtCustomer, VtPeer vtPeer, boolean isCrud){
		RtSipPeer rtSipPeer = new RtSipPeer();

		rtSipPeer.setAccountcode(InteraxTelephonyCdrFormater.formatCdrAccountCode(ServiceFamily.VOICE_TRAFFIC, vtCustomer.getEnterpriseId()));
		rtSipPeer.setAllow("g729");
		//rtSipPeer.setCallerid(vtPeer.getId() + "<" + vtPeer.getInternalNumber() + ">");
		rtSipPeer.setCallLimit(0);
		if(vtPeer.getEnabled()){
			rtSipPeer.setContext(VOICETRAFFIC_BASE_CONTEXT_PREFIX + vtCustomer.getContextSuffix()); 
		}else{
			rtSipPeer.setContext(VOICETRAFFIC_DISABLED_CONTEXT_PREFIX + vtCustomer.getContextSuffix()); 
		}
		rtSipPeer.setDisallow("all");
		rtSipPeer.setHost(vtPeer.getHost());
		rtSipPeer.setItPeerId(vtPeer.getId());
		rtSipPeer.setItPeerType(ItPeerType.VT_PEER);
		rtSipPeer.setLanguage(vtPeer.getLanguage());		
		rtSipPeer.setPort(5060);
		rtSipPeer.setQualify("no");
		rtSipPeer.setRegseconds(0);
		rtSipPeer.setSecret(vtPeer.getSecret());
		rtSipPeer.setType("peer");
		
		if(vtPeer.getLogin() != null){
			rtSipPeer.setUsername(vtPeer.getLogin());
			rtSipPeer.setName(vtPeer.getLogin());
			rtSipPeer.setInsecure("no");
		}else{
			rtSipPeer.setUsername(VOICETRAFFIC_SIP_PEER_PREFIX + vtPeer.getId());
			rtSipPeer.setName(VOICETRAFFIC_SIP_PEER_PREFIX + vtPeer.getId());
			rtSipPeer.setInsecure("very");
		}
		
		rtSipPeer.setUserfield(InteraxTelephonyCdrFormater.formatCdrUserField(VoiceTrafficAccessType.OUTGOING, vtCustomer.getType(), vtCustomer.getId(), vtPeer.getId(), "SIP")); 

		// default fields
		rtSipPeer.setDtmfmode("rfc2833");	
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

	/* Complete Customer Methods */


	/* Complete Customer Methods */

	private VtCustomer loadCompleteCustomer(Long customerId, Connection transactionalConnection) throws VoiceTrafficException{

		Connection controlledConnection = null;
		boolean transactionalCall = (transactionalConnection != null);

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			// Load the Customer

			VtCustomer vtCustomer = loadCustomer(customerId, controlledConnection, false);

			List<VtPeer> vtPeers = loadCompletePeers(customerId, controlledConnection);

			List<VtDid> vtDids = new ArrayList<VtDid>();
			List<VtVirtualOffshoreNumber> vtVirtualOffshoreNumbers = new ArrayList<VtVirtualOffshoreNumber>();

			List<HashMap<String, Object>> virtualOffshoreNumbers = loadCompleteVirtualOffshoreNumbers(customerId, transactionalConnection);

			for (HashMap<String, Object> hashtable : virtualOffshoreNumbers) {
				VtDid vtDid = (VtDid)hashtable.get(VtDid.class.getSimpleName());
				VtVirtualOffshoreNumber vtVirtualOffshoreNumber = (VtVirtualOffshoreNumber)hashtable.get(VtVirtualOffshoreNumber.class.getSimpleName());
				vtDids.add(vtDid);
				vtVirtualOffshoreNumbers.add(vtVirtualOffshoreNumber);
			}

			vtCustomer.setPeers(vtPeers);
			vtCustomer.setDids(vtDids);
			vtCustomer.setVons(vtVirtualOffshoreNumbers);

			return vtCustomer;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}

	}


	private HashMap<String, Object> addCompleteCustomer(VtCustomer vtCustomer, Connection transactionalConnection) throws VoiceTrafficException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			// Identify the enterprise
			ItEnterpriseManager itEnterpriseManager = new ItEnterpriseManager(controlledConnection);
			ItEnterprise itEnterprise = (ItEnterprise) itEnterpriseManager.load(vtCustomer.getEnterpriseId());

			// Add the Customer
			Long newCustomerId = this.addCustomer(vtCustomer, controlledConnection, false);
			vtCustomer.setId(newCustomerId);
			String customerContextSuffix = itEnterprise.getContextSuffix() + "_" + newCustomerId;
			vtCustomer.setContextSuffix(customerContextSuffix);

			VtCustomerManager vtCustomerManager = new VtCustomerManager(controlledConnection);
			HashMap<String, Object> keyPairs = new HashMap<String, Object>();
			keyPairs.put("contextSuffix", customerContextSuffix);
			vtCustomerManager.update(keyPairs, newCustomerId);

			// Add the peers
			List<VtPeer> vtPeers = vtCustomer.getPeers();
			HashMap<Long, Long> vtPeersIdsTable = new HashMap<Long,Long>();
			
			for (VtPeer vtPeer : vtPeers) {
				long oldVtPeerId = vtPeer.getId();
				vtPeer.setCustomerId(newCustomerId);
				HashMap<String, Object> responseData = this.addCompletePeer(vtPeer, vtCustomer, controlledConnection);
				VtPeer vtPeerNew = (VtPeer)responseData.get(VtPeer.class.getSimpleName());
				long newVtPeerId = vtPeerNew.getId();
				vtPeersIdsTable.put(oldVtPeerId, newVtPeerId);
			}

			correlativeIds.put(VtPeer.class.getSimpleName(), vtPeersIdsTable);
			
			List<VtDid> dids = vtCustomer.getDids();
			HashMap<Long,VtDid> didsHash = new HashMap<Long, VtDid>();

			for (VtDid did : dids) {
				didsHash.put(did.getId(), did);
			}

			HashMap<Long, Long> didIdsTable = new HashMap<Long,Long>();
			
			
			//Add the VONs
			List<VtVirtualOffshoreNumber> vtVons = vtCustomer.getVons();
			HashMap<Long, Long> vonIdsTable = new HashMap<Long,Long>();
			//correlativeIds.put(VtVirtualOffshoreNumberManager.class.getSimpleName(), vonIdsTable);

			for (VtVirtualOffshoreNumber von : vtVons) {
				long oldVonId = von.getId();
				von.setCustomerId(newCustomerId);			
				VtDid did = didsHash.get(von.getDidId());
				long oldDidId = did.getId();
				did.setCustomerId(newCustomerId);
				HashMap<String, Object> vonAded = this.addCompleteVirtualOffshoreNumber(von, did, controlledConnection);
				VtDid newDid = (VtDid) vonAded.get(VtDid.class.getSimpleName());
				VtVirtualOffshoreNumber newVon = (VtVirtualOffshoreNumber) vonAded.get(VtVirtualOffshoreNumber.class.getSimpleName());
				vonIdsTable.put(oldVonId, newVon.getId());
				didIdsTable.put(oldDidId, newDid.getId());			
			}

			correlativeIds.put(VtDid.class.getSimpleName(), didIdsTable);
			correlativeIds.put(VtVirtualOffshoreNumber.class.getSimpleName(), vonIdsTable);
		
			response.put(vtCustomer.getClass().getSimpleName(), vtCustomer);
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

	private HashMap<String, Object> updateCompleteCustomer(VtCustomer vtCustomer, Connection transactionalConnection) throws VoiceTrafficException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			// CorrelativeIds
			HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
			
			// Update the peers
			HashMap<Long, Long> peerIdsTable = new HashMap<Long,Long>();
			List<VtPeer> vtPeers = new ArrayList<VtPeer>();
			
			VtCustomer vtCustomerOld = this.loadCompleteCustomer(vtCustomer.getId(), controlledConnection); 
			VtCustomer vtCustomerNew = vtCustomer;
			vtCustomerNew.setContextSuffix(vtCustomerOld.getContextSuffix());
			List<Long> vtCustomerOldIds = new ArrayList<Long>();

			for (VtPeer vtPeerOld : vtCustomerOld.getPeers()) {
				vtCustomerOldIds.add(vtPeerOld.getId());
			}
			
			for (Long vtPeerId : vtCustomerOldIds) {
				this.removeCompletePeer(vtPeerId, controlledConnection);
			}

			for (VtPeer vtPeerNew : vtCustomerNew.getPeers()) {
				if(vtCustomerOldIds.contains(vtPeerNew.getId())){
					this.updateCompletePeer(vtPeerNew, vtCustomerNew, controlledConnection);
					vtCustomerOldIds.remove(vtPeerNew.getId()); //REMOVE ID
				}
				else{
					if(vtPeerNew.getId()>0){
						throw new VoiceTrafficPeerNotFoundException("Peer " + vtPeerNew.getId() + " not found in the DataBase");
					}
					long vtPeerOldId = vtPeerNew.getId();
					HashMap<String, Object> responseData = this.addCompletePeer(vtPeerNew, vtCustomerNew, controlledConnection);
					vtPeers.add((VtPeer)responseData.get(VtPeer.class.getSimpleName())); 
					long vtPeerNewId = vtPeerNew.getId();
					peerIdsTable.put(vtPeerOldId, vtPeerNewId);
				}
			}

			correlativeIds.put(VtPeer.class.getSimpleName(), peerIdsTable);
			vtCustomerNew.setPeers(vtPeers);
			
			List<VtDid> dids = vtCustomer.getDids();
			HashMap<Long,VtDid> didsHash = new HashMap<Long, VtDid>();
			List<VtDid> vtDids = new ArrayList<VtDid>();
			
			for (VtDid did : dids) {
				didsHash.put(did.getId(), did);
			}

			HashMap<Long, Long> didIdsTable = new HashMap<Long,Long>();
			

			//Update the Vons

			HashMap<Long, Long> vonIdsTable = new HashMap<Long,Long>();
			List<VtVirtualOffshoreNumber> vtVirtualOffshoreNumbers = new ArrayList<VtVirtualOffshoreNumber>();
			vtCustomerOldIds = new ArrayList<Long>();

			for (VtVirtualOffshoreNumber vtVonOld : vtCustomerOld.getVons()) {
				vtCustomerOldIds.add(vtVonOld.getId());
			}

			for (VtVirtualOffshoreNumber von : vtCustomerNew.getVons()) {
				VtDid did = didsHash.get(von.getDidId());
				if(vtCustomerOldIds.contains(von.getId())){
					this.updateCompleteVirtualOffshoreNumber(von, did, controlledConnection);
					vtCustomerOldIds.remove(von.getId()); //REMOVE ID
				}
				else{
					if(von.getId()>0){
						throw new VoiceTrafficVirtualOffshoreNumberNotFoundException("VON " + von.getId() + " not found in the DataBase");
					}
					long oldVonId = von.getId();
					long oldDidId = did.getId();
					HashMap<String, Object> vonAded = this.addCompleteVirtualOffshoreNumber(von, did, controlledConnection);
					VtDid newDid = (VtDid) vonAded.get(VtDid.class.getSimpleName());
					VtVirtualOffshoreNumber newVon = (VtVirtualOffshoreNumber) vonAded.get(VtVirtualOffshoreNumber.class.getSimpleName());
					
					vtVirtualOffshoreNumbers.add(newVon);
					vtDids.add(newDid);
					
					vonIdsTable.put(oldVonId, newVon.getId());
					didIdsTable.put(oldDidId, newDid.getId());
				}
			}

			for (Long vonId : vtCustomerOldIds) {
				this.removeCompleteVirtualOffshoreNumber(vonId, controlledConnection);
			}

			// Update Customer

			vtCustomerNew.setDids(vtDids);
			vtCustomerNew.setVons(vtVirtualOffshoreNumbers);
			
			this.updateCustomer(vtCustomerNew, controlledConnection, false);
	
			correlativeIds.put(VtDid.class.getSimpleName(), didIdsTable);
			correlativeIds.put(VtVirtualOffshoreNumberManager.class.getSimpleName(), vonIdsTable);
			
			response.put(VtCustomer.class.getSimpleName(), vtCustomerNew);
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

	private int removeCompleteCustomer(Long vtCustomerId, Connection transactionalConnection) throws VoiceTrafficException {

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			VtCustomerManager vtCustomerManager =  new VtCustomerManager(controlledConnection);

			VtCustomer vtCustomer = (VtCustomer) this.loadCompleteCustomer(vtCustomerId, controlledConnection);

			if(vtCustomer==null){
				throw new VoiceTrafficCustomerNotFoundException("Customer " + vtCustomerId + " not found");
			}

			//DELETE Customer
			HashMap<String, Object> keyPairs = new HashMap<String, Object>();
			keyPairs.put("deleted", true);
			int customerRows = vtCustomerManager.update(keyPairs, vtCustomerId);

			//DELETE Peers
			List<VtPeer> vtPeers = vtCustomer.getPeers();

			for (VtPeer vtPeer : vtPeers) {
				this.removeCompletePeer(vtPeer.getId(), controlledConnection);
			}

			List<VtDid> dids = vtCustomer.getDids();
			HashMap<Long,VtDid> didsHash = new HashMap<Long, VtDid>();

			for (VtDid did : dids) {
				didsHash.put(did.getId(), did);
			}

			// Delete VONs
			List<VtVirtualOffshoreNumber> vtVons = vtCustomer.getVons();

			for (VtVirtualOffshoreNumber von : vtVons) {
				Long vonId = von.getId();
				this.removeCompleteVirtualOffshoreNumber(vonId, controlledConnection);
			}

			if(!transactionalCall) controlledConnection.commit();

			return customerRows;

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




	/* Complete Peer Methods */

	private VtPeer loadCompletePeer(Long vtPeerId, Connection transactionalConnection) throws VoiceTrafficException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			VtPeer vtPeer = loadPeer(vtPeerId, controlledConnection, false);

			return vtPeer;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}

	private List<VtPeer> loadCompletePeers(Long customerId, Connection transactionalConnection) throws VoiceTrafficException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			List<VtPeer> peers = new ArrayList<VtPeer>();

			VtPeerManager vtPeerManager = new VtPeerManager(controlledConnection);;
			List<Object> peersObjectIds = vtPeerManager.listField("id", "customerId=" + customerId);

			for (Object object : peersObjectIds) {
				BigInteger bigInteger = (BigInteger) object;
				long peerId = bigInteger.longValue();
				VtPeer peer = loadPeer(peerId, controlledConnection, false);
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


	private HashMap<String,Object> addCompletePeer(VtPeer vtPeer, VtCustomer vtCustomer, Connection transactionalConnection) throws VoiceTrafficException {
		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
		HashMap<Long, Long> vtIaxPeerIdsTable = new HashMap<Long, Long>();
		HashMap<Long, Long> vtSipPeerIdsTable = new HashMap<Long, Long>();
		HashMap<Long, Long> vtPeerIdsTable = new HashMap<Long, Long>();
		
		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			long vtPeerOldId = vtPeer.getId();
		    
			if(vtPeer.getSecret() == null)
		    	vtPeer.setSecret(generateRandomPeerSecret());
		   

			long vtPeerNewId = addPeer(vtPeer, controlledConnection, false);

			vtPeerIdsTable.put(vtPeerOldId, vtPeerNewId);
			
			if(vtPeer.getHasIax()){
				long vtIaxPeerId = this.addIaxPeer(vtCustomer, vtPeer, controlledConnection, false);
				vtIaxPeerIdsTable.put(vtIaxPeerId, vtIaxPeerId);
			}

			if(vtPeer.getHasSip()){
				long vtSipPeerId = this.addSipPeer(vtCustomer, vtPeer, controlledConnection, false);
				vtSipPeerIdsTable.put(vtSipPeerId, vtSipPeerId);
			}

			correlativeIds.put(RtIaxPeer.class.getSimpleName(), vtIaxPeerIdsTable);
			correlativeIds.put(RtSipPeer.class.getSimpleName(), vtSipPeerIdsTable);
			correlativeIds.put(VtPeer.class.getSimpleName(), vtPeerIdsTable);
			
			response.put(VtPeer.class.getSimpleName(), vtPeer);
			response.put(CORRELATIVE_IDS_KEY, correlativeIds);
			
			if(!transactionalCall) controlledConnection.commit();

			return response;
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

	private HashMap<String,Object> updateCompletePeer(VtPeer vtPeer, VtCustomer vtCustomer, Connection transactionalConnection) throws VoiceTrafficException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
		HashMap<Long, Long> vtIaxPeerIdsTable = new HashMap<Long, Long>();
		HashMap<Long, Long> vtSipPeerIdsTable = new HashMap<Long, Long>();
		HashMap<Long, Long> vtPeerIdsTable = new HashMap<Long, Long>();
		
		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			VtPeer oldVtPeer = this.loadPeer(vtPeer.getId(), controlledConnection, false); 

			if (oldVtPeer == null)
				throw new VoiceTrafficPeerNotFoundException("Peer " +vtPeer.getId()+ "does not exist" );

			//vtPeer.setSecret(oldVtPeer.getSecret());			

			this.updatePeer(vtPeer, controlledConnection, false);

			if(oldVtPeer.getHasIax() == vtPeer.getHasIax()){
				this.updateIaxPeer(vtCustomer, vtPeer, controlledConnection, false);
			}else if(vtPeer.getHasIax()){
				long vtIaxPeerId = this.addIaxPeer(vtCustomer, vtPeer, controlledConnection, false);
				vtIaxPeerIdsTable.put(vtIaxPeerId, vtIaxPeerId);
			}else{
				this.removeIaxPeer(oldVtPeer.getId(), controlledConnection, false);
			}

			if(oldVtPeer.getHasSip() == vtPeer.getHasSip()){
				this.updateSipPeer(vtCustomer, vtPeer, controlledConnection, false);
			}else if(vtPeer.getHasSip()){
				long vtSipPeerId = this.addSipPeer(vtCustomer, vtPeer, controlledConnection, false);
				vtSipPeerIdsTable.put(vtSipPeerId, vtSipPeerId);
			}else{
				this.removeSipPeer(oldVtPeer.getId(), controlledConnection, false);
			}

			correlativeIds.put(RtIaxPeer.class.getSimpleName(), vtIaxPeerIdsTable);
			correlativeIds.put(RtSipPeer.class.getSimpleName(), vtSipPeerIdsTable);
			correlativeIds.put(VtPeer.class.getSimpleName(), vtPeerIdsTable);
			
			response.put(VtPeer.class.getSimpleName(), vtPeer);
			response.put(CORRELATIVE_IDS_KEY, correlativeIds);
			
			if(!transactionalCall) controlledConnection.commit();

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


	private int removeCompletePeer(Long peerId, Connection transactionalConnection) throws VoiceTrafficException{

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			VtPeerManager vtPeerManager =  new VtPeerManager(controlledConnection);

			VtPeer vtPeer = (VtPeer) vtPeerManager.load(peerId);
			if(vtPeer==null){
				throw new VoiceTrafficPeerNotFoundException("Peer " + peerId + " not found");
			}

			HashMap<String, Object> keyPairs = new HashMap<String, Object>();
			keyPairs.put("deleted", true);
			int peerRows = vtPeerManager.update(keyPairs, peerId);

			if(vtPeer.getHasIax()){
				this.removeIaxPeer(peerId, controlledConnection, false);
			}

			if(vtPeer.getHasSip()){
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

	/* Complete VirtualOffshore Methods */


	/* Complete VirtualOffshore Methods */

	private HashMap<String, Object> loadCompleteVirtualOffshoreNumber(Long virtualOffShoreNumberId, Connection transactionalConnection) throws VoiceTrafficException {

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();

		try{
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			// Load the VirtualOffshoreNumber
			VtVirtualOffshoreNumber vtVirtualOffshoreNumber = loadVirtualOffshoreNumber(virtualOffShoreNumberId, controlledConnection, false);

			VtDid vtDid = loadDid(vtVirtualOffshoreNumber.getDidId(), controlledConnection, false);

			response.put(VtVirtualOffshoreNumber.class.getSimpleName(), vtVirtualOffshoreNumber);
			response.put(VtDid.class.getSimpleName(), vtDid);
			return response;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}


	private List<HashMap<String, Object>> loadCompleteVirtualOffshoreNumbers(Long customerId, Connection transactionalConnection) throws VoiceTrafficException {
		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		List<HashMap<String, Object>> response = new ArrayList<HashMap<String,Object>>();

		try{
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();


			VtVirtualOffshoreNumberManager virtualOffshoreNumberManager = new VtVirtualOffshoreNumberManager(controlledConnection);
			List<Object> virtualOffshoreNumberObjectIds = virtualOffshoreNumberManager.listField("id", "customerId=" + customerId);

			for (Object object : virtualOffshoreNumberObjectIds) {
				BigInteger bigInteger = (BigInteger) object;
				long virtualOffshoreNumberId = bigInteger.longValue();
				HashMap<String,Object> virtualOffshoreNumber = loadCompleteVirtualOffshoreNumber(virtualOffshoreNumberId, controlledConnection);
				response.add(virtualOffshoreNumber);
			}

			return response;
		}catch (Exception e) {
			throw this.manageException(e);
		}finally{
			if(!transactionalCall)
				this.closeConecction(controlledConnection);
		}
	}

	private HashMap<String,Object> addCompleteVirtualOffshoreNumber(VtVirtualOffshoreNumber vtVirtualOffshoreNumber, VtDid vtDid, Connection transactionalConnection) throws VoiceTrafficException {
		
		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();
		HashMap<String, HashMap<Long, Long>> correlativeIds = new HashMap<String, HashMap<Long,Long>>();
		HashMap<Long, Long> vtVirtualOffshoreNumberIdsTable = new HashMap<Long,Long>();
		HashMap<Long, Long> vtDidIdsTable = new HashMap<Long,Long>();


		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			//Add Did
			vtDid.setForeignId(vtVirtualOffshoreNumber.getCustomerId()); 

			long vtDidOldId = vtDid.getId();
			long vtDidNewId = this.addDid(vtDid, null, controlledConnection, false);
			vtDidIdsTable.put(vtDidOldId, vtDidNewId);
			vtVirtualOffshoreNumber.setDidId(vtDidNewId);
			
			// Add VON
			long vtVirtualOffshoreNumberOldId = vtVirtualOffshoreNumber.getId();
			long vtVirtualOffshoreNumberNewId = this.addVirtualOffshoreNumber(vtVirtualOffshoreNumber, controlledConnection, false);
			vtVirtualOffshoreNumberIdsTable.put(vtVirtualOffshoreNumberOldId, vtVirtualOffshoreNumberNewId);

			correlativeIds.put(VtVirtualOffshoreNumber.class.getSimpleName(), vtVirtualOffshoreNumberIdsTable);
			correlativeIds.put(VtDid.class.getSimpleName(), vtDidIdsTable);

			response.put(VtVirtualOffshoreNumber.class.getSimpleName(), vtVirtualOffshoreNumber);
			response.put(VtDid.class.getSimpleName(), vtDid);
			response.put(CORRELATIVE_IDS_KEY, correlativeIds);

			if(!transactionalCall) controlledConnection.commit();

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

	private HashMap<String,Object> updateCompleteVirtualOffshoreNumber(VtVirtualOffshoreNumber vtVirtualOffshoreNumber, VtDid vtDid, Connection transactionalConnection) throws VoiceTrafficException {

		Boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;
		HashMap<String, Object> response = new HashMap<String, Object>();

		try {
			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			VtDidManager vtDidManager;

			if(controlledConnection!=null)
				vtDidManager = new VtDidManager(controlledConnection);
			else
				vtDidManager = new VtDidManager(this.dataSourceName);

			//Delete vtDid rows
		
			List<Object> vtDidOlds = vtDidManager.list("type='" + VtDidType.VIRTUAL_OFFSHORE_NUMBER + "' AND foreignId='" + vtVirtualOffshoreNumber.getId() + "'");
			
			for (Object vtDidOld : vtDidOlds) {
				VtDid vtDidToDelete = (VtDid) vtDidOld;
				removeDid(vtDidToDelete.getId(), controlledConnection, false);
			}

			vtDid.setForeignId(vtVirtualOffshoreNumber.getCustomerId()); 
			
			if(vtDid.getId() < 0){
				this.addDid(vtDid, null, controlledConnection, false);
			}else{
				this.updateDid(vtDid, null, controlledConnection, false);	
			}

			//Update VON
			vtVirtualOffshoreNumber.setDidId(vtDid.getId());
			this.updateVirtualOffshoreNumber(vtVirtualOffshoreNumber, controlledConnection, false);

			response.put(VtVirtualOffshoreNumber.class.getSimpleName(), vtVirtualOffshoreNumber);
			response.put(VtDid.class.getSimpleName(), vtDid);

			if(!transactionalCall) controlledConnection.commit();

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


	private int removeCompleteVirtualOffshoreNumber(Long virtualOffshoreNumberId, Connection transactionalConnection) throws VoiceTrafficException {

		boolean transactionalCall = (transactionalConnection != null);
		Connection controlledConnection = null;

		try {

			if(transactionalCall)
				controlledConnection = transactionalConnection;
			else
				controlledConnection = this.createControlledConnection();

			VtVirtualOffshoreNumber virtualOffshoreNumber = this.loadVirtualOffshoreNumber(virtualOffshoreNumberId, controlledConnection, false);

			//Remove VON
			int virtualOffshoreRows = this.removeVirtualOffshoreNumber(virtualOffshoreNumberId, controlledConnection, false);

			//Remove Did
			this.removeDid(virtualOffshoreNumber.getDidId(), controlledConnection, false);

			if(!transactionalCall)
				controlledConnection.commit();

			return virtualOffshoreRows;
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
				throw new VoiceTrafficInvalidTransactionException("Transaction " + transactionId + " is invalid");
			}
		}
		else{
			throw new VoiceTrafficInvalidTransactionIdException("Transaction " + transactionId + " does not exists");
		}
	} 

	/* Private conecction/exception helpers */

	private Connection createControlledConnection() throws SQLException {
		Connection controlledConnection = this.dataSource.getConnection();
		controlledConnection.setAutoCommit(false);
		controlledConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		return controlledConnection;
	}

	private VoiceTrafficException manageException(Exception e){
		if (e instanceof VoiceTrafficException) {
			return (VoiceTrafficException) e;
		}
		else{
			VoiceTrafficGeneralException voiceTrafficGeneralException = new VoiceTrafficGeneralException(e);
			return voiceTrafficGeneralException;
		}
	}

	private VoiceTrafficException manageExceptionAndConnection(Exception e, Connection controlledConnection){
		try{
			if(controlledConnection!=null) controlledConnection.rollback();
		}
		catch(SQLException sqlException){
			return new VoiceTrafficGeneralException(sqlException.getMessage());
		}
		return this.manageException(e);
	}

	private void closeConecction(Connection controlledConnection) throws VoiceTrafficGeneralException{
		try{
			if(controlledConnection!=null) controlledConnection.close();
		}catch(SQLException sqlException){
			throw new VoiceTrafficGeneralException(sqlException.getMessage());
		}
	}


	/*Private Random Methods */

	private static String generateRandomPeerSecret(){
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
}
