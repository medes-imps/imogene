package org.imogene.android.xml.converters;

import java.io.IOException;

import org.imogene.android.util.base64.Base64;
import org.xmlpull.v1.XmlSerializer;

public class ByteArrayConverter extends AbstractSingleValueConverter {
	
	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(byte[].class);
	}
	
	@Override
	public Object fromString(String str) {
		return Base64.decodeBase64(str.getBytes());
	}
	
	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException, IllegalStateException, IOException {
		// Don't serialize
	}

}
