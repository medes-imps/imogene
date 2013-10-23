package org.imogene.web.shared.proxy;

import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.web.server.locator.BinaryFileLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;


@ProxyFor(value = BinaryFile.class, locator = BinaryFileLocator.class)
public interface BinaryProxy extends ImogBeanProxy {
	
	public String getFileName();

	public void setFileName(String fileName);

	public String getContentType();

	public void setContentType(String contentType);

	public long getLength();

	public void setLength(long length);

}
