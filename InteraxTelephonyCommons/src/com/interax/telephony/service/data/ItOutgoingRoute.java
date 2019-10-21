package com.interax.telephony.service.data;
import java.io.Serializable;
import java.util.List;

public class ItOutgoingRoute implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;

	private long enterpriseId;

	private String serviceFamily;
	
	private String serviceType;
	
	private String accessType;
	
	private String ldiCode;
	
	private String ldnCode;
	
	private List<ItOutgoingRouteStep> itOutgoingRouteSteps;
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEnterpriseid() {
		return this.enterpriseId;
	}

	public void setEnterpriseid(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	

	public String getServiceFamily() {
		return this.serviceFamily;
	}

	public void setServiceFamily(String serviceFamily) {
		this.serviceFamily = serviceFamily;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getAccessType() {
		return this.accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	
	public String getLdiCode() {
		return ldiCode;
	}

	public void setLdiCode(String ldiCode) {
		this.ldiCode = ldiCode;
	}

	public String getLdnCode() {
		return ldnCode;
	}

	public void setLdnCode(String ldnCode) {
		this.ldnCode = ldnCode;
	}
	
	
	public List<ItOutgoingRouteStep> getItOutgoingRouteSteps() {
		return this.itOutgoingRouteSteps;
	}

	public void setItOutgoingRouteSteps(List<ItOutgoingRouteStep> itOutgoingRouteSteps) {
		this.itOutgoingRouteSteps = itOutgoingRouteSteps;
	}
	
	

}
