package org.imogene.admin.server.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.imogene.lib.common.constants.CommonConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.notification.Notification;
import org.imogene.lib.common.notification.NotificationDao;
import org.imogene.web.server.util.HttpSessionUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the Notification beans
 * 
 * @author Medes-IMPS
 */
public class NotificationHandler {

	private NotificationDao dao;

	/**
	 * Loads the entity (secured) with the specified id
	 * 
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public Notification findById(String entityId) {
		return dao.load(entityId);
	}

	/**
	 * Saves or updates the entity
	 * 
	 * @param entity the entity to be saved or updated
	 * @param isNew true if it is a new entity added for the first time.
	 */
	@Transactional
	public void save(Notification entity, boolean isNew) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();

		if (entity != null) {

			if (isNew) {
				entity.setCreated(new Date(System.currentTimeMillis()));
				entity.setCreatedBy(actor.getLogin());
			}

			entity.setModified(new Date(System.currentTimeMillis()));
			entity.setModifiedBy(actor.getLogin());
			entity.setModifiedFrom(CommonConstants.IS_WEB);

			dao.saveOrUpdate(entity, isNew);
		}
	}

	/**
	 * Lists the entities of type Notification
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of notification
	 */
	@Transactional(readOnly = true)
	public List<Notification> listNotification(int i, int j, String sortProperty, boolean sortOrder) {
		return dao.load(i, j, sortProperty, sortOrder);
	}

	/**
	 * Lists the entities of type Notification
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of notification
	 */
	@Transactional(readOnly = true)
	public List<Notification> listNotification(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions) {
		return dao.load(i, j, sortProperty, sortOrder, criterions);
	}

	/**
	 * Lists the entities of type Notification
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria
	 * @return list of notification
	 */
	@Transactional(readOnly = true)
	public List<Notification> listNotification(int i, int j, String sortProperty, boolean sortOrder,
			List<BasicCriteria> criterions) {
		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}

		return dao.load(i, j, sortProperty, sortOrder, junctionForCrit);
	}

	/**
	 * Lists the non affected entities of type Notification
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterion request criteria
	 * @param property the property which is not affected
	 * @return list of notification
	 */
	@Transactional(readOnly = true)
	public List<Notification> listNonAffectedNotification(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {
		return dao.loadNonAffected(i, j, sortProperty, sortOrder, property, criterions);
	}

	/**
	 * Lists the non affected entities of type Notification
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of notification
	 */
	@Transactional(readOnly = true)
	public List<Notification> listNonAffectedNotification(int i, int j, String sortProperty, boolean sortOrder,
			String property) {
		return listNonAffectedNotification(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Used when Notification is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of the
	 * Relation Return all instance of Notification non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param ImogJunction request criteria
	 * @param property the property which is not affected
	 * @return list of notification
	 */
	@Transactional(readOnly = true)
	public List<Notification> listNonAffectedNotificationReverse(int i, int j, String sortProperty, boolean sortOrder,
			ImogJunction criterions, String property) {
		return dao.loadNonAffectedReverse(i, j, sortProperty, sortOrder, property, criterions);
	}

	/**
	 * Used when Notification is involved in a Relation 1 <-> 1 Association and is the ReverseRelationField of the
	 * Relation Return all instance of Notification non affected regarding specified property.
	 * 
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param property the property which is not affected
	 * @return list of notification
	 */
	@Transactional(readOnly = true)
	public List<Notification> listNonAffectedNotificationReverse(int i, int j, String sortProperty, boolean sortOrder,
			String property) {
		return listNonAffectedNotificationReverse(i, j, sortProperty, sortOrder, null, property);
	}

	/**
	 * Gets an empty list of Notification
	 * 
	 * @return an empty list of Notification
	 */
	@Transactional(readOnly = true)
	public List<Notification> getNotificationEmptyList() {
		return new ArrayList<Notification>();
	}

	/**
	 * Counts the number of notification in the database
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNotification() {
		return countNotification(null);
	}

	/**
	 * Counts the number of notification in the database, that match the criteria
	 * 
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNotification(ImogJunction criterions) {
		return dao.count(criterions);
	}

	/**
	 * Counts the number of non affected notification entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedNotification(String property, ImogJunction criterions) {
		return dao.countNonAffected(property, criterions);
	}

	/**
	 * Counts the number of non affected notification entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedNotification(String property) {
		return countNonAffectedNotification(property, null);
	}

	/**
	 * Counts the number of non affected notification entities in the database
	 * 
	 * @param property the property which is not affected
	 * @param criterion request criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedNotificationReverse(String property, ImogJunction criterions) {
		return dao.countNonAffectedReverse(property, criterions);
	}

	/**
	 * Counts the number of non affected notification entities in the database
	 * 
	 * @param property the property which is not affected
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countNonAffectedNotificationReverse(String property) {
		return countNonAffectedNotificationReverse(property, null);
	}

	/**
	 * Deletes a group of entities identified by their IDs
	 * 
	 * @param ids Entities to delete IDs
	 * @return The list of deleted entities IDs
	 */
	@Transactional
	public void delete(Set<Notification> entities) {
		if (entities != null) {
			for (Notification entity : entities)
				delete(entity);
		}
	}

	/**
	 * Removes the specified entity from the database
	 * 
	 * @param entity The entity to be deleted
	 */
	@Transactional
	public void delete(Notification entity) {
		dao.delete(entity);
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param dao the Notification Dao
	 */
	public void setDao(NotificationDao dao) {
		this.dao = dao;
	}
}
