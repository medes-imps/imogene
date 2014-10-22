package org.imogene.lib.sync.server;

import java.util.Date;
import java.util.List;

import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.entity.ImogBean;

/**
 * Helper containing method used to retrieve specific entities to synchronize.
 * 
 * @author MEDES-IMPS
 */
public interface EntityHelper {

	/**
	 * Get the entity ids associated to the specified entity. Works only for the association 1<->N and 1<->*
	 * 
	 * @param entity the parent entity
	 * @return list of associated entities
	 */
	public List<ImogBean> getAssociatedEntitiesIds(ImogBean entity);

	/**
	 * Get the binaries associated with the given set of entities
	 * 
	 * @param entities the entities
	 * @return the list of associated binaries
	 */
	public List<Binary> getAssociatedBinaries(List<ImogBean> entities);

	/**
	 * Get the binaries associated with the given set of entities uploaded after the last synchronization date
	 * 
	 * @param entities the entities
	 * @param lastDate last synchronization date
	 * @return the list of associated binaries
	 */
	public List<Binary> getAssociatedBinariesUploaded(List<ImogBean> entities, Date lastDate);

}
