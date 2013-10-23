package org.imogene.encryption;

public class SecretKeyLoadingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static String DEFAULT_MSG = "An error occured loading the encryption key";
	
	public SecretKeyLoadingException(){
		super(DEFAULT_MSG);
	}
	
	public SecretKeyLoadingException(String msg){
		super(msg);
	}
	
	public SecretKeyLoadingException(Throwable th){
		super(DEFAULT_MSG, th);
	}
	
	public SecretKeyLoadingException(String msg, Throwable th){
		super(msg, th);
	}
	
}
