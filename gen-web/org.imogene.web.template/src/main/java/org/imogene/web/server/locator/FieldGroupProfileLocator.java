package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.web.server.handler.FieldGroupProfileHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate FieldGroupProfile beans
 * 
 * @author Medes-IMPS
 */
public class FieldGroupProfileLocator extends Locator<FieldGroupProfile, String> {

	private FieldGroupProfileHandler handler;

	public FieldGroupProfileLocator() {

	}

	@Override
	public FieldGroupProfile create(Class<? extends FieldGroupProfile> clazz) {
		return new FieldGroupProfile();
	}

	@Override
	public FieldGroupProfile find(Class<? extends FieldGroupProfile> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.getById(id);
	}

	@Override
	public Class<FieldGroupProfile> getDomainType() {
		return FieldGroupProfile.class;
	}

	@Override
	public String getId(FieldGroupProfile domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(FieldGroupProfile domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession()
				.getServletContext());
		handler = (FieldGroupProfileHandler) context.getBean("fieldGroupProfileHandler");
	}
}
