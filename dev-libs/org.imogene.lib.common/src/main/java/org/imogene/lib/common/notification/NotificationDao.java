package org.imogene.lib.common.notification;

import java.util.List;

import org.imogene.lib.common.dao.ImogBeanDao;

/**
 * Manage persistence for Notification
 * @author MEDES-IMPS
 */
public interface NotificationDao extends ImogBeanDao<Notification> {

	public List<Notification> load(String formType, String operation);

	public void saveOrUpdate(Notification notification);
}
