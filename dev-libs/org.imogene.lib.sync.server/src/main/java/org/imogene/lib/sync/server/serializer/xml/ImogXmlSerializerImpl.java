package org.imogene.lib.sync.server.serializer.xml;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.sync.SyncConstants;
import org.imogene.lib.sync.handler.ImogBeanHandler;
import org.imogene.lib.sync.serializer.xml.ImogXmlSerializer;

public class ImogXmlSerializerImpl extends ImogXmlSerializer {

	@Override
	@SuppressWarnings("unchecked")
	protected <T extends ImogBean> boolean save(T entity, ImogActor user) {
		ImogBeanHandler<T> handler = (ImogBeanHandler<T>) dataHandlerManager.getHandler(entity.getClass());
		ImogBean exist = handler.loadEntity(entity.getId());
		// TODO implement synchronization policy instead of
		// exist.getModified().before(entity.getModified())
		if (exist == null || SyncConstants.SYNC_ID_SYS.equals(exist.getModifiedFrom())
				|| exist.getModified().before(entity.getModified())) {
			handler.saveOrUpdate(entity, user,
					exist == null || SyncConstants.SYNC_ID_SYS.equals(exist.getModifiedFrom()));
			return true;
		}
		return false;
	}

}
