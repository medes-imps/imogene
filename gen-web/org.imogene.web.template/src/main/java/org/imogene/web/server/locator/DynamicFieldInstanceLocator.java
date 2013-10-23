package org.imogene.web.server.locator;
import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.dynamicfields.DynamicFieldInstance;
import org.imogene.web.server.handler.DynamicFieldInstanceHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate DynamicFieldInstance beans 
 * @author Medes-IMPS
 */
public class DynamicFieldInstanceLocator
		extends
			Locator<DynamicFieldInstance, String> {

	private DynamicFieldInstanceHandler handler;

	public DynamicFieldInstanceLocator() {

	}

	@Override
	public DynamicFieldInstance create(
			Class<? extends DynamicFieldInstance> clazz) {
		return new DynamicFieldInstance();
	}

	@Override
	public DynamicFieldInstance find(
			Class<? extends DynamicFieldInstance> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.findById(id);
	}

	@Override
	public Class<DynamicFieldInstance> getDomainType() {
		return DynamicFieldInstance.class;
	}

	@Override
	public String getId(DynamicFieldInstance domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(DynamicFieldInstance domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		handler = (DynamicFieldInstanceHandler) context
				.getBean("dynamicFieldInstanceHandler");
	}
}
