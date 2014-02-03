package org.imogene.android.common.profile;

import java.util.ArrayList;
import java.util.List;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.FieldGroupProfileCursor;
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

@XmlAlias("org.imogene.lib.common.profile.FieldGroupProfile")
public class FieldGroupProfile extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final Drawable COLOR = Tools.getColorChip(Color.parseColor("black"));
		public static final String TABLE_NAME = "fieldgroupprofile";
		public static final String BEAN_TYPE = "PRO_GRP";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(TABLE_NAME);

		public static final String PROFILE = "profile";
		public static final String FIELDGROUP = "fieldGroup";
		public static final String READ = "canRead";
		public static final String WRITE = "canWrite";
		public static final String EXPORT = "canExport";
	}

	public static FieldGroupProfile fromUri(Uri uri) {
		if (uri == null) {
			return null;
		}
		FieldGroupProfileCursor cursor = (FieldGroupProfileCursor) ImogOpenHelper.getHelper().query(uri);
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
			return new FieldGroupProfile(cursor);
		} finally {
			cursor.close();
		}
	}

	public static List<FieldGroupProfile> fromUris(List<Uri> uris) {
		if (uris == null) {
			return null;
		}
		ArrayList<FieldGroupProfile> result = new ArrayList<FieldGroupProfile>();
		for (Uri uri : uris) {
			FieldGroupProfile profile = FieldGroupProfile.fromUri(uri);
			if (profile != null) {
				result.add(profile);
			}
		}
		return result;
	}

	@XmlAlias("profile")
	private Uri profile;
	@XmlAlias("fieldGroup")
	private Uri fieldGroup;
	@XmlAlias("read")
	private Boolean read;
	@XmlAlias("write")
	private Boolean write;
	@XmlAlias("export")
	private Boolean export;

	public FieldGroupProfile() {
	}

	public FieldGroupProfile(FieldGroupProfileCursor cursor) {
		init(cursor);
	}

	public FieldGroupProfile(Bundle bundle) {
		if (bundle.containsKey(Columns.PROFILE)) {
			profile = bundle.getParcelable(Columns.PROFILE);
		}
		if (bundle.containsKey(Columns.FIELDGROUP)) {
			fieldGroup = bundle.getParcelable(Columns.FIELDGROUP);
		}
	}

	private void init(FieldGroupProfileCursor cursor) {
		super.init(cursor);
		profile = cursor.getProfile();
		fieldGroup = cursor.getFieldGroup();
		read = cursor.getRead();
		write = cursor.getWrite();
		export = cursor.getExport();
	}

	public final Uri getProfile() {
		return profile;
	}

	public final Uri getFieldGroup() {
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

	public final void setProfile(Uri profile) {
		this.profile = profile;
	}

	public final void setFieldGroup(Uri fieldGroup) {
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
		values.put(Columns.PROFILE, profile != null ? profile.getLastPathSegment() : null);

		values.put(Columns.FIELDGROUP, fieldGroup != null ? fieldGroup.getLastPathSegment() : null);

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
