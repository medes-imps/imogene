package org.imogene.lib.sync.serializer.xml;

import com.thoughtworks.xstream.converters.Converter;

public class PropertyConverter {

	private String className;
	private String propertyName;
	private Converter converter;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Converter getConverter() {
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

}
