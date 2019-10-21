package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItSeIpBlackList;


public class ItSeIpBlackListTestManager extends DataManager
{

	public ItSeIpBlackListTestManager(String dataSourceName) {
		super(dataSourceName);
	}

	public ItSeIpBlackListTestManager(Connection controlledConnection) {
		super(controlledConnection);
	}
	
	@Override
	public Object getObject() {
		return new ItSeIpBlackList();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_ip_blacklist_test_result";
	}

}
