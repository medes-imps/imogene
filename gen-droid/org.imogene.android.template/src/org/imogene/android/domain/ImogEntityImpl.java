package org.imogene.android.domain;

import java.util.List;

import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.ImogEntityCursor;
import org.imogene.android.xml.annotation.XmlAlias;
import org.imogene.android.xml.annotation.XmlConverter;
import org.imogene.android.xml.converters.CollectionConverter;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public abstract class ImogEntityImpl extends ImogBeanImpl implements ImogEntity {

	@Override
	protected void init(ImogBeanCursor cursor) {
		super.init(cursor);
		dynamicFieldValues = ((ImogEntityCursor) cursor).getDynamicFieldValues();
	}

	@XmlAlias("dynamicFieldValues")
	@XmlConverter(CollectionConverter.class)
	private List<Uri> dynamicFieldValues;

	@Override
	public void setDynamicFieldValues(List<Uri> dynamicFieldValues) {
		this.dynamicFieldValues = dynamicFieldValues;
	}

	@Override
	public List<Uri> getDynamicFieldValues() {
		return dynamicFieldValues;
	}

	@Override
	protected void postCommit(Context context) {
		super.postCommit(context);
		ContentValues v = new ContentValues();
		v.putNull(DynamicFieldInstance.Columns.FORMINSTANCE);
		context.getContentResolver().update(DynamicFieldInstance.Columns.CONTENT_URI, v,
				DynamicFieldInstance.Columns.FORMINSTANCE + "= ?", new String[] { getId() });
		if (dynamicFieldValues != null) {
			v.put(DynamicFieldInstance.Columns.FORMINSTANCE, getId());
			for (Uri uri : dynamicFieldValues) {
				context.getContentResolver().update(uri, v, null, null);
			}
		}
	}

}
