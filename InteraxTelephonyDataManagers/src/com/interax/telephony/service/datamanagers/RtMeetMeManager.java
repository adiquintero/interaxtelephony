package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.RtExtension;

public class RtMeetMeManager extends DataManager
{

	public RtMeetMeManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}
	
	public RtMeetMeManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new RtExtension();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "confno='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "rt_meetme";
	}

}