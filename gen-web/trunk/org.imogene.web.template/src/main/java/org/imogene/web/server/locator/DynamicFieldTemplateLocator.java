package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.web.server.handler.DynamicFieldTemplateHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate DynamicFieldTemplate beans 
 * @author Medes-IMPS
 */
public class DynamicFieldTemplateLocator
		extends
			Locator<DynamicFieldTemplate, String> {

	private DynamicFieldTemplateHandler handler;

	public DynamicFieldTemplateLocator() {

	}

	@Override
	public DynamicFieldTemplate create(
			Class<? extends DynamicFieldTemplate> clazz) {
		return new DynamicFieldTemplate();
	}

	@Override
	public DynamicFieldTemplate find(
			Class<? extends DynamicFieldTemplate> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.findById(id);
	}

	@Override
	public Class<DynamicFieldTemplate> getDomainType() {
		return DynamicFieldTemplate.class;
	}

	@Override
	public String getId(DynamicFieldTemplate domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(DynamicFieldTemplate domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		handler = (DynamicFieldTemplateHandler) context
				.getBean("dynamicFieldTemplateHandler");
	}
}
