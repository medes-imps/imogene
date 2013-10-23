package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.role.ImogRole;
import org.imogene.web.server.handler.ImogRoleHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate ImogRole beans
 * @author Medes-IMPS
 */
public class ImogRoleLocator extends Locator<ImogRole, String> {

	private ImogRoleHandler handler;

	public ImogRoleLocator() {

	}

	@Override
	public ImogRole create(Class<? extends ImogRole> clazz) {
		return new ImogRole();
	}

	@Override
	public ImogRole find(Class<? extends ImogRole> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.findById(id);
	}

	@Override
	public Class<ImogRole> getDomainType() {
		return ImogRole.class;
	}

	@Override
	public String getId(ImogRole domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(ImogRole domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		handler = (ImogRoleHandler) context.getBean("imogRoleHandler");
	}
}
