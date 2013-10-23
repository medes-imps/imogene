package org.imogene.encryption;

import javax.crypto.SecretKey;

public interface KeyProvider {

	public SecretKey getKey() throws SecretKeyLoadingException ;
	
}
