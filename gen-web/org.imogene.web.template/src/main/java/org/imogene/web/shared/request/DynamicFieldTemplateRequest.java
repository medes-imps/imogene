package org.imogene.web.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.web.server.handler.DynamicFieldTemplateHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the DynamicFieldTemplate Service.
 * @author Medes-IMPS
 */
@Service(value = DynamicFieldTemplateHandler.class, locator = SpringServiceLocator.class)
public interface DynamicFieldTemplateRequest extends RequestContext {

	Request<DynamicFieldTemplateProxy> findById(String id);

	Request<Void> save(DynamicFieldTemplateProxy c, boolean isNew);
	
//	Request<Void> activateDynamicFieldTemplate(DynamicFieldTemplateProxy entity, boolean activate);

	Request<List<DynamicFieldTemplateProxy>> listDynamicFieldTemplate(
			int first, int max, String sortProperty, boolean sortOrder);
	Request<List<DynamicFieldTemplateProxy>> listDynamicFieldTemplate(
			int first, int max, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions);
	Request<List<DynamicFieldTemplateProxy>> listDynamicFieldTemplate(
			int first, int max, String sortProperty, boolean sortOrder,
			List<BasicCriteriaProxy> criterions);
	Request<List<DynamicFieldTemplateProxy>> listNonAffectedDynamicFieldTemplate(
			int i, int j, String sortProperty, boolean sortOrder,
			String property);
	Request<List<DynamicFieldTemplateProxy>> listNonAffectedDynamicFieldTemplate(
			int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<DynamicFieldTemplateProxy>> listNonAffectedDynamicFieldTemplateReverse(
			int i, int j, String sortProperty, boolean sortOrder,
			String property);
	Request<List<DynamicFieldTemplateProxy>> listNonAffectedDynamicFieldTemplateReverse(
			int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);
	Request<List<DynamicFieldTemplateProxy>> getDynamicFieldTemplateEmptyList();

	Request<Long> countDynamicFieldTemplate();
	Request<Long> countDynamicFieldTemplate(ImogJunctionProxy criterions);
	Request<Long> countNonAffectedDynamicFieldTemplate(String property);
	Request<Long> countNonAffectedDynamicFieldTemplate(String property,
			ImogJunctionProxy criterions);
	Request<Long> countNonAffectedDynamicFieldTemplateReverse(String property);
	Request<Long> countNonAffectedDynamicFieldTemplateReverse(String property,
			ImogJunctionProxy criterions);

	Request<Void> delete(Set<DynamicFieldTemplateProxy> entities);
	Request<Void> delete(DynamicFieldTemplateProxy entity);
	
	Request<List<DynamicFieldTemplateProxy>> listDynamicFieldTemplate(String formType);
}
