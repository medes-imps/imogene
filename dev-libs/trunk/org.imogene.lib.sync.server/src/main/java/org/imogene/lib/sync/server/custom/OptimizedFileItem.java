package org.imogene.lib.sync.server.custom;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;

public class OptimizedFileItem implements FileItem {

	private static final long serialVersionUID = -122174345162303746L;

	private File medooFile;

	private OutputStream os;

	private String fieldName;

	private String contentType;

	private boolean isFormField;

	private String fileName;

	private int sizeThreshold;

	private File repository;

	public OptimizedFileItem(String fieldName, String contentType, boolean isFormField, String fileName,
			int sizeThreshold, File repository) {
		this.fieldName = fieldName;
		this.contentType = contentType;
		this.isFormField = isFormField;
		this.fileName = fileName;
		this.sizeThreshold = sizeThreshold;
		this.repository = repository;

	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(medooFile);
	}

	@Override
	public boolean isInMemory() {
		return false;
	}

	@Override
	public long getSize() {
		return medooFile.length();
	}

	@Override
	public byte[] get() {

		byte[] fileData = new byte[(int) getSize()];
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(medooFile);
			fis.read(fileData);
		} catch (IOException e) {
			fileData = null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return fileData;
	}

	@Override
	public void write(File file) throws Exception {

		if (medooFile != null) {
			/*
			 * The uploaded file is being stored on disk in a temporary location
			 * so move it to the desired file.
			 */
			if (!medooFile.renameTo(file)) {
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = new BufferedInputStream(new FileInputStream(medooFile));
					out = new BufferedOutputStream(new FileOutputStream(file));
					IOUtils.copy(in, out);
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							// ignore
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
							// ignore
						}
					}
				}
			}
		} else {
			/*
			 * For whatever reason we cannot write the file to disk.
			 */
			throw new FileUploadException("Cannot write uploaded file to disk!");
		}
	}

	/**
	 * When we resume synchro we never delete the file here.
	 */
	@Override
	public void delete() {

	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		if (os == null) {
			if (repository == null)
				throw new RuntimeException("repository is null !!!!!!");
			if (fileName == null)
				throw new RuntimeException("filename is null !!!!!!");
			medooFile = new File(repository, fileName);
			if (medooFile.exists())
				return os = new FileOutputStream(medooFile, true);
			return new FileOutputStream(medooFile);
		}
		return os;
	}

	public File getStoreLocation() {
		return medooFile;
	}

	@Override
	protected void finalize() {
		/*
		 * File outputFile = medooFile;
		 * 
		 * if (outputFile != null && outputFile.exists()) { outputFile.delete();
		 * }
		 */
		// WARNING - Never delete when resume.
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public boolean isFormField() {
		return isFormField;
	}

	@Override
	public void setFormField(boolean isFormField) {
		this.isFormField = isFormField;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSizeThreshold() {
		return sizeThreshold;
	}

	public void setSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}

	public File getRepository() {
		return repository;
	}

	public void setRepository(File repository) {
		this.repository = repository;
	}

	@Override
	public String getString(String arg0) throws UnsupportedEncodingException {
		return new String(get(), arg0);
	}

	@Override
	public String getName() {
		return this.fileName;
	}

	@Override
	public String getString() {
		return new String(get());
	}

	@Override
	public FileItemHeaders getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHeaders(FileItemHeaders headers) {
		// TODO Auto-generated method stub
		
	}

}
