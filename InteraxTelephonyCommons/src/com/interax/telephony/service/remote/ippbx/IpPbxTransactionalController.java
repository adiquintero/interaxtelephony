package com.interax.telephony.service.remote.ippbx;

import java.util.HashMap;
import java.util.List;
//import java.util.Vector;

import javax.ejb.Remote;

import com.interax.telephony.service.exception.ippbx.IpPbxEquipmentAlreadyActivatedException;
import com.interax.telephony.service.exception.ippbx.IpPbxException;
import com.interax.telephony.service.exception.ippbx.IpPbxGeneralException;
import com.interax.telephony.service.exception.ippbx.IpPbxInvalidEquipmentActivationCodeException;
import com.interax.telephony.service.exception.ippbx.IpPbxMaxEquipmentActivationAttemptsReachedException;
import com.interax.telephony.service.ippbx.data.IpPbxTransactionalRequest;
import com.interax.telephony.service.ippbx.data.IpPbxTransactionalResponse;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;


@Remote
public interface IpPbxTransactionalController extends InteraxTelephonyGenericEjb
{
	public static final String CURRENT_EQUIPMENT_ACTIVATION_ATTEMPTS_KEY = "CURRENT_EQUIPMENT_ACTIVATION_ATTEMPTS";
	public static final String MAX_EQUIPMENT_ACTIVATION_ATTEMPTS_KEY = "MAX_EQUIPMENT_ACTIVATION_ATTEMPTS";
	public static final String EQUIPMENT_ACTIVATION_CODE_LENGTH_KEY = "EQUIPMENT_ACTIVATION_CODE_LENGTH_KEY";


	/* Atomic Simple IpPbx Management Methods */

