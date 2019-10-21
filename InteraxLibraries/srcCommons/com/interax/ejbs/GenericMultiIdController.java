package com.interax.ejbs;

import java.util.List;
import java.util.Map;
//import java.util.Vector;

import javax.ejb.Remote;

@Remote
public interface GenericMultiIdController 
{
	public Object load(List<Object> objIds) throws Exception;
	
	public long add(Object obj) throws Exception;
	public long addBatch(List<Object> obj) throws Exception;
	
	public int update(Map<String,Object> keyPairs, List<Object> objIds) throws Exception;
	public int update(Map<String,Object> keyPairs, String whereClause) throws Exception;
	public int update(Object obj, List<Object> objIds) throws Exception;
	public int update(Object obj, String whereClause) throws Exception;
	
	public int delete(List<Object> objIds) throws Exception;
	public int delete(String whereClause) throws Exception;
	
	public List<Object> list(String whereClause) throws Exception;
	public List<Object> list(String whereClause, String orderClause) throws Exception;
	public List<Object> list(String whereClause, int begin, int count) throws Exception;
	public List<Object> list(String whereClause, String orderClause, int begin, int count) throws Exception;
	
	public List<Object> list(String whereClause, String groupClause, String orderClause) throws Exception;
	public List<Object> list(String fieldClause, String whereClause, String groupClause, String orderClause) throws Exception;
	
	public List<Object> search(String keyWords) throws Exception;
	public List<Object> search(String keyWords, String orderClause) throws Exception;
	public List<Object> search(String keyWords, int begin, int count) throws Exception;
	public List<Object> search(String keyWords, String orderClause, int begin, int count) throws Exception;
	public List<Object> search(String keyWords, String whereClause, String orderClause, int begin, int count) throws Exception;
	
	public long listCount(String whereClause) throws Exception;
	public long searchCount(String keyWords) throws Exception;
	public long searchCount(String keyWords, String whereClause) throws Exception;
}