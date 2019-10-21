package com.interax.telephony.service.datamanagers;
import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItProviderPeer;

public class ItProviderPeerManager extends DataManager{

	public ItProviderPeerManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public ItProviderPeerManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new ItProviderPeer();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "it_provider_peer";
	}

}
