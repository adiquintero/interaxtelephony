package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;
import java.util.Calendar;


public class IpExtensionHasEquipment implements Serializable{
	private static final long serialVersionUID = 1L;
        public static final String STANDARD_ACTIVATION_CODE = "0000";

	private long extensionId;
	private long equipmentId;
	private Calendar applyDate;
	private boolean enabled;
	private Calendar disableDate;
	private boolean activated;
	private Calendar activationDate;
	private String activationCode;
	private int activationAttempts;
	private String encryptionKey;
	private String fileVersion;
	private int protocol;
	
	public boolean getActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public int getActivationAttempts() {
		return activationAttempts;
	}
	public void setActivationAttempts(int activationAttempts) {
		this.activationAttempts = activationAttempts;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public Calendar getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(Calendar activationDate) {
		this.activationDate = activationDate;
	}
	public Calendar getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Calendar applyDate) {
		this.applyDate = applyDate;
	}
	public Calendar getDisableDate() {
		return disableDate;
	}
	public void setDisableDate(Calendar disableDate) {
		this.disableDate = disableDate;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public long getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(long equipmentId) {
		this.equipmentId = equipmentId;
	}
	public long getExtensionId() {
		return extensionId;
	}
	public void setExtensionId(long extensionId) {
		this.extensionId = extensionId;
	}
	public String getEncryptionKey() {
		return encryptionKey;
	}
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
	public String getFileVersion() {
		return fileVersion;
	}
	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}
	
	public IpExtensionProtocol getProtocol() {
		return IpExtensionProtocol.values()[this.protocol];
	}

	public void setProtocol(IpExtensionProtocol protocol) {
		this.protocol = protocol.ordinal();
	}

	public void setProtocol(String protocol) {
		this.protocol = IpExtensionProtocol.valueOf(protocol).ordinal();
	}
	
}