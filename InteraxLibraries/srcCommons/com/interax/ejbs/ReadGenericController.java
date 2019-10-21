package com.interax.ejbs;

//import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
//import java.util.Vector;

import javax.ejb.Remote;

@Remote
public interface ReadGenericController
{
	
	public Object load(long objId) throws Exception;
	
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
	
	
	
	public Double listSum(String field, String whereClause) throws Exception ;
	public Double listSum(String field, String whereClause, int begin, int count) throws Exception ;
	public Double listSum(String field, String whereClause, String orderClause, int begin, int count) throws Exception ;
	
	public HashMap<String, Double> listSum(List<String> fields, String whereClause) throws Exception ;
	public HashMap<String, Double> listSum(List<String> fields, String whereClause, int begin, int count) throws Exception ;
	public HashMap<String, Double> listSum(List<String> fields, String whereClause, String orderClause, int begin, int count) throws Exception ;
	
	public Double searchSum(String field, String keyWords) throws Exception ;
	public Double searchSum(String field, String keyWords, String whereClause) throws Exception ;
	public Double searchSum(String field, String keyWords, String whereClause, int begin, int count) throws Exception ;
	
	public HashMap<String, Double> searchSum(List<String> fields, String keyWords) throws Exception ;
	public HashMap<String, Double> searchSum(List<String> fields, String keyWords, String whereClause) throws Exception ;
	public HashMap<String, Double> searchSum(List<String> fields, String keyWords, String whereClause, int ini, int fin) throws Exception ;
	
	/*
	 * NOTA:ESTE METODO SE PUBLICÓ PARA LA OBTENCION DEL WHERECLAUSE DADO UN KEYWORDS 
	 * 		PARA LA GENERACIÓN DE UN REPORTE MEDIANTE EL MODULO DE MISMO   
	*/
	public String getWhereClauseFromKeyWords(String keyWords) throws Exception;
}