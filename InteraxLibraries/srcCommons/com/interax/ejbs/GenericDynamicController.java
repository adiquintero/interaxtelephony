package com.interax.ejbs;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.interax.persistence.common.DynamicObject;

@Remote
public interface GenericDynamicController 
{
	public DynamicObject getDynamicAttributes() throws Exception;
	
	public DynamicObject load(long objId) throws Exception;
	
	public long add(DynamicObject obj) throws Exception;
	
	public int update(Map<String,Object> keyPairs, long objId) throws Exception;
	public int update(Map<String,Object> keyPairs, String whereClause) throws Exception;
	public int update(DynamicObject obj, long objId) throws Exception;
	public int update(DynamicObject obj, String whereClause) throws Exception;
	
	public int delete(int objId) throws Exception;
	public int delete(String whereClause) throws Exception;
	
	public List<DynamicObject> list(String whereClause) throws Exception;
	public List<DynamicObject> list(String whereClause, String orderClause) throws Exception;
	public List<DynamicObject> list(String whereClause, int begin, int count) throws Exception;
	public List<DynamicObject> list(String whereClause, String orderClause, int begin, int count) throws Exception;
	public List<DynamicObject> list(String whereClause, String orderClause, int begin, int count, String tableAlias) throws Exception;
	
	public List<DynamicObject> search(String keyWords) throws Exception;
	public List<DynamicObject> search(String keyWords, String orderClause) throws Exception;
	public List<DynamicObject> search(String keyWords, int begin, int count) throws Exception;
	public List<DynamicObject> search(String keyWords, String orderClause, int begin, int count) throws Exception;
	public List<DynamicObject> search(String keyWords, String whereClause, String orderClause, int begin, int count) throws Exception;
	
	public long listCount(String whereClause) throws Exception;
	public long listCount(String whereClause, String tableAlias) throws Exception;
	public long searchCount(String keyWords) throws Exception;
	public long searchCount(String keyWords, String whereClause) throws Exception;
	
	public List<DynamicObject> listJoin(String whereClause, String orderClause, int begin, int count, String joinType, String joinTable, String joinCondition) throws Exception;
	public List<DynamicObject> listJoin(String whereClause, String orderClause, int begin, int count, String joinType, String joinTable, String joinCondition, String tableAlias) throws Exception;
	public long listJoinCount(String whereClause, String joinType, String joinTable, String joinCondition) throws Exception;
	public long listJoinCount(String whereClause, String joinType, String joinTable, String joinCondition, String tableAlias) throws Exception;
	
}