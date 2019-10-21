package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItProvider;

public class ItProviderManager extends DataManager {
	
	public ItProviderManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public ItProviderManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new ItProvider();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_provider";
	}

}
