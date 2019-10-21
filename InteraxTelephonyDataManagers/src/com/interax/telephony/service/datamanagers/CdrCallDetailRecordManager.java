package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.CdrCallDetailRecord;

public class CdrCallDetailRecordManager extends DataManager
{

	public CdrCallDetailRecordManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public CdrCallDetailRecordManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}
	
	@Override
	public Object getObject() {
		return new CdrCallDetailRecord();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "cdr_call_detail_record";
	}

}