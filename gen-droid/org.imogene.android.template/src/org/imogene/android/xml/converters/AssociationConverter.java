package org.imogene.android.xml.converters;

import java.io.IOException;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogHelper;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.converters.AbstractFieldConverter;
import fr.medes.android.xml.mapper.Mapper;

public class AssociationConverter extends AbstractFieldConverter {

	private Context context;
	private Mapper mapper;

	public AssociationConverter(Context context, Mapper mapper) {
		this.context = context;
		this.mapper = mapper;
	}

	@Override
	public boolean canConvert(Class<?> clazz) {
		return ImogBean.class.isAssignableFrom(clazz);
	}

	@Override
	public Object parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.nextTag() != XmlPullParser.START_TAG) {
			return null;
		}

		Class<?> clazz = mapper.realClass(parser.getName());
		if (clazz == null) {
			return null;
		}
		Uri uri = ImogHelper.getInstance().getUriFromClass(clazz.asSubclass(ImogBean.class));
		if (uri == null) {
			return null;
		}

		String id = parser.getAttributeValue(null, "id");

		ImogBean bean = ImogOpenHelper.fromUri(ContentUrisUtils.withAppendedId(uri, id));
		if (bean == null) {
			ContentValues values = new ContentValues();
			values.put(ImogBean.Columns._ID, id);
			values.put(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
			Uri insertUri = context.getContentResolver().insert(uri, values);
			bean = ImogOpenHelper.fromUri(insertUri);
		}
		return bean;
	}

	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException, IllegalStateException,
			IOException {
		ImogBean bean = (ImogBean) obj;
		String packageName = mapper.serializedClass(bean.getClass());
		serializer.startTag(null, packageName);
		serializer.attribute(null, "id", bean.getId());
		serializer.endTag(null, packageName);
	}

}
