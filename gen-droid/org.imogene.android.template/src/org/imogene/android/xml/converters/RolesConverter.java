package org.imogene.android.xml.converters;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class RolesConverter extends AbstractFieldConverter {

	@Override
	public Object parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		while (parser.nextTag() == XmlPullParser.START_TAG) {
			if (first) {
				first = false;
			} else {
				builder.append(';');
			}
			builder.append(parser.nextText());
		}
		return builder.toString();
	}

	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException, IllegalStateException, IOException {

	}

}
