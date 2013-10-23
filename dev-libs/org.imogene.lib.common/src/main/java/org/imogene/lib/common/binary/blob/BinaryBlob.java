package org.imogene.lib.common.binary.blob;

import java.io.InputStream;

import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.entity.ImogBeanImpl;

public class BinaryBlob extends ImogBeanImpl implements Binary {

	private static final long serialVersionUID = -8523160137555183461L;

	/* binary info */
	private String fileName;
	private String contentType;
	private long length;
	private BlobStream content;

	public BinaryBlob() {
		content = new BlobStream();
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public long getLength() {
		return length;
	}

	@Override
	public void setLength(long length) {
		this.length = length;
	}

	public BlobStream getContent() {
		return content;
	}

	public void setContent(BlobStream content) {
		this.content = content;
	}

	@Override
	public InputStream createInputStream() {
		if (content != null) {
			return content.createInputStream();
		}
		return null;
	}

	public void setContent(InputStream sourceStream) {
		content.setStream(sourceStream);
	}

}
