package org.imogene.android.common.entity;

import org.imogene.android.Constants;
import org.imogene.android.database.sqlite.DefaultUserCursor;

import android.content.Context;
import android.net.Uri;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;

@XmlAlias("org.imogene.lib.common.user.DefaultUser")
public class DefaultUser extends ImogActorImpl {

	public static interface Columns extends ImogActor.Columns {
		public static final String TABLE_NAME = "defaultuser";
		public static final String BEAN_TYPE = "DEFAULT";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);
	}

	public DefaultUser(DefaultUserCursor cursor) {
		super(cursor);
	}

	public DefaultUser() {
	}

	@Override
	public void prepareForSave(Context context) {
		prepareForSave(context, Columns.BEAN_TYPE);
	}

	@Override
	public Uri saveOrUpdate(Context context) {
		return saveOrUpdate(context, Columns.CONTENT_URI);
	}

}
