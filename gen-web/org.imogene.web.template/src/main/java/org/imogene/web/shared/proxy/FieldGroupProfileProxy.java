package org.imogene.web.shared.proxy;

import org.imogene.lib.common.profile.FieldGroupProfile;
import org.imogene.web.server.locator.FieldGroupProfileLocator;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * FieldGroupProfile proxy
 * 
 * @author Medes-IMPS
 */
@ProxyFor(value = FieldGroupProfile.class, locator = FieldGroupProfileLocator.class)
public interface FieldGroupProfileProxy extends ImogBeanProxy {

	/* Description section fields */

	public ProfileProxy getProfile();

	public void setProfile(ProfileProxy value);

	public CardEntityProxy getCardEntity();

	public void setCardEntity(CardEntityProxy value);

	public FieldGroupProxy getFieldGroup();

	public void setFieldGroup(FieldGroupProxy value);

	public Boolean getRead();

	public void setRead(Boolean value);

	public Boolean getWrite();

	public void setWrite(Boolean value);

	public Boolean getExport();

	public void setExport(Boolean value);

}
