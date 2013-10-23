package org.imogene.lib.sync.serializer.xml;

import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UTCDateConverter implements Converter {

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (value != null) {
			writer.setValue(String.valueOf(((Date) value).getTime()));
		} else {
			writer.setValue("");
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String value = reader.getValue();
		if (!value.equals("")) {
			return new Date(Long.parseLong(reader.getValue()));
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class arg0) {
		if (Date.class.isAssignableFrom(arg0)) {
			return true;
		}
		return false;
	}

}
