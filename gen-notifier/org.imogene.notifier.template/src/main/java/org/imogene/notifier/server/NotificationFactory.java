package org.imogene.notifier.server;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.entity.ImogBeanImpl;
import org.imogene.lib.common.notification.Notification;
import org.imogene.lib.common.notification.NotificationDao;
import org.imogene.lib.common.role.ImogRole;
import org.imogene.lib.common.role.RoleActorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * factory for notification instances
 * 
 * @author Medes-IMPS
 */
public class NotificationFactory {

	private Logger logger = Logger.getLogger("org.imogene.notifier.server");

	@Autowired
	@Qualifier(value = "roleActorDao")
	private RoleActorDao roleActorDao;

	@Autowired
	@Qualifier(value = "notificationDao")
	private NotificationDao notificationDao;

	@Autowired
	@Qualifier(value = "genericDao")
	private GenericDao genericDao;

	/**
	 * Create notification for the given parameters if it exists.
	 * 
	 * @param type the entity type
	 * @param id the entity id
	 * @param op the entity operation
	 * @return All notification instances
	 */
	public Set<NotificationInstance> createNotification(String type, String id, String op) {
		Set<NotificationInstance> instances = new HashSet<NotificationInstance>();
		logger.debug(instances.size() + " instance found.");
		List<Notification> notifications = notificationDao.load(type, op);
		if (notifications != null) {
			for (Notification notification : notifications) {
				instances.add(createInstance(notification, id));
			}
		}
		return instances;
	}

	/**
	 * Create a notification instance for the specified entity, based on the
	 * specified notification template
	 * 
	 * @param template the notification template
	 * @param id the id of the entity
	 * @return the notification instance
	 */
	private NotificationInstance createInstance(Notification template, String id) {
		NotificationInstance instance = new NotificationInstance();
		instance.setId(template.getId());
		instance.setName(template.getName());
		instance.setTile(template.getTitle());
		instance.setMessage(createMessage(template, id));
		instance.setMethod(template.getMethod());
		instance.setUserRecipients(createRecipients(template, id));
		logger.debug("Instance created form the template " + template.getName());
		return instance;
	}

	/**
	 * create the messages based on the message template.
	 * 
	 * @param template the notification template
	 * @param id the entity id
	 * @return the prepared message
	 */
	private String createMessage(Notification template, String id) {
		String message = template.getMessage();
		ImogBean bean = genericDao.load(ImogBeanImpl.class, id);
		if (bean != null) {
			return message.replace("${creator}", bean.getCreatedBy());
		}
		return message;
	}

	/**
	 * Create user recipient
	 * 
	 * @param template the notification template
	 * @param id the entity id
	 * @return a set of all recipients
	 */
	private Set<ImogActor> createRecipients(Notification template, String id) {
		Set<ImogActor> actors = new HashSet<ImogActor>();
		/* add single user */
		List<? extends ImogActor> recipients = template.getActorRecipients();
		if (template.getActorRecipients() != null) {
			actors.addAll(recipients);
		}
		/* add role users */
		List<ImogRole> roles = template.getRoleRecipients();
		if (roles != null) {
			for (ImogRole role : roles) {
				recipients = roleActorDao.listActorsForRole(role);
				if (recipients != null) {
					actors.addAll(recipients);
				}
			}
		}
		// TODO add user implied by the entity (ie: creator, user field ... ).
		logger.debug("Numbers of recipient added : " + actors.size());
		return actors;
	}

}
