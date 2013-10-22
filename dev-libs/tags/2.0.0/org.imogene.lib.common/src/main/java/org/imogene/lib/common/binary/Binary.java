package org.imogene.lib.common.binary;

import java.io.InputStream;

import org.imogene.lib.common.entity.ImogBean;

public interface Binary extends ImogBean {

	public String getFileName();

	public void setFileName(String name);

	public String getContentType();

	public void setContentType(String contentType);

	public long getLength();

	public void setLength(long _length);

	public InputStream createInputStream();

}
