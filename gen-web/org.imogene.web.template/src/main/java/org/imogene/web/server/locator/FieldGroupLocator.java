package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.model.FieldGroup;
import org.imogene.web.server.handler.FieldGroupHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate FieldGroup beans
 * 
 * @author Medes-IMPS
 */
public class FieldGroupLocator extends Locator<FieldGroup, String> {

	private FieldGroupHandler handler;

	public FieldGroupLocator() {

	}

	@Override
	public FieldGroup create(Class<? extends FieldGroup> clazz) {
		return new FieldGroup();
	}

	@Override
	public FieldGroup find(Class<? extends FieldGroup> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.getById(id);
	}

	@Override
	public Class<FieldGroup> getDomainType() {
		return FieldGroup.class;
	}

	@Override
	public String getId(FieldGroup domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(FieldGroup domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession()
				.getServletContext());
		handler = (FieldGroupHandler) context.getBean("fieldGroupHandler");
	}
}