	public IpPbxTransactionalResponse loadIpPbx(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadIpPbxs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addIpPbx(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addIpPbxs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateIpPbx(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateIpPbxs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeIpPbx(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeIpPbxs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	public IpPbxTransactionalResponse loadExtension(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadExtensions(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addExtension(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addExtensions(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateExtension(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateExtensions(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeExtension(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeExtensions(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	public IpPbxTransactionalResponse loadVirtualOffshoreNumber(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addVirtualOffshoreNumber(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateVirtualOffshoreNumber(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeVirtualOffshoreNumber(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	public IpPbxTransactionalResponse loadRingGroup(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadRingGroups(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addRingGroup(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addRingGroups(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateRingGroup(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateRingGroups(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeRingGroup(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeRingGroups(List<IpPbxTransactionalRequest> requests) throws IpPbxException;	

	public IpPbxTransactionalResponse loadDid(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadDids(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addDid(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addDids(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateDid(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateDids(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeDid(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeDids(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	public IpPbxTransactionalResponse loadVoiceMailBox(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadVoiceMailBoxes(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addVoiceMailBox(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addVoiceMailBoxes(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateVoiceMailBox(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateVoiceMailBoxes(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeVoiceMailBox(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeVoiceMailBoxes(List<IpPbxTransactionalRequest> requests) throws IpPbxException;	

	public IpPbxTransactionalResponse loadEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;	      

	public IpPbxTransactionalResponse loadExtensionHasEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addExtensionHasEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateExtensionHasEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeExtensionHasEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;       
	
	
	public IpPbxTransactionalResponse loadExtensionHasForward(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadExtensionsHasForwards(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addExtensionHasForward(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addExtensionsHasForwards(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateExtensionHasForward(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateExtensionsHasForwards(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeExtensionHasForward(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeExtensionsHasForwards(List<IpPbxTransactionalRequest> requests) throws IpPbxException;       
	
	

	//Provisionin File y Provisioning File agregados por Yury el 22/04/09 
	/*public ProvisioningFile loadProvisioningFile(Long provisioningFileId) throws IpPbxException;
	public IpPbxTransactionalResponse addProvisioningFile(IpPbxTransactionalRequest request) throws IpPbxException;
	public IpPbxTransactionalResponse updateProvisioningFile(IpPbxTransactionalRequest request) throws IpPbxException;
	public void removeProvisioningFile(Long provisioningFileId) throws IpPbxException;

	public ProvisioningFileRequest loadProvisioningFileRequest(Long provisioningFileRequestId) throws IpPbxException;
	public IpPbxTransactionalResponse addProvisioningFileRequest(IpPbxTransactionalRequest request) throws IpPbxException;
	public IpPbxTransactionalResponse updateProvisioningFileRequest(IpPbxTransactionalRequest request) throws IpPbxException;
	public void removeProvisioningFileRequest(Long provisioningFileRequestId) throws IpPbxException;
	 */

	/* Atomic Complete IpPbx Methods */

	public IpPbxTransactionalResponse loadCompleteIpPbx(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteIpPbxs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteIpPbx(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteIpPbxs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteIpPbx(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteIpPbxs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteIpPbx(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteIpPbxs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	/* Atomic Complete Extension Methods */

	public IpPbxTransactionalResponse loadCompleteExtension(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteExtensions(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteExtension(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteExtensions(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteExtension(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteExtensions(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteExtension(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteExtensions(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	/* Atomic Complete VirtualOffshoreNumber Methods */

	public IpPbxTransactionalResponse loadCompleteVirtualOffshoreNumber(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteVirtualOffshoreNumber(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteVirtualOffshoreNumber(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteVirtualOffshoreNumber(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	/* Atomic Complete RingGroup Methods */

	public IpPbxTransactionalResponse loadCompleteRingGroup(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteRingGroups(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteRingGroup(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteRingGroups(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteRingGroup(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteRingGroups(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteRingGroup(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteRingGroups(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	
	/* Atomic Complete Equipment Methods */
	
	public IpPbxTransactionalResponse loadCompleteEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	
	
	/* Atomic Complete VoiceMailBox Methods */
	
	public IpPbxTransactionalResponse loadCompleteVoiceMailBox(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteVoiceMailBoxes(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteVoiceMailBox(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteVoiceMailBoxes(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteVoiceMailBox(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteVoiceMailBoxes(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteVoiceMailBox(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteVoiceMailBoxes(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	
	/* Atomic Complete ExtensionHasEquipment Methods */
	
	public IpPbxTransactionalResponse loadCompleteExtensionHasEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteExtensionHasEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteExtensionHasEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteExtensionHasEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;
	
	
	/* Atomic Complete ExtensionHasForward Methods */
	
	public IpPbxTransactionalResponse removeCompleteExtensionHasForward(IpPbxTransactionalRequest request) throws IpPbxException;
	public IpPbxTransactionalResponse loadCompleteExtensionHasForward(IpPbxTransactionalRequest request) throws IpPbxException;

	/* Atomic Extra IpPbx Management Methods */

	public IpPbxTransactionalResponse generateExtensionHasEquipmentConfig(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> generateExtensionsHasEquipmentsConfigs(List<IpPbxTransactionalRequest> requests) throws IpPbxException;

	public IpPbxTransactionalResponse deprovisioningIpPbxEquipment(IpPbxTransactionalRequest request) throws IpPbxException;
	public List<IpPbxTransactionalResponse> deprovisioningIpPbxEquipments(List<IpPbxTransactionalRequest> requests) throws IpPbxException;


	/* Transaction Management Methods */

	public Long beginTransaction() throws Exception;
	public void commitTransaction(Long transactionId) throws Exception;
	public void rollbackTransaction(Long transactionId) throws Exception;


	/* Transactional Simple IpPbx Management Methods */

	public IpPbxTransactionalResponse loadIpPbx(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadIpPbxs(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addIpPbx(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addIpPbxs(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateIpPbx(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateIpPbxs(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeIpPbx(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeIpPbxs(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	public IpPbxTransactionalResponse loadExtension(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadExtensions(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addExtension(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addExtensions(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateExtension(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateExtensions(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeExtension(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeExtensions(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	public IpPbxTransactionalResponse loadVirtualOffshoreNumber(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addVirtualOffshoreNumber(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateVirtualOffshoreNumber(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeVirtualOffshoreNumber(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	public IpPbxTransactionalResponse loadRingGroup(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadRingGroups(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addRingGroup(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addRingGroups(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateRingGroup(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateRingGroups(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeRingGroup(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeRingGroups(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	public IpPbxTransactionalResponse loadDid(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadDids(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addDid(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addDids(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateDid(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateDids(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeDid(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeDids(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	public IpPbxTransactionalResponse loadVoiceMailBox(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadVoiceMailBoxes(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addVoiceMailBox(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addVoiceMailBoxes(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateVoiceMailBox(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateVoiceMailBoxes(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeVoiceMailBox(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeVoiceMailBoxes(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	public IpPbxTransactionalResponse loadEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;       

	public IpPbxTransactionalResponse loadExtensionHasEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addExtensionHasEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateExtensionHasEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeExtensionHasEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;           

   /* Transaccional Simple ExtensionHasForward Methods */
	
	public IpPbxTransactionalResponse loadExtensionHasForward(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadExtensionsHasForwards(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addExtensionHasForward(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addExtensionsHasForwards(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateExtensionHasForward(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateExtensionsHasForwards(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeExtensionHasForward(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeExtensionsHasForwards(List<IpPbxTransactionalRequest> requests, Long transactionId)  throws IpPbxException;       
	
	
	
	
	
	
	
    
//	public IpPbxTransactionalResponse loadExtensionHasForward(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
//	public List<IpPbxTransactionalResponse> loadExtensionsHasForwards(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
//	public IpPbxTransactionalResponse addExtensionHasForward(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
//	public List<IpPbxTransactionalResponse> addExtensionsHasForwards(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
//	public IpPbxTransactionalResponse updateExtensionHasForward(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
//	public List<IpPbxTransactionalResponse> updaCompleteteExtensionsHasForwards(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
//	public IpPbxTransactionalResponse removeExtensionHasForward(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
//	public List<IpPbxTransactionalResponse> removeExtensionsHasForwards(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException; 

	/* Transaccional Complete IpPbx Methods */

	public IpPbxTransactionalResponse loadCompleteIpPbx(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteIpPbxs(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteIpPbx(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteIpPbxs(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteIpPbx(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteIpPbxs(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteIpPbx(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteIpPbxs(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	/* Transaccional Complete Extension Methods */

	public IpPbxTransactionalResponse loadCompleteExtension(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteExtensions(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteExtension(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteExtensions(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteExtension(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteExtensions(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteExtension(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteExtensions(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	/* Transaccional Complete VirtualOffshoreNumber Methods */

	public IpPbxTransactionalResponse loadCompleteVirtualOffshoreNumber(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteVirtualOffshoreNumber(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteVirtualOffshoreNumber(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteVirtualOffshoreNumber(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteVirtualOffshoreNumbers(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	/* Transaccional Complete RingGroup Methods */

	public IpPbxTransactionalResponse loadCompleteRingGroup(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteRingGroups(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteRingGroup(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteRingGroups(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteRingGroup(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteRingGroups(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteRingGroup(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteRingGroups(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	
	/* Transaccional Complete Equipment Methods */
	
	public IpPbxTransactionalResponse loadCompleteEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	
	
	/* Transaccional Complete VoiceMailBox Methods */
	
	public IpPbxTransactionalResponse loadCompleteVoiceMailBox(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteVoiceMailBoxes(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteVoiceMailBox(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteVoiceMailBoxes(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteVoiceMailBox(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteVoiceMailBoxes(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteVoiceMailBox(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteVoiceMailBoxes(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;


	/* Transaccional Complete ExtensionHasEquipment Methods */
	
	public IpPbxTransactionalResponse loadCompleteExtensionHasEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> loadCompleteExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse addCompleteExtensionHasEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> addCompleteExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse updateCompleteExtensionHasEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> updateCompleteExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;
	public IpPbxTransactionalResponse removeCompleteExtensionHasEquipment(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteExtensionsHasEquipments(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;

	
	
	/* Transaccional Complete ExtensionHasForward Methods */
	public IpPbxTransactionalResponse removeCompleteExtensionsHasForward(IpPbxTransactionalRequest requests, Long transactionId) throws IpPbxException;
	public List<IpPbxTransactionalResponse> removeCompleteExtensionsHasForward(List<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;    
	public IpPbxTransactionalResponse activateExtensionForward(IpPbxTransactionalRequest request) throws IpPbxException;

	//	/* Transactional Extra IpPbx Management Methods */
	//	
	//	public IpPbxTransactionalResponse generateExtensionHasEquipmentConfig(IpPbxTransactionalRequest request, Long transactionId) throws IpPbxException;
	//	public List<IpPbxTransactionalResponse> generateExtensionsHasEquipmentsConfigs(Vector<IpPbxTransactionalRequest> requests, Long transactionId) throws IpPbxException;


	/* Extension forward activation   */
	
	public IpPbxTransactionalResponse activateCompleteExtensionForward(IpPbxTransactionalRequest request) throws IpPbxException;
	public IpPbxTransactionalResponse disableAndRemoveCompleteExtensionForward(IpPbxTransactionalRequest request) throws IpPbxException;
	

	/* Equipment Activation Methods */

	/**
	 * Retorna según el identificador de un dispositivo, el número de intentos de activación que lleva hasta el momento, lanza una 
	 * IpPbxUnknownEquipmentException si no encuentra un dispositivo en el sistema asociado a ese identificador.
	 * @param long ipExtensionId -- El id de la extensión cuyo dispositivo se desea activar.
	 * @return HashMap
	 * @throws IpPbxUnknownExtensionException
	 * @throws IpPbxEquipmentUnknownException
	 * @throws IpPbxGeneralException
	 */
	public HashMap<String, Integer> getEquipmentActivationAttemps(long ipExtensionId) throws IpPbxException;


	/**
	 * Función para activar un dispositivo de identificador equipmentId y código activationCode, devuelve verdadero en caso que lo pueda
	 * activar, si no encuentra un dispositivo en el sistema asociado a ese identificador lanza una IpPbxUnknownEquipmentException y si el 
	 * código no concuerda lanza un IpPbxInvalidActivationCodeException.
	 * @param long ipExtensionId	-- El id de la extensión cuyo dispositivo se desea activar.
	 * @param String activationCode	-- El código de activación indicado por el usuario
	 * @param String ipAddress		-- La dirección IP del equipo desde el cual se originó la llamada
	 * @return boolean
	 * @throws IpPbxUnknownExtensionException
	 * @throws IpPbxEquipmentUnknownException
	 * @throws IpPbxEquipmentAlreadyActivatedException
	 * @throws IpPbxMaxEquipmentActivationAttemptsReachedException
	 * @throws IpPbxInvalidEquipmentActivationCodeException
	 * @throws IpPbxGeneralException
	 */
	public boolean activateEquipment(long ipExtensionId, String activationCode, String ipAddress) throws IpPbxException;
	
	public HashMap<String,String> loadRtSipPeerByExtension(long ipExtensionId) throws IpPbxException;
	
}

