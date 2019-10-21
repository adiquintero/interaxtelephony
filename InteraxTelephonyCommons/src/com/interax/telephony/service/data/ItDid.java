package com.interax.telephony.service.data;

import java.io.Serializable;

import com.interax.telephony.service.callingcard.data.CallingCardServiceType;
import com.interax.telephony.service.did.data.DIDServiceType;
import com.interax.telephony.service.did.data.DidService;
import com.interax.telephony.service.interactivevoiceresponse.data.InteractiveVoiceResponseServiceType;
import com.interax.telephony.service.ippbx.data.IpPbxServiceType;
import com.interax.telephony.service.voicetraffic.data.VoiceTrafficServiceType;


public class ItDid implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private String externalNumber;
	private int serviceFamily = -1;
	private int serviceType;
	private long foreignId;
	
//	private boolean delayedServiceTypeLoad = false;
//	private String delayedServiceTypeValue = null;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExternalNumber() {
		return externalNumber;
	}
	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}
	
	public ServiceFamily getServiceFamily() {
		return ServiceFamily.values()[this.serviceFamily];
	}

	public void setServiceFamily(ServiceFamily serviceFamily) {
		this.serviceFamily = serviceFamily.ordinal();
//		setDelayedServiceType();
	}

	public void setServiceFamily(String serviceFamily) {
		this.serviceFamily = ServiceFamily.valueOf(serviceFamily).ordinal();
//		setDelayedServiceType();
	}
	
//	private void setDelayedServiceType(){
//		if(this.delayedServiceTypeLoad){
//			this.delayedServiceTypeLoad = false;
//			this.setServiceType(this.delayedServiceTypeValue);
//		}
//	}
	
	
	@SuppressWarnings("unchecked")
	public Enum getServiceType() {
		switch(this.getServiceFamily()){
			case CALLING_CARD:
			return CallingCardServiceType.values()[this.serviceType];
			
			case DID:
				return DIDServiceType.values()[this.serviceType];

			case IP_PBX:
				return IpPbxServiceType.values()[this.serviceType];

			case VOICE_TRAFFIC:
				return VoiceTrafficServiceType.values()[this.serviceType];
		
			case IVR:
				return InteractiveVoiceResponseServiceType.values()[this.serviceType];
			
			default:
				return null;
		}
	}

	public void setServiceType(CallingCardServiceType serviceType) {
		this._setServiceType(serviceType);
	}
	
	public void setServiceType(DIDServiceType serviceType) {
		this._setServiceType(serviceType);
	}
	
	public void setServiceType(IpPbxServiceType serviceType) {
		this._setServiceType(serviceType);
	}
	
	public void setServiceType(InteractiveVoiceResponseServiceType serviceType) {
		this._setServiceType(serviceType);
	}

	public void setServiceType(VoiceTrafficServiceType serviceType) {
		this._setServiceType(serviceType);
	}

	@SuppressWarnings("unchecked")
	private void _setServiceType(Enum serviceType) {
		this.serviceType = serviceType.ordinal();
	}

	public void setServiceType(String serviceType) {
		
//		if(this.serviceFamily<0){
//			this.delayedServiceTypeLoad = true;
//			this.delayedServiceTypeValue = serviceType;
//		}
//		else{
			switch(this.getServiceFamily()){
				case CALLING_CARD:
					this.serviceType = CallingCardServiceType.valueOf(serviceType).ordinal();
				break;
			
				case DID:
					this.serviceType = DIDServiceType.valueOf(serviceType).ordinal();
				break;
			
				case IP_PBX:
					this.serviceType = IpPbxServiceType.valueOf(serviceType).ordinal();
				break;
					
				case VOICE_TRAFFIC:
					this.serviceType = VoiceTrafficServiceType.valueOf(serviceType).ordinal();
		
				case IVR:
					this.serviceType = InteractiveVoiceResponseServiceType.valueOf(serviceType).ordinal();
				break;
			}
//		}
	}
	
	public long getForeignId() {
		return foreignId;
	}
	public void setForeignId(long foreignId) {
		this.foreignId = foreignId;
	}

}