package org.imogene.web.server.handler;

import java.util.ArrayList;
import java.util.List;

import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.dao.ImogActorImplDao;
import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.lib.common.user.DefaultUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the ImogActor beans 
 * @author Medes-IMPS
 */
public class ImogActorHandler {

	private ImogActorImplDao dao;

	/**
	 * Loads the entity (secured) with the specified id
	 * @param entityId the entity id
	 * @return the entity or null
	 */
	@Transactional(readOnly = true)
	public ImogActorImpl findById(String entityId) {
		ImogActorImpl bean = dao.load(entityId);
		return bean;
	}


	/**
	 * Lists the entities of type ImogActor
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @return list of ImogActor
	 */
	@Transactional(readOnly = true)
	public List<ImogActorImpl> listAppliUser(int i, int j, String sortProperty,	boolean sortOrder) {
		
		List<ImogActorImpl> beans = dao.load(i, j, sortProperty, sortOrder);		
		List<ImogActorImpl> result = new ArrayList<ImogActorImpl>();
		
		if(beans!=null) {
			for(ImogActorImpl actor:beans) {
				if(!(actor instanceof DefaultUser))
					result.add(actor);
			}
		}		
		return result;
	}

	/**
	 * Lists the entities of type ImogActor
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria	 
	 * @return list of ImogActor
	 */
	@Transactional(readOnly = true)
	public List<ImogActorImpl> listAppliUser(int i, int j, String sortProperty, boolean sortOrder, ImogJunction criterions) {
		
		List<ImogActorImpl> beans = dao.load(i, j, sortProperty, sortOrder, criterions);	
		List<ImogActorImpl> result = new ArrayList<ImogActorImpl>();
		
		if(beans!=null) {
			for(ImogActorImpl actor:beans) {
				if(!(actor instanceof DefaultUser))
					result.add(actor);
			}
		}		
		return result;
	}

	/**
	 * Lists the entities of type ImogActor
	 * @param i first index to retrieve
	 * @param j nb of items to retrieve
	 * @param sortProperty the property used to sort the collection
	 * @param sortOrder true for an ascendant sort
	 * @param criterions request criteria	 
	 * @return list of ImogActor
	 */
	@Transactional(readOnly = true)
	public List<ImogActorImpl> listAppliUser(int i, int j, String sortProperty,
			boolean sortOrder, List<BasicCriteria> criterions) {

		ImogJunction junctionForCrit = new ImogConjunction();
		if (criterions != null) {
			for (BasicCriteria crit : criterions)
				junctionForCrit.add(crit);
		}

		List<ImogActorImpl> beans = dao.load(i, j, sortProperty, sortOrder, junctionForCrit);		
		List<ImogActorImpl> result = new ArrayList<ImogActorImpl>();
		
		if(beans!=null) {
			for(ImogActorImpl actor:beans) {
				if(!(actor instanceof DefaultUser))
					result.add(actor);
			}
		}		
		return result;
	}


	/**
	 * Gets an empty list of ImogActor	
	 * @return an empty list of ImogActor
	 */
	@Transactional(readOnly = true)
	public List<ImogActorImpl> getAppliUserEmptyList() {
		return new ArrayList<ImogActorImpl>();
	}

	/**
	 * Counts the number of ImogActor in the database
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countAppliUser() {
		return countAppliUser(null);
	}

	/**
	 * Counts the number of ImogActor in the database, 
	 * that match the criteria
	 * @return the count
	 */
	@Transactional(readOnly = true)
	public Long countAppliUser(ImogJunction criterions) {
		return dao.count(criterions);
	}


	/**
	 * Lists the entities of type ImogActor that have a given login
	 * @param login the login of the ImogActor
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ImogActorImpl> loadFromLogin(String login) {
		return dao.loadFromLogin(login);
	}

	/**
	 * Setter for bean injection
	 * @param dao the ImogActor Dao
	 */
	public void setDao(ImogActorImplDao dao) {
		this.dao = dao;
	}

}
