package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.RtIaxPeer;

public class RtIaxPeerManager extends DataManager
{

	public RtIaxPeerManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public RtIaxPeerManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new RtIaxPeer();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "rt_iax_peer";
	}
	
}