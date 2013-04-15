package org.imogene.android.util.ntp;

public class SntpException extends Exception {

	private static final String MSG = "Couldn't get time from web";
	/**
	 * 
	 */
	private static final long serialVersionUID = 7540017650356084808L;
	
	public SntpException() {
		super(MSG);
	}

}
