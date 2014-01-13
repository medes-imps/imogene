package org.imogene.admin.server.locator;

import javax.servlet.http.HttpServletRequest;

import org.imogene.admin.server.handler.NotificationHandler;
import org.imogene.lib.common.notification.Notification;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * A Locator to locate Notification beans 
 * @author Medes-IMPS
 */
public class NotificationLocator extends Locator<Notification, String> {

	private NotificationHandler handler;

	public NotificationLocator() {

	}

	@Override
	public Notification create(Class<? extends Notification> clazz) {
		return new Notification();
	}

	@Override
	public Notification find(Class<? extends Notification> clazz, String id) {
		if (handler == null)
			initHandler();
		return handler.findById(id);
	}

	@Override
	public Class<Notification> getDomainType() {
		return Notification.class;
	}

	@Override
	public String getId(Notification domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(Notification domainObject) {
		return domainObject.getVersion();
	}

	private void initHandler() {
		HttpServletRequest request = RequestFactoryServlet
				.getThreadLocalRequest();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession()
						.getServletContext());
		handler = (NotificationHandler) context.getBean("notificationHandler");
	}
}
