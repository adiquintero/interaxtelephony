package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItOutgoingRouteStep;

public class ItOutgoingRouteStepManager extends DataManager{
	
	public ItOutgoingRouteStepManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public ItOutgoingRouteStepManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new ItOutgoingRouteStep();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_outgoing_route_step";
	}

}
