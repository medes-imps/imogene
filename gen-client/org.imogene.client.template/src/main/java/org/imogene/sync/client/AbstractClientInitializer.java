package org.imogene.sync.client;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.role.ImogRole;
import org.imogene.lib.common.role.RoleActorDao;
import org.imogene.lib.common.sync.entity.SynchronizableEntity;
import org.imogene.lib.common.sync.entity.SynchronizableEntityDao;
import org.imogene.lib.common.user.DefaultUser;
import org.imogene.lib.common.user.DefaultUserDao;
import org.imogene.lib.sync.client.parameter.SyncParameter;
import org.imogene.lib.sync.client.parameter.SyncParameterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractClientInitializer implements ClientInitializer {
	
	@Autowired
	@Qualifier(value = "syncEntityDao")
	private SynchronizableEntityDao synchronizableEntityDao;
	
	@Autowired
	@Qualifier(value = "syncParameterDao")
	private SyncParameterDao syncParameterDao;

	@Autowired
	@Qualifier(value = "roleActorDao")
	private RoleActorDao roleActorDao;

	@Autowired
	@Qualifier(value = "defaultUserDao")
	private DefaultUserDao defaultUserDao;

	@Autowired
	@Qualifier(value = "genericDao")
	private GenericDao genericDao;
	
	@Transactional
	protected void initDatase(String[] roles, Map<String, Class<? extends ImogBean>> entities) {
		for (String role : roles) {
			ImogRole r = new ImogRole();
			r.setId(role);
			r.setName(role);
			genericDao.saveOrUpdate(r);
		}
		for (String key : entities.keySet()) {
			addEntity(key, entities.get(key));
		}
	}
	
	
	
	@Transactional
	public void initDefaultUser() {
		DefaultUser user = defaultUserDao.load("admin");
		if (user == null) {
			user = new DefaultUser();
			user.setId("admin");
			user.setLogin("admin");
			user.setPassword("epipassword");
			
			user.setRoles(new HashSet<ImogRole>());
			for (ImogRole role : roleActorDao.listRoles()) {
				user.addRole(role);
			}
			
			user.setSynchronizables(new HashSet<SynchronizableEntity>());
			for (SynchronizableEntity entity : synchronizableEntityDao.load()) {
				user.addSynchronizable(entity);
			}
			defaultUserDao.saveOrUpdate(user, true);
		}
	}

	/**
	 * create the sync parameters
	 */
	@Transactional
	public void initSyncParameters() {
		/* create sync parameters */
		SyncParameter parameter = syncParameterDao.load();
		if (parameter == null) {
			/* create synchronizable entities (all by default / to be configured) */
			parameter = new SyncParameter();
			parameter.setLogin("admin");
			parameter.setPassword("secret");
			parameter.setPeriod(10000);
			parameter.setLoop(true);
			parameter.setUrl("http://localhost:7080/AvionSync");
			parameter.setType("xml");
			parameter.setTerminalId(UUID.randomUUID().toString());
			syncParameterDao.store(parameter);
		} else {		
			parameter.setTerminalId(UUID.randomUUID().toString());
			syncParameterDao.store(parameter);
		}
	}
	
	@Transactional
	public SyncParameter getCurrentParameters(){
		return syncParameterDao.load();
	}
	
	@Transactional
	public void updateSyncUrl(String url){
		SyncParameter param = getCurrentParameters();
		param.setUrl(url);
		updateParameters(param);
	}
	
	@Transactional
	public void updateSyncLoop(boolean loop){
		SyncParameter param = getCurrentParameters();
		param.setLoop(loop);
		updateParameters(param);
	}
	
	@Transactional
	public void updateSyncPeriod(int period){
		SyncParameter param = getCurrentParameters();
		param.setPeriod(period);
		updateParameters(param);
	}
	
	@Transactional
	public void updateSyncParameters(String url, boolean loop, int period){
		SyncParameter param = getCurrentParameters();
		param.setUrl(url);
		param.setLoop(loop);
		param.setPeriod(period);
		updateParameters(param);
	}

	private void updateParameters(SyncParameter param){
		syncParameterDao.store(param);
	}

	protected void addRole(String role) {
		ImogRole r = new ImogRole();
		r.setId(role);
		r.setName(role);
		genericDao.saveOrUpdate(r);
	}
	
	/**
	 * Add an entity to entity that could be synchronizable
	 * @param classToAdd the entity class
	 * @return the synchronizable entity description
	 */
	protected SynchronizableEntity addEntity(String shortName, Class<? extends ImogBean> clazz){
		SynchronizableEntity entity = new SynchronizableEntity();
		entity.setId(shortName);
		entity.setName(clazz.getName());
		synchronizableEntityDao.store(entity);
		return entity;
	}
}
