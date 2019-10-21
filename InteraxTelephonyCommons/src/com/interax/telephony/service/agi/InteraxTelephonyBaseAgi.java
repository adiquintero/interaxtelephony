package com.interax.telephony.service.agi;

import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.BaseAgiScript;

public abstract class InteraxTelephonyBaseAgi extends BaseAgiScript{


	public String createDialOption(String serviceFamily,  String serviceType, String accessType, Long enterpriseId, String dni, int priority ){
		String dialOptions = null;
		try {
			dialOptions = getFullVariable("${ODBC_IT_GET_OUTGOING_ROUTE_BY_PRIORITY("+serviceFamily+"|"+serviceType+"|"+ accessType +"|"+enterpriseId+"|"+dni+"|"+priority+")}");
			if(dialOptions.isEmpty()){
				dialOptions = null;
			}
			System.out.println(dialOptions);
		} catch (AgiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		return dialOptions; 
	}

	public String createDialOption(String serviceFamily,  String serviceType, String accessType, Long enterpriseId, String dni){

		String dialOptions = null;

		try {

			dialOptions = getFullVariable("${ODBC_IT_GET_OUTGOING_ROUTE("+serviceFamily+"|"+serviceType+"|"+ accessType +"|"+enterpriseId+"|"+dni+")}");

		} catch (AgiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dialOptions; 
	}
}
