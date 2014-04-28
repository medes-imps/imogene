package org.imogene.admin.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.web.server.handler.FieldGroupHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.FieldGroupProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;
import org.imogene.web.shared.request.ImogEntityRequest;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the FieldGroup Service.
 * 
 * @author Medes-IMPS
 */
@Service(value = FieldGroupHandler.class, locator = SpringServiceLocator.class)
public interface FieldGroupRequest extends ImogEntityRequest {

	Request<FieldGroupProxy> findById(String id);

	Request<Void> save(FieldGroupProxy c, boolean isNew);

	Request<List<FieldGroupProxy>> listFieldGroup(String sortProperty, boolean sortOrder);

	Request<List<FieldGroupProxy>> listFieldGroup(int first, int max, String sortProperty, boolean sortOrder);

	Request<List<FieldGroupProxy>> listFieldGroup(int first, int max, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions);

	Request<List<FieldGroupProxy>> listFieldGroup(int first, int max, String sortProperty, boolean sortOrder,
			List<BasicCriteriaProxy> criterions);

	Request<List<FieldGroupProxy>> listNonAffectedFieldGroup(int i, int j, String sortProperty, boolean sortOrder,
			String property);

	Request<List<FieldGroupProxy>> listNonAffectedFieldGroup(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);

	Request<List<FieldGroupProxy>> listNonAffectedFieldGroupReverse(int i, int j, String sortProperty,
			boolean sortOrder, String property);

	Request<List<FieldGroupProxy>> listNonAffectedFieldGroupReverse(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunctionProxy criterions, String property);

	Request<List<FieldGroupProxy>> getFieldGroupEmptyList();

	Request<Long> countFieldGroup();

	Request<Long> countFieldGroup(ImogJunctionProxy criterions);

	Request<Long> countNonAffectedFieldGroup(String property);

	Request<Long> countNonAffectedFieldGroup(String property, ImogJunctionProxy criterions);

	Request<Long> countNonAffectedFieldGroupReverse(String property);

	Request<Long> countNonAffectedFieldGroupReverse(String property, ImogJunctionProxy criterions);

	Request<Void> delete(Set<FieldGroupProxy> entities);

	Request<Void> delete(FieldGroupProxy entity);

	Request<Void> save(ImogBeanProxy entity, boolean isNew);

	Request<Void> delete(ImogBeanProxy entity);
}
