package org.imogene.android.common.binary;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.BinaryCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.xml.converters.ContentConverter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import fr.medes.android.util.file.FileUtils;
import fr.medes.android.util.file.MimeType;
import fr.medes.android.xml.annotation.XmlAlias;
import fr.medes.android.xml.annotation.XmlConverter;

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

	public BinaryFile(BinaryCursor<BinaryFile> c) {
		super(c);
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
		prepareForSave(context, Binary.Columns.BEAN_TYPE);
	}

	@Override
	public Uri saveOrUpdate(Context context) {
		Uri uri = saveOrUpdate(context, Binary.Columns.CONTENT_URI);
		if (data != null) {
			try {
				FileUtils.appendFile(context.getContentResolver(), data, uri);
				FileUtils.deleteFile(data.getPath());
				data = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return uri;
	}

	@Override
	protected void addValues(Context context, ContentValues values) {
		super.addValues(context, values);
		values.put(Binary.Columns.CONTENT_TYPE, contentType);
		values.put(Binary.Columns.FILE_NAME, fileName);
		values.put(Binary.Columns.LENGTH, length);
	}

	public static boolean isBinary(Uri uri) {
		return uri != null && Constants.AUTHORITY.equals(uri.getAuthority());
	}

	public static Binary toBinary(Context context, Uri data) {
		if (data == null) {
			return null;
		}
		if (isBinary(data)) {
			return ImogOpenHelper.fromUri(data);
		}

		ContentResolver r = context.getContentResolver();

		long length = 0;
		try {
			ParcelFileDescriptor fd = r.openFileDescriptor(data, "r");
			length = fd.getStatSize();
		} catch (FileNotFoundException e1) {
			return null;
		}

		MimeType mime = MimeType.getInstance(context);
		String contentType = r.getType(data);
		if (contentType == null) {
			contentType = mime.guessMimeType(data.toString());
		}
		String extension = mime.getExtension(contentType);

		BinaryFile binary = new BinaryFile();
		binary.prepareForSave(context);
		binary.setContentType(contentType);
		binary.setFileName(binary.getId() + (extension == null ? ".bin" : extension));
		binary.setLength(length);
		binary.setModifiedFrom(Binary.Columns.SYNC_TMP);

		Uri uri = binary.saveOrUpdate(context);

		try {
			FileUtils.appendFile(context.getContentResolver(), data, uri);
			return binary;
		} catch (IOException e1) {
			if (uri != null) {
				r.delete(uri, null, null);
			}
			return null;
		}
	}

}
