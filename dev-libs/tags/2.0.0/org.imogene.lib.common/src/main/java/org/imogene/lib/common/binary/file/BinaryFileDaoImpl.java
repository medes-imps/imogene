package org.imogene.lib.common.binary.file;

import org.imogene.lib.common.dao.ImogBeanDaoImpl;

public class BinaryFileDaoImpl extends ImogBeanDaoImpl<BinaryFile> implements BinaryFileDao {

	protected BinaryFileDaoImpl() {
		super(BinaryFile.class);
	}

	@Override
	public void delete() {
		em.createQuery("DELETE FROM Binary").executeUpdate();
	}

}
