package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItEnterprise;

public class ItPeerManager extends DataManager
{

	public ItPeerManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public ItPeerManager(Connection controlledConnection) {
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
		return "it_peer";
	}

}