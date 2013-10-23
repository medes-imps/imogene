package org.imogene.android.xml.converters;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/**
 * Base abstract implementation of SingleValueConverter. Subclasses should
 * implement methods {@link Converter#canConvert(Class)} and
 * {@link SingleValueConverter#fromString(String)} for the conversion.
 * 
 * @author Medes-IMPS
 * 
 */
public abstract class AbstractSingleValueConverter extends AbstractFieldConverter implements
		SingleValueConverter {

	@Override
	public Object parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		return fromString(parser.nextText());
	}

	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException,
			IllegalStateException, IOException {
		serializer.text(toString(obj));
	}

	@Override
	public String toString(Object obj) {
		return obj.toString();
	}

}
