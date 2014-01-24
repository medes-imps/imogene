package org.imogene.android.common.profile;

import java.util.ArrayList;
import java.util.List;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.EntityProfileCursor;
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

@XmlAlias("org.imogene.lib.common.profile.EntityProfile")
public class EntityProfile extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final Drawable COLOR = Tools.getColorChip(Color.parseColor("black"));
		public static final String TABLE_NAME = "entityprofile";
		public static final String BEAN_TYPE = "PRO_ENT";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(TABLE_NAME);

		public static final String PROFILE = "profile";
		public static final String ENTITY = "entity";
		public static final String CREATE = "canCreate";
		public static final String DIRECTACCESS = "canDirectAccess";
		public static final String DELETE = "canDelete";
		public static final String EXPORT = "canExport";
	}

	public static EntityProfile fromUri(Uri uri) {
		if (uri == null) {
			return null;
		}
		EntityProfileCursor cursor = (EntityProfileCursor) ImogOpenHelper.getHelper().query(uri);
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
			return new EntityProfile(cursor);
		} finally {
			cursor.close();
		}
	}

	public static List<EntityProfile> fromUris(List<Uri> uris) {
		if (uris == null) {
			return null;
		}
		ArrayList<EntityProfile> result = new ArrayList<EntityProfile>();
		for (Uri uri : uris) {
			EntityProfile profile = EntityProfile.fromUri(uri);
			if (profile != null) {
				result.add(profile);
			}
		}
		return result;
	}

	@XmlAlias("profile")
	private Uri profile;
	@XmlAlias("entity")
	private Uri entity;
	@XmlAlias("create")
	private Boolean create;
	@XmlAlias("directAccess")
	private Boolean directAccess;
	@XmlAlias("delete")
	private Boolean delete;
	@XmlAlias("export")
	private Boolean export;

	public EntityProfile() {
	}

	public EntityProfile(EntityProfileCursor cursor) {
		init(cursor);
	}

	public EntityProfile(Bundle bundle) {
		if (bundle.containsKey(Columns.PROFILE)) {
			profile = bundle.getParcelable(Columns.PROFILE);
		}
		if (bundle.containsKey(Columns.ENTITY)) {
			entity = bundle.getParcelable(Columns.ENTITY);
		}
	}

	private void init(EntityProfileCursor cursor) {
		super.init(cursor);
		profile = cursor.getProfile();
		entity = cursor.getEntity();
		create = cursor.getCreate();
		directAccess = cursor.getDirectAccess();
		delete = cursor.getDelete();
		export = cursor.getExport();
	}

	public final Uri getProfile() {
		return profile;
	}

	public final Uri getEntity() {
		return entity;
	}

	public final Boolean getCreate() {
		return create;
	}

	public final Boolean getDirectAccess() {
		return directAccess;
	}

	public final Boolean getDelete() {
		return delete;
	}

	public final Boolean getExport() {
		return export;
	}

	public final void setProfile(Uri profile) {
		this.profile = profile;
	}

	public final void setEntity(Uri entity) {
		this.entity = entity;
	}

	public final void setCreate(Boolean create) {
		this.create = create;
	}

	public final void setDirectAccess(Boolean directAccess) {
		this.directAccess = directAccess;
	}

	public final void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public final void setExport(Boolean export) {
		this.export = export;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		values.put(Columns.PROFILE, profile != null ? profile.getLastPathSegment() : null);

		values.put(Columns.ENTITY, entity != null ? entity.getLastPathSegment() : null);

		values.put(Columns.CREATE, create != null ? create.toString() : null);
		values.put(Columns.DIRECTACCESS, directAccess != null ? directAccess.toString() : null);
		values.put(Columns.DELETE, delete != null ? delete.toString() : null);
		values.put(Columns.EXPORT, export != null ? export.toString() : null);
	}

	@Override
	protected final void postCommit(Context context) {
		super.postCommit(context);
	}

	@Override
	public final void reset() {
		profile = null;
		entity = null;
		create = null;
		directAccess = null;
		delete = null;
		export = null;
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
