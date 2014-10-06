package org.imogene.android.common.model;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.FieldGroupCursor;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;

@XmlAlias("org.imogene.lib.common.model.FieldGroup")
public class FieldGroup extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "fieldgroup";
		public static final String BEAN_TYPE = "GRP";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String NAME = "name";
		public static final String ENTITY = "entity";
	}

	@XmlAlias("name")
	private String name;
	@XmlAlias("entity")
	private CardEntity entity;

	public FieldGroup() {
	}

	public FieldGroup(Bundle bundle) {
		super(bundle);
		if (bundle.containsKey(Columns.ENTITY)) {
			entity = bundle.getParcelable(Columns.ENTITY);
		}
	}

	public FieldGroup(FieldGroupCursor cursor) {
		super(cursor);
		name = cursor.getName();
		entity = cursor.getEntity();
	}

	public final String getName() {
		return name;
	}

	public final CardEntity getEntity() {
		return entity;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setEntity(CardEntity entity) {
		this.entity = entity;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		values.put(Columns.NAME, name);
		values.put(Columns.ENTITY, entity != null ? entity.getId() : null);

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
