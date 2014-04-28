package org.imogene.admin.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.web.server.handler.FieldGroupProfileHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.FieldGroupProfileProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;
import org.imogene.web.shared.request.ImogEntityRequest;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the FieldGroupProfile Service.
 * 
 * @author Medes-IMPS
 */
@Service(value = FieldGroupProfileHandler.class, locator = SpringServiceLocator.class)
public interface FieldGroupProfileRequest extends ImogEntityRequest {

	Request<FieldGroupProfileProxy> findById(String id);

	Request<Void> save(FieldGroupProfileProxy c, boolean isNew);

	Request<List<FieldGroupProfileProxy>> listFieldGroupProfile(String sortProperty, boolean sortOrder);

	Request<List<FieldGroupProfileProxy>> listFieldGroupProfile(int first, int max, String sortProperty,
			boolean sortOrder);

	Request<List<FieldGroupProfileProxy>> listFieldGroupProfile(int first, int max, String sortProperty,
			boolean sortOrder, ImogJunctionProxy criterions);

	Request<List<FieldGroupProfileProxy>> listFieldGroupProfile(int first, int max, String sortProperty,
			boolean sortOrder, List<BasicCriteriaProxy> criterions);

	Request<List<FieldGroupProfileProxy>> listNonAffectedFieldGroupProfile(int i, int j, String sortProperty,
			boolean sortOrder, String property);

	Request<List<FieldGroupProfileProxy>> listNonAffectedFieldGroupProfile(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunctionProxy criterions, String property);

	Request<List<FieldGroupProfileProxy>> listNonAffectedFieldGroupProfileReverse(int i, int j, String sortProperty,
			boolean sortOrder, String property);

	Request<List<FieldGroupProfileProxy>> listNonAffectedFieldGroupProfileReverse(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunctionProxy criterions, String property);

	Request<List<FieldGroupProfileProxy>> getFieldGroupProfileEmptyList();

	Request<Long> countFieldGroupProfile();

	Request<Long> countFieldGroupProfile(ImogJunctionProxy criterions);

	Request<Long> countNonAffectedFieldGroupProfile(String property);

	Request<Long> countNonAffectedFieldGroupProfile(String property, ImogJunctionProxy criterions);

	Request<Long> countNonAffectedFieldGroupProfileReverse(String property);

	Request<Long> countNonAffectedFieldGroupProfileReverse(String property, ImogJunctionProxy criterions);

	Request<Void> delete(Set<FieldGroupProfileProxy> entities);

	Request<Void> delete(FieldGroupProfileProxy entity);

	Request<Void> save(ImogBeanProxy entity, boolean isNew);

	Request<Void> delete(ImogBeanProxy entity);
}
