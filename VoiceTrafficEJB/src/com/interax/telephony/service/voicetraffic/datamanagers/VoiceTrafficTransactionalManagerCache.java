package com.interax.telephony.service.voicetraffic.datamanagers;

import java.util.Calendar;
//import java.util.Enumeration;
//import java.util.Hashtable;

import com.interax.date.InteraxDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class VoiceTrafficTransactionalManagerCache {

	private static HashMap<Long,VoiceTrafficTransactionalManager> managers;
	private static HashMap<Long,InteraxDate> lastUseDates;
	private static InteraxDate nextGarbageCollectionDate;
	
	
	public static synchronized VoiceTrafficTransactionalManager getManager(long enterpriseId)
	 {
	  InteraxDate now = new InteraxDate();
	  // Inicialización
	  if(managers==null)
	   {
		managers = new HashMap<Long, VoiceTrafficTransactionalManager>();
		lastUseDates = new HashMap<Long, InteraxDate>();
		nextGarbageCollectionDate = (InteraxDate) now.clone();
		nextGarbageCollectionDate.add(Calendar.HOUR, 1);
	   }
	  
	  if(!managers.containsKey(enterpriseId))
	   {
		//FIXME VALOR CABLEADO
		managers.put(enterpriseId, new VoiceTrafficTransactionalManager("jdbc/Asterisk"));
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
