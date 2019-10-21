package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItEnterprise;

public class ItEnterpriseManager extends DataManager
{

	public ItEnterpriseManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public ItEnterpriseManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}	
	
	@Override
	public Object getObject() {
		return new ItEnterprise();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_enterprise";
	}

}