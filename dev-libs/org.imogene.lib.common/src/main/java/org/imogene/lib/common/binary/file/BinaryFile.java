package org.imogene.lib.common.binary.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.entity.ImogBeanImpl;

@Entity
@Table(name="binary_file")
public class BinaryFile extends ImogBeanImpl implements Binary {

	private static final long serialVersionUID = -2465662621898667723L;

	private String fileName;
	private String contentType;
	private long length;

	public BinaryFile() {
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

	@Override
	public InputStream createInputStream() {
		try {
			return new FileInputStream(BinaryFileManager.getInstance().buildFilePath(getId(), fileName));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
