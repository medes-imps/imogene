package org.imogene.android.xml.converters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.imogene.android.domain.ImogBean;
import org.imogene.android.util.content.ContentUrisUtils;
import org.imogene.android.xml.mapper.Mapper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class CollectionConverter extends AbstractFieldConverter {

	private Context context;
	private Mapper mapper;

	public CollectionConverter(Context context, Mapper mapper) {
		this.context = context;
		this.mapper = mapper;
	}

	@Override
	public Object parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.nextTag() != XmlPullParser.START_TAG) {
			return null;
		}
		parser.require(XmlPullParser.START_TAG, null, "collection");

		ContentResolver res = context.getContentResolver();
		List<Uri> uris = null;

		while (parser.nextTag() == XmlPullParser.START_TAG) {
			Uri uri = mapper.realUri(parser.getName());
			if (uri != null) {
				String id = parser.getAttributeValue(null, "id");
				Cursor c = res.query(uri, new String[] { ImogBean.Columns._ID }, ImogBean.Columns._ID + "='" + id + "'", null, null);
				if (c.getCount() != 1) {
					c.close();
					ContentValues values = new ContentValues();
					values.put(ImogBean.Columns._ID, id);
					values.put(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
					if (uris == null) {
						uris = new ArrayList<Uri>();
					}
					uris.add(res.insert(uri, values));
				} else {
					c.moveToFirst();
					String sId = c.getString(0);
					c.close();
					if (uris == null) {
						uris = new ArrayList<Uri>();
					}
					uris.add(ContentUrisUtils.withAppendedId(uri, sId));
				}
			}
			parser.nextTag();
		}
		return uris;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag(null, "collection");
		List<Uri> uris = (List<Uri>) obj;
		for (Uri uri : uris) {
			String packageName = mapper.serializeUri(uri);
			serializer.startTag(null, packageName);
			serializer.attribute(null, "id", uri.getLastPathSegment());
			serializer.endTag(null, packageName);
		}
		serializer.endTag(null, "collection");
	}
}
