package org.imogene.android.xml.converters;

import org.imogene.android.domain.DynamicFieldTemplate;

public class DynamicFieldTypeConverter extends AbstractSingleValueConverter {
	
	@Override
	public Object fromString(String str) {
		return DynamicFieldTemplate.Type.parseType(str);
	}
	
	@Override
	public String toString(Object obj) {
		return ((DynamicFieldTemplate.Type) obj).getValue();
	}
	
	@Override
	public boolean canConvert(Class<?> clazz) {	
		return clazz.equals(DynamicFieldTemplate.Type.class);
	}

}
