package org.imogene.android.xml.converters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogHelper;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.net.Uri;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.converters.AbstractFieldConverter;
import fr.medes.android.xml.mapper.Mapper;

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

		List<ImogBean> beans = null;

		while (parser.nextTag() == XmlPullParser.START_TAG) {
			Class<?> clazz = mapper.realClass(parser.getName());
			if (clazz == null) {
				continue;
			}
			Uri uri = ImogHelper.getInstance().getUriFromClass(clazz.asSubclass(ImogBean.class));
			if (uri == null) {
				continue;
			}
			String id = parser.getAttributeValue(null, "id");

			ImogBean bean = ImogOpenHelper.fromUri(ContentUrisUtils.withAppendedId(uri, id));
			if (bean == null) {
				try {
					bean = (ImogBean) clazz.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (bean == null) {
					continue;
				}
				bean.reset();
				bean.setId(id);
				bean.setModifiedFrom(ImogBean.Columns.SYNC_SYSTEM);
				bean.saveOrUpdate(context);
			}
			if (beans == null) {
				beans = new ArrayList<ImogBean>();
			}
			beans.add(bean);
			parser.nextTag();
		}
		return beans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void serialize(XmlSerializer serializer, Object obj) throws IllegalArgumentException, IllegalStateException,
			IOException {
		serializer.startTag(null, "collection");
		List<ImogBean> beans = (List<ImogBean>) obj;
		for (ImogBean bean : beans) {
			String packageName = mapper.serializedClass(bean.getClass());
			serializer.startTag(null, packageName);
			serializer.attribute(null, "id", bean.getId());
			serializer.endTag(null, packageName);
		}
		serializer.endTag(null, "collection");
	}
}
