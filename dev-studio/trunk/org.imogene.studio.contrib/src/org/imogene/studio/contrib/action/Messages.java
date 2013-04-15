package org.imogene.studio.contrib.action;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.imogene.studio.contrib.action.messages"; //$NON-NLS-1$
	public static String GenerateJob_1;
	public static String GenerateJob_2;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
