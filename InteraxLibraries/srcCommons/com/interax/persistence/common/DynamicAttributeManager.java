package com.interax.persistence.common;

import java.sql.Connection;

import com.interax.persistence.datamanagers.DataManager;

public class DynamicAttributeManager extends DataManager 
{

	public DynamicAttributeManager(String dataSourceName) {
		super(dataSourceName);
	}
	
	public DynamicAttributeManager(Connection connection) {
		super(connection);
	}

	@Override
	public Object getObject() {
		return new DynamicAttribute();
	}

	@Override
	public String getPrimaryKey(Object obj) {
		DynamicAttribute dynAttr = (DynamicAttribute)obj;
		return "name='"+dynAttr.getName()+"' AND tableName='"+dynAttr.getTableName()+"'";
	}

	@Override
	public String getTableName() {
		return "dynamic_attribute";
	}
}
