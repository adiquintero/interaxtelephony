package com.interax.telephony.service.callingcard.agi;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import com.interax.telephony.server.agi.InteraxTelephonyAgiServer;
import com.interax.telephony.service.callingcard.data.CallingCardAccessType;
import com.interax.telephony.service.callingcard.data.CallingCardServiceType;
import com.interax.telephony.util.InteraxTelephonyConfig;


public class CallingCardAgiConfig extends InteraxTelephonyConfig {


	//	public final Vector<String> DNIDS;
	public final Long ENTERPRISE_ID;
	public final String ENTERPRISE_NAME;
	public final Vector<CallingCardServiceType> SERVICE_TYPES;
	public final CallingCardAccessType ACCESS_TYPE;
	public final String DIAL_PROTOCOL;
	public final String DIAL_PREFIX;
	public final String DIAL_TEST_PREFIX;
	public final String DIAL_GATEWAY;
	public final String DIAL_TEST_GATEWAY;
	public final String DIAL_TIMEOUT;
	public final String AUDIO_FOLDER;
	public final String[] LANGUAGES;
	public final Vector<CallingCardAgiInputPrefix> INPUT_PREFIXES;
	
	
	public final int LANGUAGE_MAX_ATTEMPTS;
	public final int LANGUAGE_DIGIT_TIMEOUT;
	public final int AUTHENTICATION_MAX_ATTEMPTS;
	public final int AUTHENTICATION_DIGIT_TIMEOUT;
	public final int MAX_PIN_SECRET_LENGTH;
	public final int ASK_PASSWORD_DIGIT_TIMEOUT;
	public final int ASK_PASSWORD_MAX_ATTEMPTS;
	public final int CHANGE_PASSWORD_DIGIT_TIMEOUT;
	public final int CHANGE_PASSWORD_MAX_ATTEMPTS;
	public final int MAX_PIN_PASSWORD_LENGTH;
	public final int SPECIAL_MENU_MAX_ATTEMPTS;
	public final int SPECIAL_MENU_DIGIT_TIMEOUT;
	public final int VALIDATE_DESTINY_MAX_ATTEMPTS;
	public final int VALIDATE_DESTINY_DIGIT_TIMEOUT;
	
	
	public final String CONFERENCE_APPLICATION;
	public final String CONFERENCE_TIMEOUT;

	public final String RATER_EJB_SERVER;
	public final String RATER_EJB_PORT;
	public final String PIN_EJB_SERVER;
	public final String PIN_EJB_PORT;
	
