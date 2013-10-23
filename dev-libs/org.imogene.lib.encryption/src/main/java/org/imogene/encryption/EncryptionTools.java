package org.imogene.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class EncryptionTools {

	/**
	 * Load a secret key from the specified file
	 * 
	 * @param fileName the key file name
	 * @return the secret key
	 */
	public static  SecretKey loadSecretKeyFromFile(File file) throws SecretKeyLoadingException {		
		try{
			return loadSecretKeyFromStream(new FileInputStream(file));
		}catch (FileNotFoundException fne) {
			throw new SecretKeyLoadingException(fne);
		}
	}
	
	/**
	 * Load a secret key from the specified stream
	 * 
	 * @param is the input stream to read
	 * @return the secret key
	 */
	public static SecretKey loadSecretKeyFromStream(InputStream is) throws SecretKeyLoadingException {
		/* read the key from file */		
		byte[] tripleDesKeyData = new byte[24];
		try{
			is.read(tripleDesKeyData);	
			is.close();
			return loadSecretKey(tripleDesKeyData);
		}catch(IOException e){
			throw new SecretKeyLoadingException(e);
		}
	}
	
		
	/**
	 * Load a secret key from raw data
	 * 
	 * @param key raw data
	 * @return the secret key
	 */
	public static SecretKey loadSecretKey(byte[] rawKey) throws SecretKeyLoadingException {		
		
		/* create the key from raw data read */	
		SecretKey myTripleDesKey=null;
		try{
			DESedeKeySpec myTripleDesKeySpec = new DESedeKeySpec(rawKey);			
			myTripleDesKey = SecretKeyFactory.getInstance("DESede").generateSecret(myTripleDesKeySpec);
			return myTripleDesKey;
		}catch (Exception e) {
			throw new SecretKeyLoadingException(e);
		}
	}
}
