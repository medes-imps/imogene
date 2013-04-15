package org.imogene.admin.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.admin.server.handler.SynchronizableEntityHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.SynchronizableEntityProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the SynchronizableEntity Service.
 * @author Medes-IMPS
 */
@Service(value = SynchronizableEntityHandler.class, locator = SpringServiceLocator.class)
public interface SynchronizableEntityRequest extends RequestContext {

	Request<SynchronizableEntityProxy> findById(String id);

	Request<Void> save(SynchronizableEntityProxy c, boolean isNew);

	Request<List<SynchronizableEntityProxy>> listSynchronizableEntity(
			int first, int max, String sortProperty, boolean sortOrder);
	Request<List<SynchronizableEntityProxy>> listSynchronizableEntity(
			int first, int max, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions);
	Request<List<SynchronizableEntityProxy>> listSynchronizableEntity(
			int first, int max, String sortProperty, boolean sortOrder,
			List<BasicCriteriaProxy> criterions);
	Request<List<SynchronizableEntityProxy>> listNonAffectedSynchronizableEntity(
			int i, int j, String sortProperty, boolean sortOrder,
			String property);
	Request<List<SynchronizableEntityProxy>> listNonAffectedSynchronizableEntity(
			int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<SynchronizableEntityProxy>> listNonAffectedSynchronizableEntityReverse(
			int i, int j, String sortProperty, boolean sortOrder,
			String property);
	Request<List<SynchronizableEntityProxy>> listNonAffectedSynchronizableEntityReverse(
			int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<SynchronizableEntityProxy>> getSynchronizableEntityEmptyList();

	Request<Long> countSynchronizableEntity();
	Request<Long> countSynchronizableEntity(ImogJunctionProxy criterions);
	Request<Long> countNonAffectedSynchronizableEntity(String property);
	Request<Long> countNonAffectedSynchronizableEntity(String property,
			ImogJunctionProxy criterions);
	Request<Long> countNonAffectedSynchronizableEntityReverse(String property);
	Request<Long> countNonAffectedSynchronizableEntityReverse(String property,
			ImogJunctionProxy criterions);

	Request<Void> delete(Set<SynchronizableEntityProxy> entities);
	Request<Void> delete(SynchronizableEntityProxy entity);

}
