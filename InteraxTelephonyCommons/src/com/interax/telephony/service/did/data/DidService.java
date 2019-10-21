package com.interax.telephony.service.did.data;

import java.io.Serializable;
import java.util.List;

import com.interax.telephony.service.ippbx.data.IpDid;
import com.interax.telephony.service.ippbx.data.IpEquipment;
import com.interax.telephony.service.ippbx.data.IpExtension;
import com.interax.telephony.service.ippbx.data.IpExtensionHasEquipment;
import com.interax.telephony.service.ippbx.data.IpRingGroup;
import com.interax.telephony.service.ippbx.data.IpVirtualOffshoreNumber;
import com.interax.telephony.service.ippbx.data.IpVoiceMailBox;

public class DidService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;

	private long foreignId;
	
	private long enterpriseId;

	private boolean enabled;

	private boolean deleted;
	
	private String terminationIp;

	private List<DidDid> didDids;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public void setForeignId(long foreingId){
		this.foreignId = foreingId;
	}
	
	public long getForeignId(){
		return foreignId;
	}

	public List<DidDid> getDidDids() {
		return didDids;
	}

	public void setDidDids(List<DidDid> dids) {
		this.didDids = dids;
	}

	public String getTerminationIp() {
		return terminationIp;
	}

	public void setTerminationIp(String terminationIp) {
		this.terminationIp = terminationIp;
	}
}
