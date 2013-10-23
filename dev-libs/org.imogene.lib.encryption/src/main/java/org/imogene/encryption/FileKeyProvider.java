package org.imogene.encryption;

import java.io.File;

import javax.crypto.SecretKey;

public class FileKeyProvider implements KeyProvider {

	private File keyFile;
	
	public FileKeyProvider(String fileName){
		keyFile = new File(fileName);
	}
	
	public FileKeyProvider(File file){
		keyFile = file;
	}
	
	@Override
	public SecretKey getKey() throws SecretKeyLoadingException {
			return EncryptionTools.loadSecretKeyFromFile(keyFile);
	}		

}
