package org.imogene.android.common.profile;

import java.util.ArrayList;
import java.util.List;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.ProfileCursor;
import org.imogene.android.util.Tools;
import org.imogene.android.util.content.ContentUrisUtils;
import org.imogene.android.xml.annotation.XmlAlias;
import org.imogene.android.xml.annotation.XmlConverter;
import org.imogene.android.xml.converters.CollectionConverter;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

@XmlAlias("org.imogene.lib.common.profile.Profile")
public class Profile extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final Drawable COLOR = Tools.getColorChip(Color.parseColor("black"));
		public static final String TABLE_NAME = "profile";
		public static final String BEAN_TYPE = "PRO";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(TABLE_NAME);

		public static final String NAME = "name";
		public static final String ENTITYPROFILES = "entityProfiles";
		public static final String FIELDGROUPPROFILES = "fieldGroupProfiles";
	}

	public static Profile fromUri(Uri uri) {
		if (uri == null) {
			return null;
		}
		ProfileCursor cursor = (ProfileCursor) ImogOpenHelper.getHelper().query(uri);
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
			return new Profile(cursor);
		} finally {
			cursor.close();
		}
	}

	public static List<Profile> fromUris(List<Uri> uris) {
		if (uris == null) {
			return null;
		}
		ArrayList<Profile> result = new ArrayList<Profile>();
		for (Uri uri : uris) {
			Profile profile = Profile.fromUri(uri);
			if (profile != null) {
				result.add(profile);
			}
		}
		return result;
	}

	@XmlAlias("name")
	private String name;
	@XmlAlias("entityProfiles")
	@XmlConverter(CollectionConverter.class)
	private List<Uri> entityProfiles;
	@XmlAlias("fieldGroupProfiles")
	@XmlConverter(CollectionConverter.class)
	private List<Uri> fieldGroupProfiles;

	public Profile() {
	}

	public Profile(ProfileCursor cursor) {
		init(cursor);
	}

	public Profile(Bundle bundle) {
		if (bundle.containsKey(Columns.ENTITYPROFILES)) {
			entityProfiles = bundle.getParcelableArrayList(Columns.ENTITYPROFILES);
		}
		if (bundle.containsKey(Columns.FIELDGROUPPROFILES)) {
			fieldGroupProfiles = bundle.getParcelableArrayList(Columns.FIELDGROUPPROFILES);
		}
	}

	private void init(ProfileCursor cursor) {
		super.init(cursor);
		name = cursor.getName();
		entityProfiles = cursor.getEntityProfiles();
		fieldGroupProfiles = cursor.getFieldGroupProfiles();
	}

	public final String getName() {
		return name;
	}

	public final List<Uri> getEntityProfiles() {
		return entityProfiles;
	}

	public final List<Uri> getFieldGroupProfiles() {
		return fieldGroupProfiles;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setEntityProfiles(List<Uri> entityProfiles) {
		this.entityProfiles = entityProfiles;
	}

	public final void setFieldGroupProfiles(List<Uri> fieldGroupProfiles) {
		this.fieldGroupProfiles = fieldGroupProfiles;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		values.put(Columns.NAME, name);
	}

	@Override
	protected final void postCommit(Context context) {
		super.postCommit(context);

		{
			ContentValues v = new ContentValues();
			v.putNull(EntityProfile.Columns.PROFILE);
			context.getContentResolver().update(EntityProfile.Columns.CONTENT_URI, v,
					EntityProfile.Columns.PROFILE + "='" + getId() + "'", null);
			if (entityProfiles != null) {
				v.put(EntityProfile.Columns.PROFILE, getId());
				for (Uri uri : entityProfiles) {
					context.getContentResolver().update(uri, v, null, null);
				}
			}
		}

		{
			ContentValues v = new ContentValues();
			v.putNull(FieldGroupProfile.Columns.PROFILE);
			context.getContentResolver().update(FieldGroupProfile.Columns.CONTENT_URI, v,
					FieldGroupProfile.Columns.PROFILE + "='" + getId() + "'", null);
			if (fieldGroupProfiles != null) {
				v.put(FieldGroupProfile.Columns.PROFILE, getId());
				for (Uri uri : fieldGroupProfiles) {
					context.getContentResolver().update(uri, v, null, null);
				}
			}
		}

	}

	@Override
	public final void reset() {
		name = null;
		entityProfiles = null;
		fieldGroupProfiles = null;
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
