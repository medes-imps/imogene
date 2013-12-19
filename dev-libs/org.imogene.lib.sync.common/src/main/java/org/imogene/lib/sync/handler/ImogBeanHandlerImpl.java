package org.imogene.lib.sync.handler;

import java.util.Date;
import java.util.List;

import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.dao.ImogBeanDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.security.ImogBeanFilter;

/**
 * Abstract class for EntityHandler implementation
 * 
 * @author MEDES-IMPS
 */
public abstract class ImogBeanHandlerImpl<T extends ImogBean> implements ImogBeanHandler<T> {

	protected boolean hasNewClientFilter = false;

	private ImogBeanFilter filter;

	/**
	 * Setter for bean injection
	 * 
	 * @param imogBeanFilter
	 */
	public void setFilter(ImogBeanFilter filter) {
		this.filter = filter;
	}

	@Override
	public T loadEntity(String entityId) {
		return getDao().load(entityId);
	}

	@Override
	public T loadEntity(String entityId, ImogActor user) {
		if (user != null) {
			return filter.<T> toSecure(getDao().load(entityId));
		} else {
			return getDao().load(entityId);
		}
	}

	@Override
	public List<T> loadEntities(ImogActor user, String terminalId) {
		if (user != null) {
			ImogConjunction conj = new ImogConjunction();
			conj.add(createFilterJuntion(user));
			ImogJunction clientFilterJunction = createClientFilterJuntion(user.getLogin(), terminalId);
			if (clientFilterJunction != null) {
				conj.add(clientFilterJunction);
				if (hasNewClientFilter) {
					hasNewClientFilter = false;
				}
			}
			return filter.<T> toSecure(getDao().load(conj));
		} else {
			return getDao().load();
		}
	}

	@Override
	public List<T> loadModified(Date date, ImogActor user) {
		if (user != null) {
			ImogConjunction conj = new ImogConjunction();
			conj.add(createFilterJuntion(user));
			List<T> entities = getDao().loadModified(date, conj);
			List<T> securedEntities = filter.<T> toSecure(entities);
			return securedEntities;
		} else {
			return getDao().loadModified(date);
		}
	}

	@Override
	public T loadModified(String entityId, Date date, ImogActor user) {
		if (user != null) {
			ImogConjunction conj = new ImogConjunction();
			conj.add(createFilterJuntion(user));
			T entity = getDao().loadModified(date, conj, entityId);
			T securedEntity = filter.<T> toSecure(entity);
			return securedEntity;
		} else {
			return getDao().loadModified(date, entityId);
		}
	}

	@Override
	public List<T> loadUploaded(Date date, ImogActor user, String terminalId) {
		if (user != null) {
			ImogConjunction conj = new ImogConjunction();
			conj.add(createFilterJuntion(user));
			ImogJunction clientFilterJunction = createClientFilterJuntion(user.getLogin(), terminalId);

			if (clientFilterJunction != null) {
				conj.add(clientFilterJunction);
				/*
				 * if new client filter, send all cardentities, not only last modified ones
				 */
				if (hasNewClientFilter) {
					hasNewClientFilter = false;
					return loadEntities(user, conj);
				}
			}

			List<T> entities = getDao().loadUploaded(date, conj);
			List<T> securedEntities = filter.<T> toSecure(entities);
			return securedEntities;
		} else {
			return getDao().loadUploaded(date);
		}
	}

	@Override
	public T loadUploaded(String entityId, Date date, ImogActor user) {
		if (user != null) {
			ImogConjunction conj = new ImogConjunction();
			conj.add(createFilterJuntion(user));
			T entity = getDao().loadUploaded(date, conj, entityId);
			T securedEntity = filter.<T> toSecure(entity);
			return securedEntity;
		} else {
			return getDao().loadUploaded(date, entityId);
		}
	}

	protected void saveOrUpdate(T entity, boolean neu) {
		getDao().saveOrUpdate(entity, neu);
	};

	@Override
	public void saveOrUpdate(T entity, ImogActor user, boolean neu) {
		if (user != null) {
			T toSave = filter.toHibernate(entity);
			if (toSave != null) {
				saveOrUpdate(toSave, neu);
			}
		} else {
			saveOrUpdate(entity, neu);
		}
	}

	@Override
	public T merge(T entity, boolean neu) {
		return getDao().merge(entity, neu);
	};

	/**
	 * 
	 * @param user current user whose access has to be filtered (if null, no filtering)
	 * @param conj search criterions
	 * @return list of entities
	 */
	private List<T> loadEntities(ImogActor user, ImogConjunction conj) {
		if (user != null) {
			return filter.<T> toSecure(getDao().load(conj));
		} else {
			return getDao().load(conj);
		}
	}

	/**
	 * Get the DAO used to access data To be used only if no data access control is needed
	 * 
	 * @return the DAO used to access data
	 */
	protected abstract ImogBeanDao<T> getDao();

	/**
	 * Creates filtering criterias
	 * 
	 * @param actor the current user
	 * @return Meedoo junction containing filtering criterias if the type of user has been assigned filtering criteria
	 */
	protected abstract ImogJunction createFilterJuntion(ImogActor actor);

	/**
	 * Gets filtering criterias
	 * 
	 * @param userId the login of the user whose filters are searched
	 * @param terminalId the id of the terminal for which filtering criterias are defined
	 * @return a list of ClientFilters
	 */
	protected abstract ImogJunction createClientFilterJuntion(String userId, String terminalId);

}
