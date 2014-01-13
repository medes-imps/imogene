package org.imogene.android.domain;

import org.imogene.android.database.sqlite.DefaultUserCursor;
import org.imogene.android.util.content.ContentUrisUtils;
import org.imogene.android.xml.annotation.XmlAlias;

import android.content.Context;
import android.net.Uri;

@XmlAlias("org.imogene.lib.common.user.DefaultUser")
public class DefaultUser extends ImogActorImpl {
	
	public static interface Columns extends ImogActor.Columns {
		public static final String TABLE_NAME = "defaultuser";
		public static final String BEAN_TYPE = "DEFAULT";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(TABLE_NAME);
	}
	
	public DefaultUser() {
	}

	public DefaultUser(DefaultUserCursor defaultUserCursor) {
		init(defaultUserCursor);
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
