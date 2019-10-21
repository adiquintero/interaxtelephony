package com.interax.remoting;

import javax.crypto.SealedObject;
import javax.ejb.Remote;

@Remote
public interface InvokeInterface {
	public SealedObject invoke(SealedObject object) throws Exception;
	
	
	public byte[] authenticate(SessionInfo sessionInfo, byte[] publicKey) throws Exception;
}
