package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;
import java.util.List;

public class IpIpPbx implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private long enterpriseId;

	private String name;

	private String contextSuffix;

	private boolean enabled;

	private boolean deleted;

	private int type;

	private List<IpExtension> extensions;

	private List<IpRingGroup> ringGroups;

	private List<IpVirtualOffshoreNumber> virtualOffshoreNumbers;

	private List<IpDid> dids;

	private List<IpVoiceMailBox> voiceMailBoxes;

	private List<IpEquipment> equipments;

	private List<IpExtensionHasEquipment> extensionsHasEquipments;

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

	public void setContextSuffix(String contextSuffix) {
		this.contextSuffix = contextSuffix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContextSuffix() {
		return contextSuffix;
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<IpExtension> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<IpExtension> extensions) {
		this.extensions = extensions;
	}

	public List<IpRingGroup> getRingGroups() {
		return ringGroups;
	}

	public void setRingGroups(List<IpRingGroup> ringGroups) {
		this.ringGroups = ringGroups;
	}

	public List<IpVirtualOffshoreNumber> getVirtualOffshoreNumbers() {
		return virtualOffshoreNumbers;
	}

	public void setVirtualOffshoreNumbers(
			List<IpVirtualOffshoreNumber> virtualOffshoreNumbers) {
		this.virtualOffshoreNumbers = virtualOffshoreNumbers;
	}

	public List<IpDid> getDids() {
		return dids;
	}

	public void setDids(List<IpDid> dids) {
		this.dids = dids;
	}

	public List<IpEquipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<IpEquipment> equipments) {
		this.equipments = equipments;
	}

	public IpPbxServiceType getType() {
		return IpPbxServiceType.values()[this.type];
	}

	public void setType(IpPbxServiceType type) {
		this.type = type.ordinal();
	}

	public void setType(String type) {
		this.type = IpPbxServiceType.valueOf(type).ordinal();
	}

	public List<IpVoiceMailBox> getVoiceMailBoxes() {
		return voiceMailBoxes;
	}

	public void setVoiceMailBoxes(List<IpVoiceMailBox> voiceMailBoxes) {
		this.voiceMailBoxes = voiceMailBoxes;
	}

	public List<IpExtensionHasEquipment> getExtensionsHasEquipments() {
		return extensionsHasEquipments;
	}

	public void setExtensionsHasEquipments(List<IpExtensionHasEquipment> extensionsHasEquipments) {
		this.extensionsHasEquipments = extensionsHasEquipments;
	}

}