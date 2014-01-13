package org.imogene.lib.sync.binary.file;

import java.util.Date;

import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.lib.common.binary.file.BinaryFileDao;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.sync.SyncConstants;
import org.imogene.lib.sync.handler.ImogBeanHandlerImpl;

/**
 * Implements a data handler for the Binary
 * @author MEDES-IMPS
 */
public class BinaryFileHandlerImpl extends ImogBeanHandlerImpl<BinaryFile> implements BinaryFileHandler {

	private BinaryFileDao binaryFileDao;

	/**
	 * Setter for bean injection
	 * @param binaryFileDao
	 */
	public void setBinaryFileDao(BinaryFileDao binaryFileDao) {
		this.binaryFileDao = binaryFileDao;
	}

	@Override
	public BinaryFile createNewEntity(String id) {
		BinaryFile entity = new BinaryFile();
		entity.setId(id);
		entity.setModified(new Date());
		entity.setModifiedBy(SyncConstants.SYNC_ID_SYS);
		entity.setModifiedFrom(SyncConstants.SYNC_ID_SYS);
		return entity;
	}

	@Override
	protected BinaryFileDao getDao() {
		return binaryFileDao;
	}

	@Override
	protected ImogJunction createFilterJuntion(ImogActor actor) {
		return null;
	}

	@Override
	protected ImogJunction createClientFilterJuntion(String userId, String terminalId) {
		return null;
	}
	
	@Override
	public void saveOrUpdate(BinaryFile entity, ImogActor user, boolean neu) {
		saveOrUpdate(entity, neu);
	}

}