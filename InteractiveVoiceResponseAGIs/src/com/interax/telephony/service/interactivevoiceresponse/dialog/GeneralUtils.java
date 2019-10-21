package com.interax.telephony.service.interactivevoiceresponse.dialog;

import java.util.Enumeration;
//import java.util.Hashtable;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GeneralUtils{

	public static HashMap<String, String> loadVoices() throws Exception
	 {
	  try
	   {
		// Se crea el ResourceBundle para leer el archivo de propiedades de voces disponibles
		ResourceBundle rb = ResourceBundle.getBundle("voice-languages");
		Enumeration<String> voices = rb.getKeys();
		HashMap<String, String> voiceLanguages = new HashMap<String, String>();
		  
		  while (voices.hasMoreElements()) {
			String voice = (String) voices.nextElement();
			System.out.println("VOICE: " + voice);
			voiceLanguages.put(voice, rb.getString(voice).trim());
		  }
		  return voiceLanguages;

	   	}
	  	catch(Exception e)
	  	{
	  		throw e;
	  	}
	 }
	
//    public static boolean vectorContainsEventType(Vector<GenericEvent> events, EventType eventType){
//    	
//    	for (GenericEvent genericEvent : events) {
//			
//    		Event event = (Event)genericEvent.getEvent();
//    		if(event.getEventType() == eventType)
//    			return true;
//		}
//    	return false;
//    }
//   
//	public static void manageFinishCondition(long customerId, long deliveryId, int attempt, Contact contact, ContactFinishedCondition contactFinishedCondition, String metaData, Dispatch dispatch, Vector<GenericEvent> clientEvents){
//		
//		if(dispatch.getPropertyValue(ChannelPropertyType.VOICE_CONTACT_FINISHED_CONDITION.name()).equals(contactFinishedCondition.name())){
//			if(!GeneralUtils.vectorContainsEventType(clientEvents, EventType.SCRIPT_COMPLETED)){
//				Event event = new Event();
//		        event.setContactId(contact.getId());
//		        event.setDeliveryId(deliveryId);
//		        event.setAttempt(attempt);
//		        event.setTimeZoneId(contact.getTimeZoneId());
//		        event.setCreationDate(new ContactManagerDate());
//		        event.setMetaData(metaData);
//		        event.setEventType(EventType.SCRIPT_COMPLETED);
//		        GenericEvent genericEvent = new GenericEvent();
//		        genericEvent.setCustomerId(customerId);
//		        genericEvent.setEvent(event);
//		        clientEvents.add(genericEvent);
//			}
//		}
//	}
	
}
