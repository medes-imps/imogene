package org.imogene.android.common.profile;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.common.model.FieldGroup;
import org.imogene.android.database.sqlite.FieldGroupProfileCursor;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;

@XmlAlias("org.imogene.lib.common.profile.FieldGroupProfile")
public class FieldGroupProfile extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "fieldgroupprofile";
		public static final String BEAN_TYPE = "PRO_GRP";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String PROFILE = "profile";
		public static final String FIELDGROUP = "fieldGroup";
		public static final String READ = "canRead";
		public static final String WRITE = "canWrite";
		public static final String EXPORT = "canExport";
	}

	@XmlAlias("profile")
	private Profile profile;
	@XmlAlias("fieldGroup")
	private FieldGroup fieldGroup;
	@XmlAlias("read")
	private Boolean read;
	@XmlAlias("write")
	private Boolean write;
	@XmlAlias("export")
	private Boolean export;

	public FieldGroupProfile() {
	}

	public FieldGroupProfile(Bundle bundle) {
		super(bundle);
		if (bundle.containsKey(Columns.PROFILE)) {
			profile = bundle.getParcelable(Columns.PROFILE);
		}
		if (bundle.containsKey(Columns.FIELDGROUP)) {
			fieldGroup = bundle.getParcelable(Columns.FIELDGROUP);
		}
	}

	public FieldGroupProfile(FieldGroupProfileCursor cursor) {
		super(cursor);
		profile = cursor.getProfile();
		fieldGroup = cursor.getFieldGroup();
		read = cursor.getRead();
		write = cursor.getWrite();
		export = cursor.getExport();
	}

	public final Profile getProfile() {
		return profile;
	}

	public final FieldGroup getFieldGroup() {
		return fieldGroup;
	}

	public final Boolean getRead() {
		return read;
	}

	public final Boolean getWrite() {
		return write;
	}

	public final Boolean getExport() {
		return export;
	}

	public final void setProfile(Profile profile) {
		this.profile = profile;
	}

	public final void setFieldGroup(FieldGroup fieldGroup) {
		this.fieldGroup = fieldGroup;
	}

	public final void setRead(Boolean read) {
		this.read = read;
	}

	public final void setWrite(Boolean write) {
		this.write = write;
	}

	public final void setExport(Boolean export) {
		this.export = export;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		values.put(Columns.PROFILE, profile != null ? profile.getId() : null);

		values.put(Columns.FIELDGROUP, fieldGroup != null ? fieldGroup.getId() : null);

		values.put(Columns.READ, read != null ? read.toString() : null);
		values.put(Columns.WRITE, write != null ? write.toString() : null);
		values.put(Columns.EXPORT, export != null ? export.toString() : null);
	}

	@Override
	public final void reset() {
		profile = null;
		fieldGroup = null;
		read = null;
		write = null;
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
