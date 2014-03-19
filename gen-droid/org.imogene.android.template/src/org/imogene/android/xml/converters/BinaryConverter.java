package org.imogene.android.xml.converters;

import java.io.IOException;
import java.util.Date;

import org.imogene.android.common.binary.Binary;
import org.imogene.android.common.binary.BinaryFile;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import fr.medes.android.xml.converters.Converter;
import fr.medes.android.xml.converters.ConverterLookup;

public class BinaryConverter implements Converter {

	private ConverterLookup lookup;

	public BinaryConverter(ConverterLookup lookup) {
		this.lookup = lookup;
	}

	@Override
	public boolean canConvert(Class<?> clazz) {
		if (clazz.equals(BinaryFile.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		return null;
	}

	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException, IllegalStateException,
			IOException {
		Binary binary = (Binary) obj;

		// id
		serializer.startTag(null, "id");
		serializer.text(binary.getId());
		serializer.endTag(null, "id");

		// modified
		serializer.startTag(null, "modified");
		if (binary.getModified() != null) {
			Converter converter = lookup.lookupConverterForType(Date.class);
			converter.serialize(serializer, binary.getModified());
		}
		serializer.endTag(null, "modified");

		// uploadDate
		serializer.startTag(null, "uploadDate");
		if (binary.getUploadDate() != null) {
			Converter converter = lookup.lookupConverterForType(Date.class);
			converter.serialize(serializer, binary.getUploadDate());
		}
		serializer.endTag(null, "uploadDate");

		// modifiedBy
		serializer.startTag(null, "modifiedBy");
		if (binary.getModifiedBy() != null) {
			serializer.text(binary.getModifiedBy());
		}
		serializer.endTag(null, "modifiedBy");

		// modifiedFrom
		serializer.startTag(null, "modifiedFrom");
		if (binary.getModifiedFrom() != null)
			serializer.text(binary.getModifiedFrom());
		serializer.endTag(null, "modifiedFrom");

		// created
		serializer.startTag(null, "created");
		if (binary.getCreated() != null) {
			Converter converter = lookup.lookupConverterForType(Date.class);
			converter.serialize(serializer, binary.getCreated());
		}
		serializer.endTag(null, "created");

		// createdBy
		serializer.startTag(null, "createdBy");
		serializer.text(binary.getCreatedBy());
		serializer.endTag(null, "createdBy");

		// fileName
		serializer.startTag(null, "fileName");
		if (binary.getFileName() != null) {
			serializer.text(binary.getFileName());
		}
		serializer.endTag(null, "fileName");

		// contentType
		serializer.startTag(null, "contentType");
		if (binary.getContentType() != null) {
			serializer.text(binary.getContentType());
		}
		serializer.endTag(null, "contentType");

		// length
		serializer.startTag(null, "length");
		if (binary.getLength() > 0) {
			serializer.text(String.valueOf(binary.getLength()));
		}
		serializer.endTag(null, "length");

		// content
		serializer.startTag(null, "content");
		Converter fc = lookup.lookupConverterOfType(ContentConverter.class);
		fc.setString(binary.getId());
		fc.serialize(serializer, obj);
		serializer.endTag(null, "content");
	}

	@Override
	public void setInteger(int integer) {
	}

	@Override
	public void setString(String string) {
	}

}
