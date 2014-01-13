package org.imogene.web.shared.request;

import java.util.List;

import org.imogene.web.server.handler.ImogActorHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the AppliUser Service.
 * @author Medes-IMPS
 */
@Service(value = ImogActorHandler.class, locator = SpringServiceLocator.class)
public interface ImogActorRequest extends RequestContext {

	Request<ImogActorProxy> findById(String id);

	Request<List<ImogActorProxy>> listAppliUser(int first, int max, String sortProperty, boolean sortOrder);

	Request<List<ImogActorProxy>> listAppliUser(int first, int max, String sortProperty, boolean sortOrder, ImogJunctionProxy criterions);

	Request<List<ImogActorProxy>> listAppliUser(int first, int max, String sortProperty, boolean sortOrder, List<BasicCriteriaProxy> criterions);

	Request<List<ImogActorProxy>> getAppliUserEmptyList();

	Request<Long> countAppliUser();

	Request<Long> countAppliUser(ImogJunctionProxy criterions);

}
