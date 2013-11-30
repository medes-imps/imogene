package org.imogene.admin.shared.proxy;

import java.util.List;

import org.imogene.admin.server.locator.NotificationLocator;
import org.imogene.lib.common.notification.Notification;
import org.imogene.web.shared.proxy.ImogActorProxy;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * Notification proxy
 * 
 * @author Medes-IMPS
 */
@ProxyFor(value = Notification.class, locator = NotificationLocator.class)
public interface NotificationProxy extends ImogBeanProxy {

	/* Description section fields */

	public String getName();

	public void setName(String value);

	public String getMethod();

	public void setMethod(String value);

	public String getTitle();

	public void setTitle(String value);

	public String getFormType();

	public void setFormType(String value);

	public String getOperation();

	public void setOperation(String value);

	public String getMessage();

	public void setMessage(String value);

	/* Recipients section fields */

	public List<ImogActorProxy> getActorRecipients();

	public void setActorRecipients(List<ImogActorProxy> value);

}
