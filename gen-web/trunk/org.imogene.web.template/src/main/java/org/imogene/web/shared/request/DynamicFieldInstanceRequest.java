package org.imogene.web.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.web.server.handler.DynamicFieldInstanceHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.DynamicFieldInstanceProxy;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the DynamicFieldInstance Service.
 * @author Medes-IMPS
 */
@Service(value = DynamicFieldInstanceHandler.class, locator = SpringServiceLocator.class)
public interface DynamicFieldInstanceRequest extends RequestContext {

	Request<DynamicFieldInstanceProxy> findById(String id);

	Request<Void> save(DynamicFieldInstanceProxy c, boolean isNew);

	Request<List<DynamicFieldInstanceProxy>> listDynamicFieldInstance(
			int first, int max, String sortProperty, boolean sortOrder);
	Request<List<DynamicFieldInstanceProxy>> listDynamicFieldInstance(
			int first, int max, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions);
	Request<List<DynamicFieldInstanceProxy>> listDynamicFieldInstance(
			int first, int max, String sortProperty, boolean sortOrder,
			List<BasicCriteriaProxy> criterions);
	Request<List<DynamicFieldInstanceProxy>> listNonAffectedDynamicFieldInstance(
			int i, int j, String sortProperty, boolean sortOrder,
			String property);
	Request<List<DynamicFieldInstanceProxy>> listNonAffectedDynamicFieldInstance(
			int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<DynamicFieldInstanceProxy>> listNonAffectedDynamicFieldInstanceReverse(
			int i, int j, String sortProperty, boolean sortOrder,
			String property);
	Request<List<DynamicFieldInstanceProxy>> listNonAffectedDynamicFieldInstanceReverse(
			int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<DynamicFieldInstanceProxy>> getDynamicFieldInstanceEmptyList();

	Request<Long> countDynamicFieldInstance();
	Request<Long> countDynamicFieldInstance(ImogJunctionProxy criterions);
	Request<Long> countNonAffectedDynamicFieldInstance(String property);
	Request<Long> countNonAffectedDynamicFieldInstance(String property,
			ImogJunctionProxy criterions);
	Request<Long> countNonAffectedDynamicFieldInstanceReverse(String property);
	Request<Long> countNonAffectedDynamicFieldInstanceReverse(String property,
			ImogJunctionProxy criterions);

	Request<Void> delete(Set<DynamicFieldInstanceProxy> entities);
	Request<Void> delete(DynamicFieldInstanceProxy entity);

	Request<Void> saveFieldTemplate(DynamicFieldTemplateProxy entity,
			boolean isNew);
	Request<Void> deleteFieldTemplate(DynamicFieldTemplateProxy entity);
}
