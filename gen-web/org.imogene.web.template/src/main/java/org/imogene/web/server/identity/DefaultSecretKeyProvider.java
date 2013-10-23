package org.imogene.web.server.identity;

import java.io.File;

import javax.crypto.SecretKey;
import javax.servlet.ServletContext;

import org.imogene.encryption.EncryptionTools;
import org.imogene.encryption.KeyProvider;
import org.imogene.encryption.SecretKeyLoadingException;
import org.springframework.web.context.ServletContextAware;

public class DefaultSecretKeyProvider implements KeyProvider, ServletContextAware {
	
	private String DEFAULT_KEY = "WEB-INF/encryption.key";
	
	private static ServletContext context;

	@Override
	public SecretKey getKey() throws SecretKeyLoadingException {
		String realPath =  context.getRealPath(DEFAULT_KEY);
		return EncryptionTools.loadSecretKeyFromFile(new File(realPath));
	}

	@Override
	public void setServletContext(ServletContext ctx) {
		context = ctx;
	}

}
