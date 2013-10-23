package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.entity.ImogBeanImpl;
import org.imogene.web.server.handler.GenericHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate ImogBeanImpl beans 
 * @author Medes-IMPS
 */
public class ImogBeanImplLocator extends Locator<ImogBeanImpl, String> {

	private GenericHandler handler;

	public ImogBeanImplLocator() {

	}

	@Override
	public ImogBeanImpl create(Class<? extends ImogBeanImpl> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ImogBeanImpl find(Class<? extends ImogBeanImpl> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.find(clazz, id);
	}

	@Override
	public Class<ImogBeanImpl> getDomainType() {
		return ImogBeanImpl.class;
	}

	@Override
	public String getId(ImogBeanImpl domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(ImogBeanImpl domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		handler = (GenericHandler) context.getBean("genericHandler");
	}
}
