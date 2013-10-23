package org.imogene.lib.sync.client.encryption;

import java.io.InputStream;

import javax.crypto.SecretKey;

import org.imogene.encryption.EncryptionTools;
import org.imogene.encryption.KeyProvider;
import org.imogene.encryption.SecretKeyLoadingException;

public class DefaultSecretKeyProvider implements KeyProvider {

	@Override
	public SecretKey getKey() throws SecretKeyLoadingException {
		InputStream is = DefaultSecretKeyProvider.class.getResourceAsStream("/encryption.key");
		return EncryptionTools.loadSecretKeyFromStream(is);
	}

}
