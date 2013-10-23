package org.imogene.admin.shared;

import org.imogene.admin.shared.request.ImogRoleRequest;
import org.imogene.admin.shared.request.NotificationRequest;
import org.imogene.admin.shared.request.SynchronizableEntityRequest;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.request.ImogActorRequest;

/**
 * RequestFactory of the application
 * @author Medes-IMPS
 */
public interface AdminRequestFactory extends ImogRequestFactory {

	ImogRoleRequest imogRoleRequest();
	SynchronizableEntityRequest synchronizableEntityRequest();
	NotificationRequest notificationRequest();
	
	ImogActorRequest imogActorRequest();

}
