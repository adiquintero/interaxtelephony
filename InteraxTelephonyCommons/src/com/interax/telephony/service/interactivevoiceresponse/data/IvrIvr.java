package com.interax.telephony.service.interactivevoiceresponse.data;

import java.io.Serializable;

public class IvrIvr implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;

	private long enterpriseId;

	private String name;

	private int type;

	private String configName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InteractiveVoiceResponseServiceType getType() {
		return InteractiveVoiceResponseServiceType.values()[type];
	}

	public void setType(String type) {
		this.type =  InteractiveVoiceResponseServiceType.valueOf(type).ordinal();
	}

	public void setType(InteractiveVoiceResponseServiceType type) {
		this.type = type.ordinal();
	}

	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}

}
