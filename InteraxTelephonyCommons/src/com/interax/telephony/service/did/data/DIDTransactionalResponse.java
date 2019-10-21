package com.interax.telephony.service.did.data;

import java.io.Serializable;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.List;

public class DIDTransactionalResponse implements Serializable
{
	private static final long serialVersionUID = 1L;

	Object responseData;
	@SuppressWarnings("unchecked")
	Class responseType;

	@SuppressWarnings("unchecked")
	HashMap<String, List> relatedEntities;
	HashMap<String, HashMap<Long, Long>> correlativeIds;
	
	
	public HashMap<String, HashMap<Long, Long>> getCorrelativeIds() {
		return correlativeIds;
	}
	public void setCorrelativeIds(
			HashMap<String, HashMap<Long, Long>> correlativeIds) {
		this.correlativeIds = correlativeIds;
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, List> getRelatedEntities() {
		return relatedEntities;
	}
	@SuppressWarnings("unchecked")
	public void setRelatedEntities(HashMap<String, List> relatedEntities) {
		this.relatedEntities = relatedEntities;
	}
	public Object getResponseData() {
		return responseData;
	}
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
	@SuppressWarnings("unchecked")
	public Class getResponseType() {
		return responseType;
	}
	@SuppressWarnings("unchecked")
	public void setResponseType(Class responseType) {
		this.responseType = responseType;
	}
	
}
