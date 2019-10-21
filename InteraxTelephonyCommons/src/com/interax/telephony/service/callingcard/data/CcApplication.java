package com.interax.telephony.service.callingcard.data;
import java.io.Serializable;



public class CcApplication implements Serializable {

	private long id;

	private long enterpriseId;

	private String configName;

	private String country;

	private static final long serialVersionUID = 1L;

	public CcApplication() {
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

	public void setEnterpriseid(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getConfigname() {
		return this.configName;
	}

	public void setConfigname(String configname) {
		this.configName = configname;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
