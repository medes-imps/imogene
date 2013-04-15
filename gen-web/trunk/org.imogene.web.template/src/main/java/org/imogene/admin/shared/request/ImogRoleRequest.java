package org.imogene.admin.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.web.server.handler.ImogRoleHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.ImogRoleProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the ImogRole Service.
 * @author Medes-IMPS
 */
@Service(value = ImogRoleHandler.class, locator = SpringServiceLocator.class)
public interface ImogRoleRequest extends RequestContext {

	Request<ImogRoleProxy> findById(String id);

	Request<Void> save(ImogRoleProxy c, boolean isNew);

	Request<List<ImogRoleProxy>> listImogRole(int first, int max,
			String sortProperty, boolean sortOrder);
	Request<List<ImogRoleProxy>> listImogRole(int first, int max,
			String sortProperty, boolean sortOrder, ImogJunctionProxy criterions);
	Request<List<ImogRoleProxy>> listImogRole(int first, int max,
			String sortProperty, boolean sortOrder,
			List<BasicCriteriaProxy> criterions);
	Request<List<ImogRoleProxy>> listNonAffectedImogRole(int i, int j,
			String sortProperty, boolean sortOrder, String property);
	Request<List<ImogRoleProxy>> listNonAffectedImogRole(int i, int j,
			String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<ImogRoleProxy>> listNonAffectedImogRoleReverse(int i, int j,
			String sortProperty, boolean sortOrder, String property);
	Request<List<ImogRoleProxy>> listNonAffectedImogRoleReverse(int i, int j,
			String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<ImogRoleProxy>> getImogRoleEmptyList();

	Request<Long> countImogRole();
	Request<Long> countImogRole(ImogJunctionProxy criterions);
	Request<Long> countNonAffectedImogRole(String property);
	Request<Long> countNonAffectedImogRole(String property,
			ImogJunctionProxy criterions);
	Request<Long> countNonAffectedImogRoleReverse(String property);
	Request<Long> countNonAffectedImogRoleReverse(String property,
			ImogJunctionProxy criterions);

	Request<Void> delete(Set<ImogRoleProxy> entities);
	Request<Void> delete(ImogRoleProxy entity);

}
