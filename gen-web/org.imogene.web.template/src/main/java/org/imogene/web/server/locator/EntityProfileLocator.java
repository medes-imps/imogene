package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.profile.EntityProfile;
import org.imogene.web.server.handler.EntityProfileHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate EntityProfile beans 
 * @author Medes-IMPS
 */
public class EntityProfileLocator extends Locator<EntityProfile, String> {

	private EntityProfileHandler handler;

	public EntityProfileLocator() {

	}

	@Override
	public EntityProfile create(Class<? extends EntityProfile> clazz) {
		return new EntityProfile();
	}

	@Override
	public EntityProfile find(Class<? extends EntityProfile> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.getById(id);
	}

	@Override
	public Class<EntityProfile> getDomainType() {
		return EntityProfile.class;
	}

	@Override
	public String getId(EntityProfile domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(EntityProfile domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		handler = (EntityProfileHandler) context
				.getBean("entityProfileHandler");
	}
}
