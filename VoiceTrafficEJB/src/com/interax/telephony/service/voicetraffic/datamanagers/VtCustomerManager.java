package com.interax.telephony.service.voicetraffic.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.voicetraffic.data.VtCustomer;

public class VtCustomerManager extends DataManager
{

	public VtCustomerManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public VtCustomerManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new VtCustomer();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "vt_customer";
	}

}