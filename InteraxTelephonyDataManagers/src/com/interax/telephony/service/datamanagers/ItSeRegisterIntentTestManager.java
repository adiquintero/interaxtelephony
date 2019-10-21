package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItSeRegisterIntent;

public class ItSeRegisterIntentTestManager extends DataManager
{

	public ItSeRegisterIntentTestManager(String dataSourceName) {
		super(dataSourceName);
	}

	public ItSeRegisterIntentTestManager(Connection controlledConnection) {
		super(controlledConnection);
	}
	
	@Override
	public Object getObject() {
		return new ItSeRegisterIntent();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_register_intent_test_result";
	}

}