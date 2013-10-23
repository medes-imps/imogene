package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.web.server.handler.BinaryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate BinaryFile beans 
 * @author Medes-IMPS
 */
public class BinaryFileLocator extends Locator<BinaryFile, String> {

	private BinaryHandler handler;

	public BinaryFileLocator() {

	}

	@Override
	public BinaryFile create(Class<? extends BinaryFile> clazz) {
		return new BinaryFile();
	}

	@Override
	public BinaryFile find(Class<? extends BinaryFile> clazz, String id) {
		if (handler == null)
			initHandler();
		return (BinaryFile)handler.getBinary(id);
	}

	@Override
	public Class<BinaryFile> getDomainType() {
		return BinaryFile.class;
	}

	@Override
	public String getId(BinaryFile domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(BinaryFile domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		handler = (BinaryHandler) context.getBean("binaryHandler");
	}
}
