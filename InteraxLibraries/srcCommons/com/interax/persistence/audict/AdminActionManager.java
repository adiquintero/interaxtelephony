package com.interax.persistence.audict;

import com.interax.persistence.datamanagers.DataManager;

public class AdminActionManager extends DataManager
{
	public AdminActionManager(String dataSourceName) {
		super(dataSourceName);
	}

	@Override
	public Object getObject() {
		return new AdminAction();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		return "id='"+obj+"'";
	}

	@Override
	public String getTableName() {
		return "admin_action";
	}
	
}
