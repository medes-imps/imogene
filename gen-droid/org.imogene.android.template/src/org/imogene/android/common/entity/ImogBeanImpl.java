package org.imogene.android.common.entity;

import java.util.Date;

import org.imogene.android.database.DatabaseCache;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.Preferences;
import org.imogene.android.util.BeanKeyGenerator;
import org.imogene.android.util.NTPClock;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;
import fr.medes.android.xml.annotation.XmlOmitField;

public abstract class ImogBeanImpl implements ImogBean {

	@XmlAlias("id")
	private String id;

	@XmlAlias("modified")
	private Date modified;

	@XmlAlias("modifiedBy")
	private String modifiedBy;

	@XmlAlias("modifiedFrom")
	private String modifiedFrom;

	@XmlAlias("uploadDate")
	private Date uploadDate;

	@XmlAlias("created")
	private Date created;

	@XmlAlias("createdBy")
	private String createdBy;

	@XmlAlias("deleted")
	private Date deleted;

	@XmlOmitField
	private boolean flagRead;

	@XmlOmitField
	private boolean flagSynchronized;

	public ImogBeanImpl(ImogBeanCursor<? extends ImogBean> cursor) {
		id = cursor.getId();
		modified = cursor.getModified();
		modifiedBy = cursor.getModifiedBy();
		modifiedFrom = cursor.getModifiedFrom();
		uploadDate = cursor.getUploadDate();
		created = cursor.getCreated();
		createdBy = cursor.getCreatedBy();
		deleted = cursor.getDeleted();
		flagRead = cursor.getFlagRead();
		flagSynchronized = cursor.getFlagSynchronized();
		DatabaseCache.getInstance().put(this);
	}

	public ImogBeanImpl(Bundle bundle) {
	}

	public ImogBeanImpl(Parcel in) {
		id = in.readString();
		Long modifiedValue = (Long) in.readValue(null);
		modified = modifiedValue != null ? new Date(modifiedValue) : null;
		modifiedBy = in.readString();
		modifiedFrom = in.readString();
		Long uploadDateValue = (Long) in.readValue(null);
		uploadDate = uploadDateValue != null ? new Date(uploadDateValue) : null;
		Long createdValue = (Long) in.readValue(null);
		created = createdValue != null ? new Date(createdValue) : null;
		createdBy = in.readString();
		Long deletedValue = (Long) in.readValue(null);
		deleted = deletedValue != null ? new Date(deletedValue) : null;
		flagRead = in.readInt() == 1;
		flagSynchronized = in.readInt() == 1;
	}

	public ImogBeanImpl() {
	}

	@Override
	public final String getId() {
		return id;
	}

	@Override
	public final void setId(String id) {
		this.id = id;
	}

	@Override
	public final Date getModified() {
		return modified;
	}

	@Override
	public final void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public final String getModifiedBy() {
		return modifiedBy;
	}

	@Override
	public final void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public final String getModifiedFrom() {
		return modifiedFrom;
	}

	@Override
	public final void setModifiedFrom(String modifiedFrom) {
		this.modifiedFrom = modifiedFrom;
	}

	@Override
	public final Date getUploadDate() {
		return uploadDate;
	}

	@Override
	public final void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public final Date getCreated() {
		return created;
	}

	@Override
	public final void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public final String getCreatedBy() {
		return createdBy;
	}

	@Override
	public final void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public final Date getDeleted() {
		return deleted;
	}

	@Override
	public final void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	@Override
	public final boolean getFlagRead() {
		return flagRead;
	}

	@Override
	public final void setFlagRead(boolean read) {
		this.flagRead = read;
	}

	@Override
	public final boolean getFlagSynchronized() {
		return flagSynchronized;
	}

	@Override
	public final void setFlagSynchronized(boolean isSynchronized) {
		flagSynchronized = isSynchronized;
	}

	@Override
	public void reset() { /* Nothing to do */
	}

	protected void prepareForSave(Context context, String beanType) {
		Preferences prefs = Preferences.getPreferences(context);

		if (id == null) {
			id = BeanKeyGenerator.getNewId(beanType);
		}
		if (created == null) {
			created = NTPClock.getInstance(context).getTime();
		}
		if (uploadDate == null) {
			uploadDate = created;
		}
		modified = null;

		String login = prefs.getCurrentLogin();
		modifiedBy = login;
		if (createdBy == null) {
			createdBy = login;
		}
		modifiedFrom = prefs.getSyncTerminal();
		flagRead = true;
		flagSynchronized = false;
	}

	protected void preCommit(Context context) {
	}

	protected void postCommit(Context context) {
	}

	protected void addValues(Context context, ContentValues values) {
	}

	protected Uri saveOrUpdate(Context context, Uri contentUri) {
		preCommit(context);

		ContentValues values = new ContentValues();
		values.put(Columns._ID, id);
		values.put(Columns.MODIFIED, modified != null ? modified.getTime() : null);
		values.put(Columns.MODIFIEDBY, modifiedBy);
		values.put(Columns.MODIFIEDFROM, modifiedFrom);
		values.put(Columns.UPLOADDATE, uploadDate != null ? uploadDate.getTime() : null);
		values.put(Columns.CREATED, created != null ? created.getTime() : null);
		values.put(Columns.CREATEDBY, createdBy);
		values.put(Columns.DELETED, deleted != null ? deleted.getTime() : null);
		values.put(Columns.FLAG_READ, flagRead ? 1 : 0);
		values.put(Columns.FLAG_SYNCHRONIZED, flagSynchronized ? 1 : 0);

		addValues(context, values);

		if (modified == null) {
			modified = NTPClock.getInstance(context).getTime();
			values.put(Columns.MODIFIED, modified.getTime());
		}

		Uri uri = null;
		if (ImogOpenHelper.getHelper().exists(contentUri.getLastPathSegment(), id)) {
			uri = ContentUrisUtils.withAppendedId(contentUri, id);
			context.getContentResolver().update(uri, values, null, null);
		} else {
			uri = context.getContentResolver().insert(contentUri, values);
		}

		postCommit(context);

		return uri;
	}

	@Override
	public String getMainDisplay(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSecondaryDisplay(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeValue(modified != null ? modified.getTime() : null);
		dest.writeString(modifiedBy);
		dest.writeString(modifiedFrom);
		dest.writeValue(uploadDate != null ? uploadDate.getTime() : null);
		dest.writeValue(created != null ? created.getTime() : null);
		dest.writeString(createdBy);
		dest.writeValue(deleted != null ? deleted.getTime() : null);
		dest.writeInt(flagRead ? 1 : 0);
		dest.writeInt(flagSynchronized ? 1 : 0);
	}

}
