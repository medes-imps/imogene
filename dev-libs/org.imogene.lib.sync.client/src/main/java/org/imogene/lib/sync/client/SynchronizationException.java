package org.imogene.lib.sync.client;

public class SynchronizationException extends Exception {

	public final static int DEFAULT_ERROR = 0;

	public final static int ERROR_INIT = 1;
	public final static int ERROR_SEND = 2;
	public final static int ERROR_RECEIVE = 3;
	public final static int ERROR_CLOSING = 4;
	public final static int ERROR_AUTH = 5;

	private int errorCode = DEFAULT_ERROR;

	private static final long serialVersionUID = -1336555355746115416L;

	public SynchronizationException(String message) {
		super(message);
	}

	public SynchronizationException(String message, int code) {
		super(message);
		errorCode = code;
	}

	public SynchronizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SynchronizationException(String message, Throwable cause, int code) {
		super(message, cause);
		errorCode = code;
	}

	public int getCode() {
		return errorCode;
	}

	public void setCode(int code) {
		errorCode = code;
	}
}
