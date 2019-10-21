package com.interax.ejbs;

import javax.ejb.Remote;

@Remote
public interface GenericController extends ReadGenericController, AddGenericController, UpdateGenericController 
{
	//TODO: poner una función dummy llamada echo para verificar que la referencia al controller está OK.
}