package org.imogene.android.util.encryption;

import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.imogene.android.template.R;

import android.content.Context;

public class DESedeProvider implements EncryptionProvider {

	private static final String name = "DESedeProvider";

	private final SecretKey key;

	/**
	 * Constructor
	 */
	public DESedeProvider(Context context) {
		key = loadSecretKeyFromFile(context);
	}

	@Override
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

	/**
	 * Load a secret key from the specified file
	 * 
	 * @param fileName
	 *            the key file name
	 * @return the secret key
	 */
	private SecretKey loadSecretKeyFromFile(Context context) {

		/* read the key from file */
		InputStream is = context.getResources().openRawResource(R.raw.ig_encryption);
		byte[] tripleDesKeyData = new byte[24];
		try {
			is.read(tripleDesKeyData);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		/* create the key from raw data read */
		SecretKey myTripleDesKey = null;
		try {
			DESedeKeySpec myTripleDesKeySpec = new DESedeKeySpec(tripleDesKeyData);
			myTripleDesKey = SecretKeyFactory.getInstance("DESede").generateSecret(myTripleDesKeySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return myTripleDesKey;
	}

}
