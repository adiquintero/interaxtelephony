package com.interax.telephony.service.callingcard.data;
import java.io.Serializable;


public class CcAplication implements Serializable {

	private long id;

	private long enterpriseId;

	private String configName;

	private String country;

	private static final long serialVersionUID = 1L;

	public CcAplication() {
		super();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getConfigname() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
