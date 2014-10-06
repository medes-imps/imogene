package org.imogene.android.common.profile;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.common.model.CardEntity;
import org.imogene.android.database.sqlite.EntityProfileCursor;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;

@XmlAlias("org.imogene.lib.common.profile.EntityProfile")
public class EntityProfile extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "entityprofile";
		public static final String BEAN_TYPE = "PRO_ENT";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String PROFILE = "profile";
		public static final String ENTITY = "entity";
		public static final String CREATE = "canCreate";
		public static final String DIRECTACCESS = "canDirectAccess";
		public static final String DELETE = "canDelete";
		public static final String EXPORT = "canExport";
	}

	@XmlAlias("profile")
	private Profile profile;
	@XmlAlias("entity")
	private CardEntity entity;
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

	public EntityProfile(Bundle bundle) {
		super(bundle);
		if (bundle.containsKey(Columns.PROFILE)) {
			profile = bundle.getParcelable(Columns.PROFILE);
		}
		if (bundle.containsKey(Columns.ENTITY)) {
			entity = bundle.getParcelable(Columns.ENTITY);
		}
	}

	public EntityProfile(EntityProfileCursor cursor) {
		super(cursor);
		profile = cursor.getProfile();
		entity = cursor.getEntity();
		create = cursor.getCreate();
		directAccess = cursor.getDirectAccess();
		delete = cursor.getDelete();
		export = cursor.getExport();
	}

	public final Profile getProfile() {
		return profile;
	}

	public final CardEntity getEntity() {
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

	public final void setProfile(Profile profile) {
		this.profile = profile;
	}

	public final void setEntity(CardEntity entity) {
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
		values.put(Columns.PROFILE, profile != null ? profile.getId() : null);

		values.put(Columns.ENTITY, entity != null ? entity.getId() : null);

		values.put(Columns.CREATE, create != null ? create.toString() : null);
		values.put(Columns.DIRECTACCESS, directAccess != null ? directAccess.toString() : null);
		values.put(Columns.DELETE, delete != null ? delete.toString() : null);
		values.put(Columns.EXPORT, export != null ? export.toString() : null);
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
