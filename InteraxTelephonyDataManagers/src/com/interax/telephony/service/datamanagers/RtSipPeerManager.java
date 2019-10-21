package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.RtSipPeer;

public class RtSipPeerManager extends DataManager
{

	public RtSipPeerManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}
	
	public RtSipPeerManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new RtSipPeer();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "rt_sip_peer";
	}

}