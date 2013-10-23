package org.imogene.lib.common.role;

import java.util.List;

import org.imogene.lib.common.dao.ImogBeanDao;
import org.imogene.lib.common.entity.ImogActor;

/**
 * Manage persistence for ImogRole
 * @author MEDES-IMPS
 */
public interface RoleActorDao extends ImogBeanDao<ImogRole> {

	public List<? extends ImogActor> listActors();
	
	public List<? extends ImogActor> listActorsForRole(ImogRole role);

	public ImogActor getActor(String id);

	public List<ImogRole> listRoles();

	public ImogRole getRole(String id);
}