	public final int UNLIMITED_CALLS_MAX_MINUTES;
	public final int UNLIMITED_CALLS_RANDOM_MINUTES;
	public final String MASK_ANI_LIST;
	public final Boolean IS_MASK_ANI; 
	
	
	public CallingCardAgiConfig(ResourceBundle rb)  throws MissingResourceException, NumberFormatException {
		
//		this.DNIDS = new Vector<String>(Arrays.asList(rb.getString("DNIDS").split(",")));
		this.ENTERPRISE_ID = Long.parseLong(rb.getString("ENTERPRISE_ID"));
		this.ENTERPRISE_NAME = rb.getString("ENTERPRISE_NAME");
		this.SERVICE_TYPES = new Vector<CallingCardServiceType>();
		String[] serviceTypes = rb.getString("SERVICE_TYPES").split(",");
		for(int i=0; i<serviceTypes.length; i++){
			CallingCardServiceType serviceType = CallingCardServiceType.valueOf(serviceTypes[i]);
			this.SERVICE_TYPES.add(serviceType);
		}
		this.ACCESS_TYPE = CallingCardAccessType.valueOf(rb.getString(("ACCESS_TYPE")));
		this.INPUT_PREFIXES = new Vector<CallingCardAgiInputPrefix>();
		String[] prefixes = rb.getString("INPUT_PREFIXES").split(",");
		for(int i=0; i<prefixes.length; i++){
			String[] data = prefixes[i].split("->");
			CallingCardAgiInputPrefix inputPrefix = new CallingCardAgiInputPrefix();
			inputPrefix.setPrefix(data[0]);
			if(data.length>1)
				inputPrefix.setPadding(data[1]);
			else
				inputPrefix.setPadding("");
			this.INPUT_PREFIXES.add(inputPrefix);
		}
		this.DIAL_PROTOCOL = rb.getString("DIAL_PROTOCOL");
		this.DIAL_PREFIX = rb.getString("DIAL_PREFIX");		
		this.DIAL_TEST_PREFIX = rb.getString("DIAL_TEST_PREFIX");
		this.DIAL_GATEWAY = rb.getString("DIAL_GATEWAY");
		this.DIAL_TEST_GATEWAY = rb.getString("DIAL_TEST_GATEWAY");
		this.DIAL_TIMEOUT = rb.getString("DIAL_TIMEOUT");
		this.AUDIO_FOLDER = rb.getString("AUDIO_FOLDER");
		this.LANGUAGES = rb.getString("LANGUAGES").split(",");	
			
		switch(this.ACCESS_TYPE){
			case IVR_PIN_FREE:
			case IVR_MOBIL:
			case IVR:
				  this.LANGUAGE_MAX_ATTEMPTS = Integer.parseInt(rb.getString("LANGUAGE_MAX_ATTEMPTS"));
				  this.LANGUAGE_DIGIT_TIMEOUT = Integer.parseInt(rb.getString("LANGUAGE_DIGIT_TIMEOUT"));
				  this.AUTHENTICATION_MAX_ATTEMPTS = Integer.parseInt(rb.getString("AUTHENTICATION_MAX_ATTEMPTS"));
				  this.AUTHENTICATION_DIGIT_TIMEOUT = Integer.parseInt(rb.getString("AUTHENTICATION_DIGIT_TIMEOUT"));
				  this.MAX_PIN_SECRET_LENGTH = Integer.parseInt(rb.getString("MAX_PIN_SECRET_LENGTH"));
				  this.ASK_PASSWORD_DIGIT_TIMEOUT = Integer.parseInt(rb.getString("ASK_PASSWORD_DIGIT_TIMEOUT"));
				  this.ASK_PASSWORD_MAX_ATTEMPTS = Integer.parseInt(rb.getString("ASK_PASSWORD_MAX_ATTEMPTS"));
				  this.CHANGE_PASSWORD_DIGIT_TIMEOUT = Integer.parseInt(rb.getString("CHANGE_PASSWORD_DIGIT_TIMEOUT"));
				  this.CHANGE_PASSWORD_MAX_ATTEMPTS = Integer.parseInt(rb.getString("CHANGE_PASSWORD_MAX_ATTEMPTS"));
				  this.MAX_PIN_PASSWORD_LENGTH = Integer.parseInt(rb.getString("MAX_PIN_PASSWORD_LENGTH"));
				  this.SPECIAL_MENU_MAX_ATTEMPTS = Integer.parseInt(rb.getString("SPECIAL_MENU_MAX_ATTEMPTS"));
				  this.SPECIAL_MENU_DIGIT_TIMEOUT = Integer.parseInt(rb.getString("SPECIAL_MENU_DIGIT_TIMEOUT"));
				  this.VALIDATE_DESTINY_MAX_ATTEMPTS = Integer.parseInt(rb.getString("VALIDATE_DESTINY_MAX_ATTEMPTS"));
				  this.VALIDATE_DESTINY_DIGIT_TIMEOUT = Integer.parseInt(rb.getString("VALIDATE_DESTINY_DIGIT_TIMEOUT"));
				  this.CONFERENCE_APPLICATION = null;
				  this.CONFERENCE_TIMEOUT = null; 
			break;

			case WEB_CONFERENCE:
				  this.CONFERENCE_APPLICATION = rb.getString("CONFERENCE_APPLICATION");
				  this.CONFERENCE_TIMEOUT = rb.getString("CONFERENCE_TIMEOUT"); 
				  this.LANGUAGE_MAX_ATTEMPTS = 0;
				  this.LANGUAGE_DIGIT_TIMEOUT = 0;
				  this.AUTHENTICATION_MAX_ATTEMPTS = 0;
				  this.AUTHENTICATION_DIGIT_TIMEOUT = 0;
				  this.MAX_PIN_SECRET_LENGTH = 0;
				  this.ASK_PASSWORD_DIGIT_TIMEOUT = 0;
				  this.ASK_PASSWORD_MAX_ATTEMPTS = 0;
				  this.CHANGE_PASSWORD_DIGIT_TIMEOUT = 0;
				  this.CHANGE_PASSWORD_MAX_ATTEMPTS = 0;
				  this.MAX_PIN_PASSWORD_LENGTH = 0;
				  this.SPECIAL_MENU_MAX_ATTEMPTS = 0;
				  this.SPECIAL_MENU_DIGIT_TIMEOUT = 0;
				  this.VALIDATE_DESTINY_MAX_ATTEMPTS = 1;
				  this.VALIDATE_DESTINY_DIGIT_TIMEOUT = 0;
			break;
			
			default:
				  this.LANGUAGE_MAX_ATTEMPTS = 0;
				  this.LANGUAGE_DIGIT_TIMEOUT = 0;
				  this.AUTHENTICATION_MAX_ATTEMPTS = 0;
				  this.AUTHENTICATION_DIGIT_TIMEOUT = 0;
				  this.MAX_PIN_SECRET_LENGTH = 0;
				  this.ASK_PASSWORD_DIGIT_TIMEOUT = 0;
				  this.ASK_PASSWORD_MAX_ATTEMPTS = 0;
				  this.CHANGE_PASSWORD_DIGIT_TIMEOUT = 0;
				  this.CHANGE_PASSWORD_MAX_ATTEMPTS = 0;
				  this.MAX_PIN_PASSWORD_LENGTH = 0;
				  this.SPECIAL_MENU_MAX_ATTEMPTS = 0;
				  this.SPECIAL_MENU_DIGIT_TIMEOUT = 0;
				  this.VALIDATE_DESTINY_MAX_ATTEMPTS = 1;
				  this.VALIDATE_DESTINY_DIGIT_TIMEOUT = 0;
				  this.CONFERENCE_APPLICATION = null;
				  this.CONFERENCE_TIMEOUT = null; 
				 
			break;
		}
		
		this.RATER_EJB_SERVER = rb.getString("RATER_EJB_SERVER");
		this.RATER_EJB_PORT = rb.getString("RATER_EJB_PORT");
		this.PIN_EJB_SERVER = rb.getString("PIN_EJB_SERVER");
		this.PIN_EJB_PORT = rb.getString("PIN_EJB_PORT");
		
		this.UNLIMITED_CALLS_MAX_MINUTES = Integer.parseInt(rb.getString("UNLIMITED_CALLS_MAX_MINUTES"));
		this.UNLIMITED_CALLS_RANDOM_MINUTES = Integer.parseInt(rb.getString("UNLIMITED_CALLS_RANDOM_MINUTES"));
		this.IS_MASK_ANI = Boolean.parseBoolean(rb.getString("IS_MASK_ANI"));
		this.MASK_ANI_LIST = rb.getString("MASK_ANI_LIST");
	}
	
	public CallingCardAgiInputPrefix findInputPrefix(String dni){
		
		int prefixCount = this.INPUT_PREFIXES.size();
		for(int i=0; i<prefixCount; i++){
			CallingCardAgiInputPrefix currentPrefix = this.INPUT_PREFIXES.get(i);
			if(dni.startsWith(currentPrefix.getPrefix()) || InteraxTelephonyAgiServer.getConfig().DEFAULT_INPUT_PREFIX.equals(currentPrefix.getPrefix())) 
				return currentPrefix;
		}
		
		return null;
	}
}
