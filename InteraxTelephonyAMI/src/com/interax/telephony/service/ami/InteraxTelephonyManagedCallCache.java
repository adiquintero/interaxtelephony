package com.interax.telephony.service.ami;

//import java.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class InteraxTelephonyManagedCallCache {

	public List<InteraxTelephonyManagedCall> managedCalls;
	

	public InteraxTelephonyManagedCallCache(){
		super();
		this.managedCalls = new ArrayList<InteraxTelephonyManagedCall>();
	}
	
	public static InteraxTelephonyManagedCallCache load(){
		return new InteraxTelephonyManagedCallCache();
	}
  	 
}
