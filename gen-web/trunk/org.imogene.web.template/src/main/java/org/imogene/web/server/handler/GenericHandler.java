package org.imogene.web.server.handler;

import java.util.List;

import org.imogene.lib.common.dao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data handler for the Generic dao 
 * @author Medes-IMPS
 */
public class GenericHandler {

	private GenericDao dao;


	
	/**
	 * Loads an entity instance
	 * @param className the entity class name
	 * @param id the instance id
	 * @return the entity instance if it exists
	 */
	@Transactional(readOnly = true)
	public <T> T find(Class<T> clazz, String id) {
		return dao.load(clazz, id);
	}
	
	/**
	 * Loads an entity instance
	 * @param className the entity class name
	 * @param id the instance id
	 * @return the entity instance if it exists
	 */
	@Transactional(readOnly = true)
	public <T> List<T> find(Class<T> clazz) {
		return dao.load(clazz);
	}

	/**
	 * Saves or updates the entity
	 * @param entity the entity to be saved or updated
	 */
	@Transactional
	public <T> void save(T object) {
		dao.saveOrUpdate(object);
	}


	/**
	 * 	Setter for bean injection
	 * @param dao
	 */
	public void setDao(GenericDao dao) {
		this.dao = dao;
	}
	
	




}
