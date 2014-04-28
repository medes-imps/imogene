package org.imogene.admin.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.web.server.handler.CardEntityHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.CardEntityProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;
import org.imogene.web.shared.request.ImogEntityRequest;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the CardEntity Service.
 * 
 * @author Medes-IMPS
 */
@Service(value = CardEntityHandler.class, locator = SpringServiceLocator.class)
public interface CardEntityRequest extends ImogEntityRequest {

	Request<CardEntityProxy> findById(String id);

	Request<Void> save(CardEntityProxy c, boolean isNew);

	Request<List<CardEntityProxy>> listCardEntity(String sortProperty, boolean sortOrder);

	Request<List<CardEntityProxy>> listCardEntity(int first, int max, String sortProperty, boolean sortOrder);

	Request<List<CardEntityProxy>> listCardEntity(int first, int max, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions);

	Request<List<CardEntityProxy>> listCardEntity(int first, int max, String sortProperty, boolean sortOrder,
			List<BasicCriteriaProxy> criterions);

	Request<List<CardEntityProxy>> listNonAffectedCardEntity(int i, int j, String sortProperty, boolean sortOrder,
			String property);

	Request<List<CardEntityProxy>> listNonAffectedCardEntity(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);

	Request<List<CardEntityProxy>> listNonAffectedCardEntityReverse(int i, int j, String sortProperty,
			boolean sortOrder, String property);

	Request<List<CardEntityProxy>> listNonAffectedCardEntityReverse(int i, int j, String sortProperty,
			boolean sortOrder, ImogJunctionProxy criterions, String property);

	Request<List<CardEntityProxy>> getCardEntityEmptyList();

	Request<Long> countCardEntity();

	Request<Long> countCardEntity(ImogJunctionProxy criterions);

	Request<Long> countNonAffectedCardEntity(String property);

	Request<Long> countNonAffectedCardEntity(String property, ImogJunctionProxy criterions);

	Request<Long> countNonAffectedCardEntityReverse(String property);

	Request<Long> countNonAffectedCardEntityReverse(String property, ImogJunctionProxy criterions);

	Request<Void> delete(Set<CardEntityProxy> entities);

	Request<Void> delete(CardEntityProxy entity);

	Request<Void> save(ImogBeanProxy entity, boolean isNew);

	Request<Void> delete(ImogBeanProxy entity);
}
