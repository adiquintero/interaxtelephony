package com.interax.remoting.security;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * The DHKeyManager class is used to agree a symmetrical cipher key between two parties via the Diffie-Hellam algorithm
 * @author Vicente Robles Bango
 */
public class DHKeyManager
 {
  private DHParameterSpec params;
  private KeyPair keyPair;
  private SecretKey secretKey;
	
  /**
   * Creates the prime numbers used as a seed for the Diffie-Hellman algorithm. It's a very resource-consuming method. It should be called by the party that initiates the agreement only.
   * @throws Exception
   */
  public void generateParams() throws Exception
   {
    AlgorithmParameterGenerator paramGenerator = AlgorithmParameterGenerator.getInstance("DH");
    paramGenerator.init(512);
    AlgorithmParameters genericParams = paramGenerator.generateParameters();
    this.params = (DHParameterSpec)genericParams.getParameterSpec(DHParameterSpec.class);
   }
  
  /**
   * Generates a DH Key pair to be used during the symmetrical cipher key agreement. 
   * @throws Exception
   */
  public void createKeyPair() throws Exception
   {
	KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
    keyPairGen.initialize(this.params);
    this.keyPair = keyPairGen.generateKeyPair();
   }

  /**
   * Returns the DH public key from this party 
   * @return The DH public key
   * @throws Exception
   */
  public byte[] getPublicKey()
   {
    return this.keyPair.getPublic().getEncoded();
   }

  /**
   * Returns the symmetrical cipher key agreed with the remote party 
   * @return The symmetrical cipher key
   * @throws Exception
   */
  public SecretKey getSecretKey()
   {
    return this.secretKey;
   }
  
  /**
   * This method is used by each party to generate a symmetrical cipher key based on it's own private DH key and the other party's public DH key.
   * @param remoteEncodedPublicKey - The remote party's public DH Key
   * @throws Exception
   */
  public void agreeKey(byte[] remoteEncodedPublicKey) throws Exception
   {
	KeyFactory keyFactory = KeyFactory.getInstance("DH");
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(remoteEncodedPublicKey);
    PublicKey remotePublicKey = keyFactory.generatePublic(x509KeySpec);

    if(this.keyPair == null)
	 {
      this.params = ((DHPublicKey) remotePublicKey).getParams();
	  KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
      keyPairGen.initialize(this.params);
      this.keyPair = keyPairGen.generateKeyPair();
	 }
      
    KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
    keyAgreement.init(this.keyPair.getPrivate());
    keyAgreement.doPhase(remotePublicKey, true);
      
    byte[] secret = keyAgreement.generateSecret();
    this.secretKey = new SecretKeySpec(secret, 0, 16, "AES");
   }
 
 }
