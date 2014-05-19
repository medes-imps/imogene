package org.imogene.lib.sync.client.util;

import java.util.UUID;

import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.util.SystemUtil;
import org.imogene.lib.sync.client.params.SyncParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ClientSystemUtil implements SystemUtil {

	@Autowired
	private GenericDao genericDao;

	@Override
	@Transactional(readOnly = true)
	public long getCurrentTimeMillis() {
		SyncParams params = genericDao.load(SyncParams.class, SyncParams.ID);
		if (params == null || params.getOffset() == null) {
			return System.currentTimeMillis();
		}
		return System.currentTimeMillis() + params.getOffset();
	}

	@Override
	@Transactional
	public String getTerminal() {
		SyncParams params = genericDao.load(SyncParams.class, SyncParams.ID);
		if (params == null) {
			params = new SyncParams();
		}
		if (params.getTerminal() == null) {
			params.setTerminal(UUID.randomUUID().toString());
			genericDao.merge(params);
		}
		return params.getTerminal();
	}
}
