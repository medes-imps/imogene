package org.imogene.android.util.encryption;

import android.content.Context;


public class EncryptionManager implements EncryptionProvider {

	private static EncryptionManager instance = null;
	
	private EncryptionProvider proxy;
	
	private EncryptionManager(Context context){
		proxy= new DESedeProvider(context);
	}
	
	public static EncryptionManager getInstance(Context context){
		if(instance==null)
			instance = new EncryptionManager(context);
		return instance;
	}

	public void setProxy(EncryptionProvider proxy) {
		this.proxy = proxy;
	}
	
	public String getProviderName(){
		return proxy.getName();
	}

	@Override
	public byte[] decrypt(byte[] encrypted) {
		return proxy.decrypt(encrypted);
	}

	@Override
	public byte[] encrypt(byte[] decrypted) {
		return proxy.encrypt(decrypted);
	}

	@Override
	public String getName() {
		return proxy.getName();
	}	
}
