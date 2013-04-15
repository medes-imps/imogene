package org.imogene.android.util.encryption;


public interface EncryptionProvider {

	public String getName();
	
	public byte[] encrypt(byte[] decrypted);
	
	public byte[] decrypt(byte[] encrypted);
}
