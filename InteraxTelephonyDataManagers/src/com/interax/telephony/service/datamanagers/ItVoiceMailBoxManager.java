package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.ItEnterprise;

public class ItVoiceMailBoxManager extends DataManager
{

	public ItVoiceMailBoxManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}

	public ItVoiceMailBoxManager(Connection controlledConnection) {
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
		return "it_voice_mail_box";
	}

}