package org.imogene.android.xml.converters;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import org.imogene.android.common.entity.LocalizedText;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import fr.medes.android.xml.converters.AbstractFieldConverter;

public class LocalizedTextConverter extends AbstractFieldConverter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(LocalizedText.class);
	}

	@Override
	public Object parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		String tag = parser.getName();
		LocalizedText text = LocalizedText.newInstance();
		while (parser.nextTag() != XmlPullParser.END_TAG || !tag.equals(parser.getName())) {
			if (parser.getEventType() == XmlPullParser.START_TAG) {
				String language = parser.getName();
				String value = parser.nextText();
				try {
					Method m = text.getClass().getMethod(
							"set" + language.substring(0, 1).toUpperCase() + language.substring(1), String.class);
					m.invoke(text, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return text;
	}

	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException, IllegalStateException,
			IOException {
		LocalizedText text = (LocalizedText) obj;
		Map<String, String> values = text.getFieldAndValue();
		for (String language : values.keySet()) {
			String value = values.get(language);
			if (value != null) {
				serializer.startTag(null, language);
				serializer.text(value);
				serializer.endTag(null, language);
			}
		}
	}

}
