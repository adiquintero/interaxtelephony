package com.interax.telephony.cdr;

import com.interax.telephony.service.callingcard.data.CallingCardAccessType;
import com.interax.telephony.service.callingcard.data.CallingCardServiceType;
import com.interax.telephony.service.data.ServiceFamily;
import com.interax.telephony.service.did.data.DidAccessType;
import com.interax.telephony.service.did.data.DidServiceType;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseAccessType;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseServiceType;
import com.interax.telephony.service.ippbx.data.IpPbxAccessType;
import com.interax.telephony.service.ippbx.data.IpPbxServiceType;
import com.interax.telephony.service.pickdialing.data.PickDialingAccessType;
import com.interax.telephony.service.pickdialing.data.PickDialingServiceType;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficAccessType;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficServiceType;

public class InteraxTelephonyCdrFormater {

	private static final String CDR_ACCOUNT_CODE_SEPARATOR = "|";
	private static final String CDR_USER_FIELD_SEPARATOR = "|";
	private static final String CDR_USER_FIELD_INTERNAL_SEPARATOR = ":";
	private static final String CDR_NULL_DATA_CHARACTER = "-";
	
	public static String formatCdrAccountCode(ServiceFamily serviceFamily, Long enterpriseId){
		
		String serviceFamilyStr = (serviceFamily!=null) ?  serviceFamily.name() : null;
		String enterpriseIdStr = (enterpriseId!=null) ? enterpriseId.toString() : null;
			
		return formatCdrAccountCode(serviceFamilyStr, enterpriseIdStr);
	}
	
	public static String formatCdrUserField(CallingCardAccessType accessType, CallingCardServiceType serviceType, Long pinSerial, String customData){

		String accessTypeStr = (accessType!=null) ?  accessType.name() : null;
		String serviceTypeStr = (serviceType!=null) ? serviceType.name() : null;
		String customerIdStr = (pinSerial!=null) ? pinSerial.toString() : null;
			
		return formatCdrUserField(accessTypeStr, serviceTypeStr, customerIdStr, customData);
	}
	
	public static String formatCdrUserField(IpPbxAccessType accessType, IpPbxServiceType serviceType, Long pbxId, Long extensionId, String customData){

		String accessTypeStr = (accessType!=null) ?  accessType.name() : null;
		String serviceTypeStr = (serviceType!=null) ? serviceType.name() : null;
		StringBuffer customerIdStr = new StringBuffer();
		customerIdStr.append((pbxId!=null) ? pbxId.toString() : CDR_NULL_DATA_CHARACTER);
		customerIdStr.append(CDR_USER_FIELD_INTERNAL_SEPARATOR);
		customerIdStr.append((extensionId!=null) ? extensionId.toString() : CDR_NULL_DATA_CHARACTER);
			
		return formatCdrUserField(accessTypeStr, serviceTypeStr, customerIdStr.toString(), customData);
	}

	public static String formatCdrUserField(VoiceTrafficAccessType accessType, VoiceTrafficServiceType serviceType, Long customerId, Long peerId, String customData){

		String accessTypeStr = (accessType!=null) ?  accessType.name() : null;
		String serviceTypeStr = (serviceType!=null) ? serviceType.name() : null;
		StringBuffer customerIdStr = new StringBuffer();
		customerIdStr.append((customerId!=null) ? customerId.toString() : CDR_NULL_DATA_CHARACTER);
		customerIdStr.append(CDR_USER_FIELD_INTERNAL_SEPARATOR);
		customerIdStr.append((peerId!=null) ? peerId.toString() : CDR_NULL_DATA_CHARACTER);
			
		return formatCdrUserField(accessTypeStr, serviceTypeStr, customerIdStr.toString(), customData);
	}
	
