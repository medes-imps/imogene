package org.imogene.android.xml.converters;


/**
 * Base abstract implementation of Converter. Subclasses should implement
 * methods {@link Converter#canConvert(Class)} and
 * {@link Converter#serialize(org.xmlpull.v1.XmlSerializer, Object)} and
 * {@link Converter#parse(org.xmlpull.v1.XmlPullParser)} for the conversion.
 * 
 * @author Medes-IMPS
 * 
 */
public abstract class AbstractFieldConverter implements Converter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return false;
	}

	@Override
	public void setInteger(int ints) {
	}

	@Override
	public void setString(String string) {
	}

}
