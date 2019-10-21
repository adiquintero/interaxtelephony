package com.interax.telephony.service.data;

import java.io.Serializable;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.List;

public class ItTransactionalRequest implements Serializable{
	private static final long serialVersionUID = 1L;

	Object requestData;
	@SuppressWarnings("unchecked")
	Class requestType;
	@SuppressWarnings("unchecked")
	HashMap<String, List> relatedEntities;
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, List> getRelatedEntities() {
		return relatedEntities;
	}
	@SuppressWarnings("unchecked")
	public void setRelatedEntities(HashMap<String, List> relatedEntities) {
		this.relatedEntities = relatedEntities;
	}
	public Object getRequestData() {
		return requestData;
	}
	public void setRequestData(Object requestData) {
		this.requestData = requestData;
	}
	@SuppressWarnings("unchecked")
	public Class getRequestType() {
		return requestType;
	}
	@SuppressWarnings("unchecked")
	public void setRequestType(Class requestType) {
		this.requestType = requestType;
	}

}
