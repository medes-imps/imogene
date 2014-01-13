package org.imogene.android.xml.converters;

import java.io.IOException;

import org.imogene.android.util.FormatHelper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.location.Location;

public class GpsConverter extends AbstractFieldConverter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(Location.class);
	}

	@Override
	public Object parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		String tag = parser.getName();
		Double lat = null;
		Double lon = null;
		while (parser.nextTag() != XmlPullParser.END_TAG || !tag.equals(parser.getName())) {
			if (parser.getEventType() == XmlPullParser.START_TAG) {
				if ("latitude".equals(parser.getName())) {
					lat = FormatHelper.toDouble(parser.nextText());
				} else if ("longitude".equals(parser.getName())) {
					lon = FormatHelper.toDouble(parser.nextText());
				}
			}
		}
		if (lat != null && lon != null) {
			Location l = new Location("gps");
			l.setLatitude(lat.doubleValue());
			l.setLongitude(lon.doubleValue());
			return l;
		}
		return null;
	}

	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException,
			IllegalStateException, IOException {
		Location location = (Location) obj;
		serializer.startTag(null, "latitude");
		serializer.text(String.valueOf(location.getLatitude()));
		serializer.endTag(null, "latitude");
		serializer.startTag(null, "longitude");
		serializer.text(String.valueOf(location.getLongitude()));
		serializer.endTag(null, "longitude");
	}

}
