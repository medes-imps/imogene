package org.imogene.admin.shared.request;

import java.util.List;
import java.util.Set;

import org.imogene.web.server.handler.ProfileHandler;
import org.imogene.web.server.locator.SpringServiceLocator;
import org.imogene.web.shared.proxy.ImogBeanProxy;
import org.imogene.web.shared.proxy.ProfileProxy;
import org.imogene.web.shared.proxy.criteria.BasicCriteriaProxy;
import org.imogene.web.shared.proxy.criteria.ImogJunctionProxy;
import org.imogene.web.shared.request.ImogEntityRequest;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Source of request objects for the Profile Service.
 * 
 * @author Medes-IMPS
 */
@Service(value = ProfileHandler.class, locator = SpringServiceLocator.class)
public interface ProfileRequest extends ImogEntityRequest {

	Request<ProfileProxy> findById(String id);

	Request<Void> save(ProfileProxy c, boolean isNew);

	Request<List<ProfileProxy>> listProfile(String sortProperty, boolean sortOrder);

	Request<List<ProfileProxy>> listProfile(int first, int max, String sortProperty, boolean sortOrder);

	Request<List<ProfileProxy>> listProfile(int first, int max, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions);

	Request<List<ProfileProxy>> listProfile(int first, int max, String sortProperty, boolean sortOrder,
			List<BasicCriteriaProxy> criterions);

	Request<List<ProfileProxy>> listNonAffectedProfile(int i, int j, String sortProperty, boolean sortOrder,
			String property);

	Request<List<ProfileProxy>> listNonAffectedProfile(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);

	Request<List<ProfileProxy>> listNonAffectedProfileReverse(int i, int j, String sortProperty, boolean sortOrder,
			String property);

	Request<List<ProfileProxy>> listNonAffectedProfileReverse(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunctionProxy criterions, String property);

	Request<List<ProfileProxy>> getProfileEmptyList();

	Request<Long> countProfile();

	Request<Long> countProfile(ImogJunctionProxy criterions);

	Request<Long> countNonAffectedProfile(String property);

	Request<Long> countNonAffectedProfile(String property, ImogJunctionProxy criterions);

	Request<Long> countNonAffectedProfileReverse(String property);

	Request<Long> countNonAffectedProfileReverse(String property, ImogJunctionProxy criterions);

	Request<Void> delete(Set<ProfileProxy> entities);

	Request<Void> delete(ProfileProxy entity);

	Request<Void> save(ImogBeanProxy entity, boolean isNew);

	Request<Void> delete(ImogBeanProxy entity);

	Request<ProfileProxy> createProfile();
}
