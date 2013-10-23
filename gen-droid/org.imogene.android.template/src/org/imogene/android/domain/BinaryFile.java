package org.imogene.android.domain;

import java.io.IOException;
import java.util.Date;

import org.imogene.android.Constants;
import org.imogene.android.database.BinaryCursor;
import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.util.BeanKeyGenerator;
import org.imogene.android.util.file.FileUtils;
import org.imogene.android.util.file.MimeType;
import org.imogene.android.xml.annotation.XmlAlias;
import org.imogene.android.xml.annotation.XmlConverter;
import org.imogene.android.xml.converters.ContentConverter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

@XmlAlias("org.imogene.lib.common.binary.file.BinaryFile")
public final class BinaryFile extends ImogBeanImpl implements Binary {
	
	@XmlAlias("fileName")
	private String fileName = null;
	
	@XmlAlias("contentType")
	private String contentType = null;
	
	@XmlAlias("length")
	private long length = 0;
	
	@XmlAlias("content")
	@XmlConverter(ContentConverter.class)
	private Uri data = null;

	public BinaryFile() {
	}
	
	public BinaryFile(BinaryCursor c) {
		init(c);
		fileName = c.getFileName();
		contentType = c.getContentType();
		length = c.getLength();
		data = c.getData();
	}

	@Override
	public final String getFileName() {
		return fileName;
	}

	@Override
	public final void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public final String getContentType() {
		return contentType;
	}

	@Override
	public final void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public final long getLength() {
		return length;
	}

	@Override
	public final void setLength(long length) {
		this.length = length;
	}

	@Override
	public final Uri getData() {
		return data;
	}

	@Override
	public final void setData(Uri uri) {
		data = uri;
	}

	@Override
	public void prepareForSave(Context context) {
		prepareForSave(context, Binary.Columns.TYPE);
	}
	
	@Override
	public Uri saveOrUpdate(Context context) {
		Uri uri = saveOrUpdate(context, Binary.Columns.CONTENT_URI);
		if (data != null) {
			try {
				FileUtils.appendFile(context.getContentResolver(), data, uri);
				data = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return uri;
	}
	
	@Override
	protected void addValues(Context context, ContentValues values) {
		values.put(Binary.Columns.CONTENT_TYPE, contentType);
		values.put(Binary.Columns.FILE_NAME, fileName);
		values.put(Binary.Columns.LENGTH, length);
	}
	
	public static boolean isBinary(Uri uri) {
		return uri != null && Constants.AUTHORITY.equals(uri.getAuthority());
	}
	
	public static Uri toBinary(Context context, Uri data) {
		if (data == null || isBinary(data)) {
			return data;
		}
		
		String login = PreferenceHelper.getCurrentLogin(context);
		
		String id = BeanKeyGenerator.getNewId(Binary.Columns.TYPE);
		
		ContentResolver r = context.getContentResolver();
		
		MimeType mime = MimeType.getInstance(context);
		String contentType = r.getType(data);
		if (contentType == null) {
			contentType = mime.guessMimeType(data.toString());
		}
		
		String extension = mime.getExtension(contentType);
		String fileName = id + (extension == null ? ".bin" : extension);

		BinaryFile binary = new BinaryFile();
		binary.setId(id);
		binary.setCreated(PreferenceHelper.getRealTime(context));
		binary.setCreatedBy(login);
		binary.setModified(new Date(0));
		binary.setModifiedBy(login);
		binary.setModifiedFrom(PreferenceHelper.getHardwareId(context));
		binary.setSynchronized(false);

		binary.setContentType(contentType);
		binary.setFileName(fileName);
		
		Uri uri = binary.saveOrUpdate(context);
		
		try {
			FileUtils.appendFile(context.getContentResolver(), data, uri);

			ParcelFileDescriptor fd = r.openFileDescriptor(uri, "r");
			binary.setLength(fd.getStatSize());
			fd.close();

			binary.setModified(null);
			return binary.saveOrUpdate(context);
		} catch (Exception e) {
			if (uri != null)
				r.delete(uri, null, null);
			return null;
		}
	}

}
