package org.imogene.lib.sync.uao.security;

public class ImogSecurityHandler {

	private ImogSecurityPolicy policy;

	/* The unique instance of this object */
	private static ImogSecurityHandler instance = null;

	public static ImogSecurityHandler getInstance() {
		if (instance == null)
			instance = new ImogSecurityHandler();
		return instance;
	}

	public ImogSecurityPolicy getPolicy() {
		return policy;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param policy
	 */
	public void setImogPolicy(ImogSecurityPolicy policy) {
		this.policy = policy;
	}

}
