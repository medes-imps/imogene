package org.imogene.android.common.profile;

import java.util.List;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.ProfileCursor;
import org.imogene.android.xml.converters.CollectionConverter;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;
import fr.medes.android.xml.annotation.XmlConverter;

@XmlAlias("org.imogene.lib.common.profile.Profile")
public class Profile extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "profile";
		public static final String BEAN_TYPE = "PRO";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String NAME = "name";
		public static final String ENTITYPROFILES = "entityProfiles";
		public static final String FIELDGROUPPROFILES = "fieldGroupProfiles";
	}

	@XmlAlias("name")
	private String name;
	@XmlAlias("entityProfiles")
	@XmlConverter(CollectionConverter.class)
	private List<EntityProfile> entityProfiles;
	@XmlAlias("fieldGroupProfiles")
	@XmlConverter(CollectionConverter.class)
	private List<FieldGroupProfile> fieldGroupProfiles;

	public Profile() {
	}

	public Profile(Bundle bundle) {
		super(bundle);
		if (bundle.containsKey(Columns.ENTITYPROFILES)) {
			entityProfiles = bundle.getParcelableArrayList(Columns.ENTITYPROFILES);
		}
		if (bundle.containsKey(Columns.FIELDGROUPPROFILES)) {
			fieldGroupProfiles = bundle.getParcelableArrayList(Columns.FIELDGROUPPROFILES);
		}
	}

	public Profile(ProfileCursor cursor) {
		super(cursor);
		name = cursor.getName();
		entityProfiles = cursor.getEntityProfiles();
		fieldGroupProfiles = cursor.getFieldGroupProfiles();
	}

	public final String getName() {
		return name;
	}

	public final List<EntityProfile> getEntityProfiles() {
		return entityProfiles;
	}

	public final List<FieldGroupProfile> getFieldGroupProfiles() {
		return fieldGroupProfiles;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final void setEntityProfiles(List<EntityProfile> entityProfiles) {
		this.entityProfiles = entityProfiles;
	}

	public final void setFieldGroupProfiles(List<FieldGroupProfile> fieldGroupProfiles) {
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
				for (EntityProfile entityProfile : entityProfiles) {
					entityProfile.setProfile(this);
					entityProfile.saveOrUpdate(context);
				}
			}
		}

		{
			ContentValues v = new ContentValues();
			v.putNull(FieldGroupProfile.Columns.PROFILE);
			context.getContentResolver().update(FieldGroupProfile.Columns.CONTENT_URI, v,
					FieldGroupProfile.Columns.PROFILE + "='" + getId() + "'", null);
			if (fieldGroupProfiles != null) {
				for (FieldGroupProfile fieldGroupProfile : fieldGroupProfiles) {
					fieldGroupProfile.setProfile(this);
					fieldGroupProfile.saveOrUpdate(context);
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
