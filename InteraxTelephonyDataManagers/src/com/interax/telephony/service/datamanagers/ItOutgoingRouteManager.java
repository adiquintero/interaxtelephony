package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItProvider;
import com.interax.telephony.service.data.ItOutgoingRoute;

public class ItOutgoingRouteManager extends DataManager{
	public ItOutgoingRouteManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public ItOutgoingRouteManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new ItOutgoingRoute();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_outgoing_route";
	}


}
