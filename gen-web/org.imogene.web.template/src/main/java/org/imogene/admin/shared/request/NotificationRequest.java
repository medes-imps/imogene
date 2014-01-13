package org.imogene.admin.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.admin.server.handler.NotificationHandler;
import org.imogene.admin.shared.proxy.NotificationProxy;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the Notification Service.
 * @author Medes-IMPS
 */
@Service(value = NotificationHandler.class, locator = SpringServiceLocator.class)
public interface NotificationRequest extends RequestContext {

	Request<NotificationProxy> findById(String id);

	Request<Void> save(NotificationProxy c, boolean isNew);

	Request<List<NotificationProxy>> listNotification(int first, int max,
			String sortProperty, boolean sortOrder);
	Request<List<NotificationProxy>> listNotification(int first, int max,
			String sortProperty, boolean sortOrder, ImogJunctionProxy criterions);
	Request<List<NotificationProxy>> listNotification(int first, int max,
			String sortProperty, boolean sortOrder,
			List<BasicCriteriaProxy> criterions);
	Request<List<NotificationProxy>> listNonAffectedNotification(int i, int j,
			String sortProperty, boolean sortOrder, String property);
	Request<List<NotificationProxy>> listNonAffectedNotification(int i, int j,
			String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<NotificationProxy>> listNonAffectedNotificationReverse(int i,
			int j, String sortProperty, boolean sortOrder, String property);
	Request<List<NotificationProxy>> listNonAffectedNotificationReverse(int i,
			int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<NotificationProxy>> getNotificationEmptyList();

	Request<Long> countNotification();
	Request<Long> countNotification(ImogJunctionProxy criterions);
	Request<Long> countNonAffectedNotification(String property);
	Request<Long> countNonAffectedNotification(String property,
			ImogJunctionProxy criterions);
	Request<Long> countNonAffectedNotificationReverse(String property);
	Request<Long> countNonAffectedNotificationReverse(String property,
			ImogJunctionProxy criterions);

	Request<Void> delete(Set<NotificationProxy> entities);
	Request<Void> delete(NotificationProxy entity);

}
