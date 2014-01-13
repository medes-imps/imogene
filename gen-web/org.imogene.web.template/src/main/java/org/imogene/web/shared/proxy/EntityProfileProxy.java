package org.imogene.web.shared.proxy;

import org.imogene.lib.common.profile.EntityProfile;
import org.imogene.web.server.locator.EntityProfileLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * EntityProfile proxy
 * 
 * @author Medes-IMPS
 */
@ProxyFor(value = EntityProfile.class, locator = EntityProfileLocator.class)
public interface EntityProfileProxy extends ImogBeanProxy {

	/* Description section fields */

	public ProfileProxy getProfile();

	public void setProfile(ProfileProxy value);

	public CardEntityProxy getEntity();

	public void setEntity(CardEntityProxy value);

	public Boolean getCreate();

	public void setCreate(Boolean value);

	public Boolean getDirectAccess();

	public void setDirectAccess(Boolean value);

	public Boolean getDelete();

	public void setDelete(Boolean value);

	public Boolean getExport();

	public void setExport(Boolean value);

}
