package org.imogene.encryption;

import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;


/**
 * Implements a DESede medany store security provider.
 * 
 * @author MEDES-IMPS
 *
 */
public class DESedeProvider implements EncryptionProvider {

	
	/* default key file name */
	//private static String DEFAULT_KEY = "default.key";
	
	/* the secret key */
	private SecretKey key;
	
	/* the name of the provider */
	private final String name = "DESedeProvider";
	
	/**
	 * Constructor
	 */
	/*public DESedeProvider(){
		key = loadSecretKeyFromFile(DEFAULT_KEY);
	}*/
	
	public DESedeProvider(KeyProvider kp){
		key = kp.getKey();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.anwrt.medany.store.impl.SecurityProvider#getName()
	 */
	public String getName() {
		
		return name;
	}
	
	@Override
	public byte[] decrypt(byte[] encrypted) {
		try {
			Cipher dCipher = Cipher.getInstance("DESede");
			dCipher.init(Cipher.DECRYPT_MODE, key);
			return dCipher.doFinal(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] encrypt(byte[] decrypted) {
		try {
			Cipher eCipher = Cipher.getInstance("DESede");
			eCipher.init(Cipher.ENCRYPT_MODE, key);
			return eCipher.doFinal(decrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public InputStream getDecryptedInputStream(InputStream in) {
		try{
			Cipher dCipher = Cipher.getInstance("DESede");			
			dCipher.init(Cipher.DECRYPT_MODE, key);		
			return new CipherInputStream(in, dCipher);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public OutputStream getEncryptedOutputStream(OutputStream out) {
		try{
			Cipher eCipher = Cipher.getInstance("DESede");			
			eCipher.init(Cipher.ENCRYPT_MODE, key);
			return new CipherOutputStream(out, eCipher);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

}
