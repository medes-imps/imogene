package org.imogene.web.shared.proxy;

import java.util.List;

import org.imogene.lib.common.profile.Profile;
import org.imogene.web.server.locator.ProfileLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * Profile proxy 
 * @author Medes-IMPS
 */
@ProxyFor(value = Profile.class, locator = ProfileLocator.class)
public interface ProfileProxy extends ImogBeanProxy {

	/* Description section fields */

	public String getName();
	public void setName(String value);

	public List<EntityProfileProxy> getEntityProfiles();
	public void setEntityProfiles(List<EntityProfileProxy> value);

	public List<FieldGroupProfileProxy> getFieldGroupProfiles();
	public void setFieldGroupProfiles(List<FieldGroupProfileProxy> value);

}
