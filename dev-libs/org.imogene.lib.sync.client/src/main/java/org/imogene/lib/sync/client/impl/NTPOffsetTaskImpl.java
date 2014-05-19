package org.imogene.lib.sync.client.impl;

import java.net.InetAddress;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.sync.client.NTPOffsetTask;
import org.imogene.lib.sync.client.params.SyncParams;
import org.springframework.transaction.annotation.Transactional;

public class NTPOffsetTaskImpl implements NTPOffsetTask {

	private GenericDao genericDao;

	private String host;

	@Override
	@Transactional
	public void update() {
		NTPUDPClient client = new NTPUDPClient();
		try {
			client.setDefaultTimeout(60000);
			TimeInfo time = client.getTime(InetAddress.getByName(host));
			time.computeDetails();
			SyncParams params = genericDao.load(SyncParams.class, SyncParams.ID);
			if (params == null) {
				params = new SyncParams();
			}
			params.setOffset(time.getOffset());
			genericDao.merge(params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param genericDao GenericDao
	 */
	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	/**
	 * Set the NTP host address.
	 * 
	 * @param host The NTP haso address
	 */
	public void setHost(String host) {
		this.host = host;
	}

}
