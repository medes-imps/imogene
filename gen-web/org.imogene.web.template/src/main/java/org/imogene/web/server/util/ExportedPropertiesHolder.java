package org.imogene.web.server.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Holder of the exported spring properties
 * @author Medes-IMPS
 *
 */
public class ExportedPropertiesHolder {

	private Map<String, String> properties = new HashMap<String, String>();

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}		
}
