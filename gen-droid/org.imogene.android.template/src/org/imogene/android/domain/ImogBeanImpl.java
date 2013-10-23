package org.imogene.android.domain;

import java.util.Date;

import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.util.BeanKeyGenerator;
import org.imogene.android.util.content.ContentUrisUtils;
import org.imogene.android.xml.annotation.XmlAlias;
import org.imogene.android.xml.annotation.XmlOmitField;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public abstract class ImogBeanImpl implements ImogBean {
	
	@XmlAlias("id")
	private String id = null;
	
	@XmlAlias("modified")
	private Date modified;
	
	@XmlAlias("modifiedBy")
	private String modifiedBy = null;
	
	@XmlAlias("modifiedFrom")
	private String modifiedFrom = null;
	
	@XmlAlias("uploadDate")
	private Date uploadDate;
	
	@XmlAlias("created")
	private Date created;
	
	@XmlAlias("createdBy")
	private String createdBy = null;
	
	@XmlOmitField
	private boolean unread = false;
	
	@XmlOmitField
	private boolean mSynchronized = false;
	
	protected void init(ImogBeanCursor cursor) {
		id = cursor.getId();
		modified = cursor.getModified();
		modifiedBy = cursor.getModifiedBy();
		modifiedFrom = cursor.getModifiedFrom();
		uploadDate = cursor.getUploadDate();
		created = cursor.getCreated();
		createdBy = cursor.getCreatedBy();
		unread = cursor.getUnread();
		mSynchronized = cursor.getSynchronized();
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
	public final boolean getUnread() {
		return unread;
	}

	@Override
	public final void setUnread(boolean unread) {
		this.unread = unread;
	}

	@Override
	public final boolean getSynchronized() {
		return mSynchronized;
	}

	@Override
	public final void setSynchronized(boolean isSynchronized) {
		mSynchronized = isSynchronized;
	}
	
	@Override
	public void reset() { /* Nothing to do */ }
	
	protected void prepareForSave(Context context, String beanType) {
		if (id == null) {
			id = BeanKeyGenerator.getNewId(beanType);
		}
		if (created == null) {
			created = PreferenceHelper.getRealTime(context);
		}
		if (uploadDate == null) {
			uploadDate = created;
		}
		modified = null;
		
		String login = PreferenceHelper.getCurrentLogin(context);
		modifiedBy = login;
		if (createdBy == null) {
			createdBy = login;
		}
		modifiedFrom = PreferenceHelper.getHardwareId(context);
		mSynchronized = false;
	}
	
	protected void preCommit(Context context) {/* nothing to do */}
	
	protected void postCommit(Context context) { /* nothing to do */ }
	
	protected abstract void addValues(Context context, ContentValues values);
	
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
		values.put(Columns.UNREAD, unread ? 1 : 0);
		values.put(Columns.SYNCHRONIZED, mSynchronized ? 1 : 0);
		
		addValues(context, values);
		
		if (modified == null) {
			modified = PreferenceHelper.getRealTime(context);
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
	
}
