package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItDid;

public class ItDidManager extends DataManager
{

	public ItDidManager(String dataSourceName) {
		super(dataSourceName);
	}

	public ItDidManager(Connection controlledConnection) {
		super(controlledConnection);
	}
	
	@Override
	public Object getObject() {
		return new ItDid();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_did";
	}

}