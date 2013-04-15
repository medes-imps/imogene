package org.imogene.sync.client;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class MyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private static MyPropertyPlaceholderConfigurer instance = null;
	
	public static MyPropertyPlaceholderConfigurer getInstance() {
		if (instance == null) {
			instance = new MyPropertyPlaceholderConfigurer();
		}
		return instance;
	}
	
}
