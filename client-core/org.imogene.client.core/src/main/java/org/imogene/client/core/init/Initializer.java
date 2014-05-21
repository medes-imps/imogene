package org.imogene.client.core.init;

import java.io.File;

import org.eclipse.core.runtime.Platform;

public class Initializer {

	public void init() {
		// Derby properties
		System.setProperty("derby.system.home", createPath("derby"));

		// Jetty properties
		System.setProperty("jetty.home", createPath("jetty"));

		// Imogene properties
		System.setProperty("imogene.home", createPath("imogene"));
	}

	private static String createPath(String folder) {
		File file = new File(Platform.getInstanceLocation().getURL().getPath(), folder);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file.getAbsolutePath();
	}

}
