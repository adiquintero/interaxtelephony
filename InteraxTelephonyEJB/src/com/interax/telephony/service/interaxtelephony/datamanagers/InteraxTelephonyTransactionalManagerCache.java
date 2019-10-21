package com.interax.telephony.service.interaxtelephony.datamanagers;

import java.util.Calendar;
//import java.util.Enumeration;
//import java.util.Hashtable;

import com.interax.date.InteraxDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class InteraxTelephonyTransactionalManagerCache {

	private static HashMap<Long,InteraxTelephonyTransactionalManager> managers;
	private static HashMap<Long,InteraxDate> lastUseDates;
	private static InteraxDate nextGarbageCollectionDate;
	
	
	public static synchronized InteraxTelephonyTransactionalManager getManager(long enterpriseId)
	 {
	  InteraxDate now = new InteraxDate();
	  // Inicialización
	  if(managers==null)
	   {
		managers = new HashMap<Long, InteraxTelephonyTransactionalManager>();
		lastUseDates = new HashMap<Long, InteraxDate>();
		nextGarbageCollectionDate = (InteraxDate) now.clone();
		nextGarbageCollectionDate.add(Calendar.HOUR, 1);
	   }
	  
	  if(!managers.containsKey(enterpriseId))
	   {
		//FIXME VALOR CABLEADO
		managers.put(enterpriseId, new InteraxTelephonyTransactionalManager("jdbc/Asterisk"));
	   }
	  
	  lastUseDates.put(enterpriseId, now);
	  if(now.after(nextGarbageCollectionDate))
	   {
		InteraxDate limitDate = (InteraxDate) now.clone();
		limitDate.add(Calendar.HOUR, -1);
                Set<Long> customerIds = managers.keySet();
                Iterator<Long> iterator = customerIds.iterator();
		while(iterator.hasNext())
		 {
		  long currentCustomerId = (Long)iterator.next();
		  if(lastUseDates.get(currentCustomerId).before(limitDate))
		   {
			managers.remove(currentCustomerId);
			lastUseDates.remove(currentCustomerId);
		   }
		 }
		
		nextGarbageCollectionDate.add(Calendar.HOUR, 1);
	   }
	  
	  return managers.get(enterpriseId);
	 }
}
