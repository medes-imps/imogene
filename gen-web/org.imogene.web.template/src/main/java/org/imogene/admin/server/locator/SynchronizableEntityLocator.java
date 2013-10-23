package org.imogene.admin.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.admin.server.handler.SynchronizableEntityHandler;
import org.imogene.lib.common.sync.entity.SynchronizableEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate SynchronizableEntity beans 
 * @author Medes-IMPS
 */
public class SynchronizableEntityLocator
		extends
			Locator<SynchronizableEntity, String> {

	private SynchronizableEntityHandler handler;

	public SynchronizableEntityLocator() {

	}

	@Override
	public SynchronizableEntity create(
			Class<? extends SynchronizableEntity> clazz) {
		return new SynchronizableEntity();
	}

	@Override
	public SynchronizableEntity find(
			Class<? extends SynchronizableEntity> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.findById(id);
	}

	@Override
	public Class<SynchronizableEntity> getDomainType() {
		return SynchronizableEntity.class;
	}

	@Override
	public String getId(SynchronizableEntity domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(SynchronizableEntity domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		handler = (SynchronizableEntityHandler) context
				.getBean("synchronizableEntityHandler");
	}
}
