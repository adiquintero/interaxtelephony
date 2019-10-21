package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItSeRegisterDetails;

public class ItSeRegisterDetailsTestManager extends DataManager
{

	public ItSeRegisterDetailsTestManager(String dataSourceName) {
		super(dataSourceName);
	}

	public ItSeRegisterDetailsTestManager(Connection controlledConnection) {
		super(controlledConnection);
	}
	
	@Override
	public Object getObject() {
		return new ItSeRegisterDetails();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_register_details_test_result";
	}

}