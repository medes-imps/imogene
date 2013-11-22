package org.imogene.android.sync;

public class SynchronizationException extends Exception {

	private static final long serialVersionUID = -1336555355746115416L;

	public static final int DEFAULT_ERROR = 0;
	public static final int ERROR_INIT = 1;
	public static final int ERROR_SEND = 2;
	public static final int ERROR_RECEIVE = 3;
	public static final int ERROR_CLOSING = 4;
	public static final int ERROR_AUTH = 5;
	public static final int ERROR_SEARCH = 6;

	private int errorCode = DEFAULT_ERROR;

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