	public static String formatCdrUserField(DidAccessType accessType, DidServiceType serviceType, Long customerId, Long peerId, String customData){

		String accessTypeStr = (accessType!=null) ?  accessType.name() : null;
		String serviceTypeStr = (serviceType!=null) ? serviceType.name() : null;
		StringBuffer customerIdStr = new StringBuffer();
		customerIdStr.append((customerId!=null) ? customerId.toString() : CDR_NULL_DATA_CHARACTER);
		customerIdStr.append(CDR_USER_FIELD_INTERNAL_SEPARATOR);
		customerIdStr.append((peerId!=null) ? peerId.toString() : CDR_NULL_DATA_CHARACTER);
			
		return formatCdrUserField(accessTypeStr, serviceTypeStr, customerIdStr.toString(), customData);
	}
	
		//FIXME Metodo adaptado para ivr.No es el definitivo. 
	public static String formatCdrUserField(InteractiveVoiceResponseAccessType accessType, InteractiveVoiceResponseServiceType serviceType, Long ivrId, Long scriptId, String customData){
		//FIXME el parametro callcenterId se agrego para evitar problemas con el llamado de la funcion en los agis de otras familia 
		String accessTypeStr = (accessType!=null) ?  accessType.name() : null;
		String serviceTypeStr = (serviceType!=null) ? serviceType.name() : null;
		StringBuffer customerIdStr = new StringBuffer();
		customerIdStr.append((ivrId!=null) ? ivrId.toString() : CDR_NULL_DATA_CHARACTER);
		customerIdStr.append(CDR_USER_FIELD_INTERNAL_SEPARATOR);
		customerIdStr.append((scriptId!=null) ? scriptId.toString() : CDR_NULL_DATA_CHARACTER);

		return formatCdrUserField(accessTypeStr, serviceTypeStr, customerIdStr.toString(), customData);
	}
		
	public static String formatCdrUserField(PickDialingAccessType accessType, PickDialingServiceType serviceType, Long customerId, String ani, String customData){

		String accessTypeStr = (accessType!=null) ?  accessType.name() : null;
		String serviceTypeStr = (serviceType!=null) ? serviceType.name() : null;
		StringBuffer customerIdStr = new StringBuffer();
		customerIdStr.append((customerId!=null) ? customerId.toString() : CDR_NULL_DATA_CHARACTER);
		customerIdStr.append(CDR_USER_FIELD_INTERNAL_SEPARATOR);
		customerIdStr.append((ani!=null) ? ani.toString() : CDR_NULL_DATA_CHARACTER);
			
		return formatCdrUserField(accessTypeStr, serviceTypeStr, customerIdStr.toString(), customData);
	}
	
	
	private static String formatCdrAccountCode(String serviceFamily, String enterpriseId){
		StringBuffer userField = new StringBuffer();
		if(serviceFamily==null)
			userField.append(CDR_NULL_DATA_CHARACTER);
		else
			userField.append(serviceFamily);

		userField.append(CDR_ACCOUNT_CODE_SEPARATOR);
		if(enterpriseId==null)
			userField.append(CDR_NULL_DATA_CHARACTER);
		else
			userField.append(enterpriseId);
		
		return userField.toString();
	}
	
	private static String formatCdrUserField(String accessType, String serviceType, String customerId, String customData){
		
		StringBuffer userField = new StringBuffer();
		
		if(accessType==null)
			userField.append(CDR_NULL_DATA_CHARACTER);
		else
			userField.append(accessType);
		
		userField.append(CDR_USER_FIELD_SEPARATOR);
		if(serviceType==null)
			userField.append(CDR_NULL_DATA_CHARACTER);
		else
			userField.append(serviceType);
		
		userField.append(CDR_USER_FIELD_SEPARATOR);
		if(customerId==null)
			userField.append(CDR_NULL_DATA_CHARACTER);
		else
			userField.append(customerId);
		
		userField.append(CDR_USER_FIELD_SEPARATOR);
		if(customData==null)
			userField.append(CDR_NULL_DATA_CHARACTER);
		else
			userField.append(customData);
		
		return userField.toString();
		
	}
	
}
