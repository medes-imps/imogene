package org.imogene.android.xml.converters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.imogene.android.Constants.Paths;
import org.imogene.android.common.binary.Binary;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.net.Uri;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.converters.AbstractFieldConverter;

public class ContentConverter extends AbstractFieldConverter {

	private Context context;
	private String id;

	@Override
	public void setString(String string) {
		id = string;
	}

	public ContentConverter(Context context) {
		this.context = context;
	}

	@Override
	public Object parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		Paths.PATH_TEMPORARY.mkdirs();

		File file = File.createTempFile("tmp", ".bin", Paths.PATH_TEMPORARY);
		FileOutputStream fos = new FileOutputStream(file);

		while (parser.nextTag() == XmlPullParser.START_TAG) {
			String value = parser.nextText();
			fos.write(Base64.decodeBase64(value.getBytes()));
		}

		fos.flush();
		fos.close();

		return Uri.fromFile(file);
	}

	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException, IllegalStateException,
			IOException {
		Uri uri = ContentUrisUtils.withAppendedId(Binary.Columns.CONTENT_URI, id);
		InputStream is = context.getContentResolver().openInputStream(uri);

		byte[] bytes = new byte[1024];
		while (is.read(bytes) != -1) {
			serializer.startTag(null, "data");
			serializer.text(new String(Base64.encodeBase64(bytes)));
			serializer.endTag(null, "data");
		}
	}
}
