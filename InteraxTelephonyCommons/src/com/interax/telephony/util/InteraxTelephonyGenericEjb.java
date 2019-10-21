package com.interax.telephony.util;
import javax.ejb.Remote;

@Remote
public interface InteraxTelephonyGenericEjb {
	public boolean echo() throws Exception;
	
}
