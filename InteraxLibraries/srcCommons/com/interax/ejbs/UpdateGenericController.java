package com.interax.ejbs;

import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface UpdateGenericController 
{
	
	public int update(Map<String,Object> keyPairs, long objId) throws Exception;
	public int update(Map<String,Object> keyPairs, String whereClause) throws Exception;
	public int update(Object obj, long objId) throws Exception;
	public int update(Object obj, String whereClause) throws Exception;
	
	public int delete(int objId) throws Exception;
	public int delete(String whereClause) throws Exception;
	
}