package com.interax.telephony.service.datamanagers;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;
import com.interax.telephony.service.data.RtVoiceMailUser;

public class RtVoiceMailUserManager extends DataManager
{

	public RtVoiceMailUserManager(String dataSourceName) {
		super(dataSourceName);
		init();
	}
	
	public RtVoiceMailUserManager(Connection controlledConnection) {
		super(controlledConnection);
		init();
	}
	
	private void init(){
		this.allowInsertUnsignedNegatives = true;
	}	

	@Override
	public Object getObject() {
		return new RtVoiceMailUser();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "rt_voice_mail_user";
	}

}