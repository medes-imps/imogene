package org.imogene.admin.shared;

import org.imogene.admin.shared.request.CardEntityRequest;
import org.imogene.admin.shared.request.EntityProfileRequest;
import org.imogene.admin.shared.request.FieldGroupProfileRequest;
import org.imogene.admin.shared.request.FieldGroupRequest;
import org.imogene.admin.shared.request.NotificationRequest;
import org.imogene.admin.shared.request.ProfileRequest;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.request.ImogActorRequest;

/**
 * RequestFactory of the application
 * 
 * @author Medes-IMPS
 */
public interface AdminRequestFactory extends ImogRequestFactory {

	NotificationRequest notificationRequest();

	ImogActorRequest imogActorRequest();

	ProfileRequest profileRequest();

	EntityProfileRequest entityProfileRequest();

	FieldGroupProfileRequest fieldGroupProfileRequest();

	CardEntityRequest cardEntityRequest();

	FieldGroupRequest fieldGroupRequest();

}
