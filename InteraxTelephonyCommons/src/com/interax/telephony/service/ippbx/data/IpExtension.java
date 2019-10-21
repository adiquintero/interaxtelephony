package com.interax.telephony.service.ippbx.data;

import java.io.Serializable;

import com.interax.telephony.service.data.ItPeer;
import com.interax.telephony.service.data.ItPeerType;

public class IpExtension extends ItPeer implements Serializable {

    private static final long serialVersionUID = 1L;
    // GENERAL
    private long ipPbxId;
    private int internalNumber;
    private String callerId;
    private boolean hasEmergencyAddress;
    private boolean adminEnabled;
    private boolean enabled;
    private boolean deleted;
    private String language;
    private String ldiPrefix;
    private String ldnPrefix;
    private int localNumberMinLength;
    private int localNumberMaxLength;
    // IAX
    private boolean hasIax;
    // SIP
    private boolean hasSip;
    // CODECS
    // VOICEMAIL
    private long voiceMailBoxId;
    // DID
    private long didId;
    // EQUIPMENT
    private long equipmentId;
    
    private boolean forwardEnable;
    
    
    
    

    public IpExtension() {
        super();
        this.setType(ItPeerType.IP_EXTENSION);
    }

    public long getIpPbxId() {
        return ipPbxId;
    }

    public void setIpPbxId(long ipPbxId) {
        this.ipPbxId = ipPbxId;
    }

    public int getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(int internalNumber) {
        this.internalNumber = internalNumber;
    }

    public boolean getHasIax() {
        return hasIax;
    }

    public void setHasIax(boolean hasIax) {
        this.hasIax = hasIax;
    }

    public boolean getHasSip() {
        return hasSip;
    }

    public void setHasSip(boolean hasSip) {
        this.hasSip = hasSip;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getAdminEnabled() {
        return adminEnabled;
    }

    public void setAdminEnabled(boolean adminEnabled) {
        this.adminEnabled = adminEnabled;
    }

    public long getDidId() {
        return didId;
    }

    public void setDidId(long didId) {
        this.didId = didId;
    }

    public boolean isHasEmergencyAddress() {
        return hasEmergencyAddress;
    }

    public void setHasEmergencyAddress(boolean hasEmergencyAddress) {
        this.hasEmergencyAddress = hasEmergencyAddress;
    }

    public long getVoiceMailBoxId() {
        return voiceMailBoxId;
    }

    public void setVoiceMailBoxId(long voiceMailBoxId) {
        this.voiceMailBoxId = voiceMailBoxId;
    }

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLdiPrefix(String ldiPrefix) {
        this.ldiPrefix = ldiPrefix;
    }

    public String getLdiPrefix() {
        return ldiPrefix;
    }

    public void setLdnPrefix(String ldnPrefix) {
        this.ldnPrefix = ldnPrefix;
    }

    public String getLdnPrefix() {
        return ldnPrefix;
    }

    public void setLocalNumberMinLength(int localNumberMinLength) {
        this.localNumberMinLength = localNumberMinLength;
    }

    public int getLocalNumberMinLength() {
        return localNumberMinLength;
    }

    public void setLocalNumberMaxLength(int localNumberMaxLength) {
        this.localNumberMaxLength = localNumberMaxLength;
    }

    public int getLocalNumberMaxLength() {
        return localNumberMaxLength;
    }

	public boolean isForwardEnable() {
		return forwardEnable;
	}

	public void setForwardEnable(boolean forwardEnable) {
		this.forwardEnable = forwardEnable;
	}




}





