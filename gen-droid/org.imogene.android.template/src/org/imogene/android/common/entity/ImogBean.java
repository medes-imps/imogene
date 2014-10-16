package org.imogene.android.common.entity;

import java.util.Date;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.BaseColumns;

public interface ImogBean extends Parcelable {

	public static interface Columns extends BaseColumns {
		public static final String MODIFIED = "modified";
		public static final String UPLOADDATE = "uploadDate";
		public static final String MODIFIEDBY = "modifiedBy";
		public static final String MODIFIEDFROM = "modifiedFrom";
		public static final String CREATED = "created";
		public static final String CREATEDBY = "createdBy";
		public static final String DELETED = "deleted";
		public static final String FLAG_READ = "flagRead";
		public static final String FLAG_SYNCHRONIZED = "flagSynchronized";

		public static final String SYNC_SYSTEM = "sync-system";
	}

	public String getId();

	public void setId(String id);

	public Date getModified();

	public void setModified(Date modified);

	public String getModifiedBy();

	public void setModifiedBy(String modifiedBy);

	public String getModifiedFrom();

	public void setModifiedFrom(String modifiedFrom);

	public Date getUploadDate();

	public void setUploadDate(Date uploadDate);

	public Date getCreated();

	public void setCreated(Date created);

	public Date getDeleted();

	public void setDeleted(Date deleted);

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);

	public boolean getFlagRead();

	public void setFlagRead(boolean unread);

	public boolean getFlagSynchronized();

	public void setFlagSynchronized(boolean isSynchronized);

	public void prepareForSave(Context context);

	public Uri saveOrUpdate(Context context);

	public void reset();

	public String getMainDisplay(Context context);

	public String getSecondaryDisplay(Context context);
}
