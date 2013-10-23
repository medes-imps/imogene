package org.imogene.encryption;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * @author MEDES-IMPS
 *
 */
public class EncryptionManager {

	private static EncryptionManager instance = null;
	
	private static String DEFAULT_ERR_MSG = "EncryptionManager not initialized.";
	
	private EncryptionProvider proxy;
	
	private boolean initialized=false;
	
	/**
	 * 
	 */
	private EncryptionManager(){				
				//proxy= new DESedeProvider();
	}
	
	/**
	 * 
	 * @return
	 */
	public static EncryptionManager getInstance(){
		if(instance==null)
			instance = new EncryptionManager();
		return instance;
	}
	
	public void setKeyProvider(KeyProvider kp){
		proxy = new DESedeProvider(kp);
		initialized=true;
	}

	/**
	 * 
	 * @param proxy
	 */
	public void setProxy(EncryptionProvider proxy) {
		this.proxy = proxy;
	}
	
	/**
	 * De-crypt the specified bytes
	 * @param encrypted teh encrypted bytes
	 * @return uncrypted bytes
	 */
	public byte[] decrypt(byte[] encrypted) {
		if(initialized)
			return proxy.decrypt(encrypted);
		else
			throw new RuntimeException(DEFAULT_ERR_MSG);
	}

	/**
	 * Crypt the specified bytes
	 * @param encrypted teh uncrypted bytes
	 * @return encrypted bytes
	 */
	public byte[] encrypt(byte[] decrypted) {
		if(initialized)
			return proxy.encrypt(decrypted);
		else
			throw new RuntimeException(DEFAULT_ERR_MSG);
	}
	
	public InputStream getDecryptedInputStream(InputStream in){
		if(initialized)
			return proxy.getDecryptedInputStream(in);
		else
			throw new RuntimeException(DEFAULT_ERR_MSG);
	}
	
	public OutputStream getEncryptedOutputStream(OutputStream out){
		if(initialized)
			return proxy.getEncryptedOutputStream(out);
		else
			throw new RuntimeException(DEFAULT_ERR_MSG);
	}
	
	/**
	 * Return the name of the current provider used
	 * 
	 * @return the name of the provider
	 */
	public String getProviderName(){
		return proxy.getName();
	}
	
	
	
}
