package com.interax.ejbs;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AddGenericController 
{
	public long add(Object obj) throws Exception;
	public long addBatch(List<Object> obj) throws Exception;
	
}