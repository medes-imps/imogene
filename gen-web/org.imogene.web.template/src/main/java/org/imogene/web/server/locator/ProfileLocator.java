package org.imogene.web.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.lib.common.profile.Profile;
import org.imogene.web.server.handler.ProfileHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate Profile beans
 * 
 * @author Medes-IMPS
 */
public class ProfileLocator extends Locator<Profile, String> {

	private ProfileHandler handler;

	public ProfileLocator() {

	}

	@Override
	public Profile create(Class<? extends Profile> clazz) {
		return new Profile();
	}

	@Override
	public Profile find(Class<? extends Profile> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.getById(id);
	}

	@Override
	public Class<Profile> getDomainType() {
		return Profile.class;
	}

	@Override
	public String getId(Profile domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(Profile domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession()
				.getServletContext());
		handler = (ProfileHandler) context.getBean("profileHandler");
	}
}
