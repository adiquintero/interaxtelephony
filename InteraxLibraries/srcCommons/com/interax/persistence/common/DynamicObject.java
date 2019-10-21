package com.interax.persistence.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public abstract class DynamicObject implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	protected Map<String, DynamicAttribute> dynamicAttributes;
	protected Map<String, Object> dynamicValues;
	protected Map<String, String> displayNamesToNames;
	
	
	public DynamicObject() {
		dynamicAttributes = new TreeMap<String,DynamicAttribute>();
		dynamicValues = new TreeMap<String,Object>();
		displayNamesToNames = new TreeMap<String, String>();
	}
	
	public String getNameByDisplayName(String displayName)
	{
		return displayNamesToNames.get(displayName);
	}
	
	public DynamicAttribute getProperty(String name)
	{
		return dynamicAttributes.get(name);
	}
	public void setProperty(String name, DynamicAttribute dynAttr)
	{
		dynamicAttributes.put(name, dynAttr);
	}
	
	public Object getPropertyValue(String name)
	{
		return dynamicValues.get(name);
	}
	
	public void setPropertyValue(String name, Object value)
	{
		dynamicValues.put(name, value);
	}

	public List<String> getPropertyNames()
	{
		return new ArrayList<String>(dynamicAttributes.keySet());
	}

		
	public List<String> getPropertyDisplayNames()
	{
		List<String> names = getPropertyNames();
		List<String> ret = null;
		if(names!=null)
		{
			ret = new ArrayList<String>();
			for(int i=0;i<names.size();i++)
			{
				ret.add(dynamicAttributes.get(names.get(i)).getDisplayName());
			}			
		}
		return ret;
	}
	
	public Map<String,DynamicAttribute> getProperties()
	{
		return dynamicAttributes;
	}


	public void setProperties(Map<String, DynamicAttribute> dynamicAttributes) {
		this.dynamicAttributes = dynamicAttributes;
	}
	public void setDisplayNamesToNames(Map<String, String> displayNamesToNames)
	{
		this.displayNamesToNames = displayNamesToNames;
	}
	
}
