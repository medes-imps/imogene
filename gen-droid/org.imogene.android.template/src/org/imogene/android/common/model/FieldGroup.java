package org.imogene.android.common.model;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.FieldGroupCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.util.Tools;
import org.imogene.android.util.content.ContentUrisUtils;
import org.imogene.android.xml.annotation.XmlAlias;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

@XmlAlias("org.imogene.lib.common.model.FieldGroup")
public class FieldGroup extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final Drawable COLOR = Tools.getColorChip(Color.parseColor("black"));
		public static final String TABLE_NAME = "fieldgroup";
		public static final String BEAN_TYPE = "GRP";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(TABLE_NAME);

		public static final String NAME = "name";
		public static final String ENTITY = "entity";
	}

	public static FieldGroup fromUri(Uri uri) {
		if (uri == null) {
			return null;
		}
		FieldGroupCursor cursor = (FieldGroupCursor) ImogOpenHelper.getHelper().query(uri);
		if (cursor == null) {
			return null;
		}
		try {
			if (!cursor.moveToFirst()) {
				return null;
			}
			if (cursor.getCount() > 1) {
				throw new IllegalArgumentException("The uri do not represent a single entity: " + uri);
			}
			return new FieldGroup(cursor);
		} finally {
			cursor.close();
		}
	}

	@XmlAlias("name")
	private String name;
	@XmlAlias("entity")
	private Uri entity;

	public FieldGroup() {
	}

	public FieldGroup(FieldGroupCursor cursor) {
		init(cursor);
	}

	public FieldGroup(Bundle bundle) {
		if (bundle.containsKey(Columns.ENTITY)) {
			entity = bundle.getParcelable(Columns.ENTITY);
		}
	}

	private void init(FieldGroupCursor cursor) {
		super.init(cursor);
		name = cursor.getName();
		entity = cursor.getEntity();
	}

	public final String getName() {
		return name;
	}

	public final Uri getEntity() {
		return entity;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setEntity(Uri entity) {
		this.entity = entity;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		values.put(Columns.NAME, name);
		values.put(Columns.ENTITY, entity != null ? entity.getLastPathSegment() : null);

	}

	@Override
	public final void reset() {
		name = null;
		entity = null;
	}

	@Override
	public Uri saveOrUpdate(Context context) {
		return saveOrUpdate(context, Columns.CONTENT_URI);
	}

	@Override
	public void prepareForSave(Context context) {
		prepareForSave(context, Columns.BEAN_TYPE);
	}

}
