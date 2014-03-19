package org.imogene.android.common.model;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.CardEntityCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import fr.medes.android.util.Tools;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;

@XmlAlias("org.imogene.lib.common.model.CardEntity")
public class CardEntity extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final Drawable COLOR = Tools.getColorChip(Color.parseColor("black"));
		public static final String TABLE_NAME = "cardentity";
		public static final String BEAN_TYPE = "SYNC_ENT";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String NAME = "name";
	}

	public static CardEntity fromUri(Uri uri) {
		if (uri == null) {
			return null;
		}
		CardEntityCursor cursor = (CardEntityCursor) ImogOpenHelper.getHelper().query(uri);
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
			return new CardEntity(cursor);
		} finally {
			cursor.close();
		}
	}

	@XmlAlias("name")
	private String name;

	public CardEntity() {
	}

	public CardEntity(CardEntityCursor cursor) {
		init(cursor);
	}

	public CardEntity(Bundle bundle) {
	}

	private void init(CardEntityCursor cursor) {
		super.init(cursor);
		name = cursor.getName();
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		values.put(Columns.NAME, name);
	}

	@Override
	public final void reset() {
		name = null;
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
