package org.imogene.android.xml.converters;

import java.io.IOException;
import java.util.Date;

import org.imogene.android.util.FormatHelper;
import org.xmlpull.v1.XmlSerializer;

/**
 * Converts a java.util.Date to a String as a date format, retaining precision
 * down to milliseconds. The formatted string is by default in UTC.
 * 
 * @author Medes-IMPS
 * 
 */
public class DateConverter extends AbstractSingleValueConverter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(Date.class);
	}

	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException,
			IllegalStateException, IOException {
		serializer.attribute(null, "class", "sql-timestamp");
		super.serialize(serializer, obj);
	}

	@Override
	public Object fromString(String str) {
		return FormatHelper.toDate(str);
	}

	@Override
	public String toString(Object obj) {
		return String.valueOf(((Date) obj).getTime());
	}

}
