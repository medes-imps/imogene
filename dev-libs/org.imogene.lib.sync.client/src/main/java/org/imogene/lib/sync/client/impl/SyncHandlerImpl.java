package org.imogene.lib.sync.client.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.sync.client.SyncHandler;
import org.imogene.lib.sync.client.SynchronizationException;
import org.imogene.lib.sync.client.params.SyncParams;
import org.imogene.lib.sync.handler.DataHandlerManager;
import org.imogene.lib.sync.handler.ImogBeanHandler;
import org.imogene.lib.sync.history.SyncHistory;
import org.imogene.lib.sync.history.SyncHistoryDao;
import org.imogene.lib.sync.serializer.ImogSerializationException;
import org.imogene.lib.sync.serializer.ImogSerializer;
import org.springframework.transaction.annotation.Transactional;

public class SyncHandlerImpl implements SyncHandler {

	private static final Logger logger = Logger.getLogger(SyncHandlerImpl.class.getName());

	private DataHandlerManager dataManager;
	private ImogSerializer serializer;
	private SyncHistoryDao historyDao;
	private GenericDao genericDao;

	/**
	 * Setter for bean injection.
	 * 
	 * @param manager The {@link DataHandlerManager} implementation of the application.
	 */
	public void setDataManager(DataHandlerManager manager) {
		this.dataManager = manager;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param serializer The {@link ImogSerializer} implementation of the application.
	 */
	public void setSerializer(ImogSerializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param dao The {@link SyncHistoryDao} implementation of the application.
	 */
	public void setSyncHistoryDao(SyncHistoryDao dao) {
		this.historyDao = dao;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param dao The {@link GenericDao} implementation of the application.
	 */
	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	@Transactional(readOnly = true)
	public SyncHistory loadLastError() {
		return historyDao.loadLastError();
	}

	@Override
	@Transactional(readOnly = true)
	public SyncParams loadParams() {
		return genericDao.load(SyncParams.class, SyncParams.ID);
	}

	@Override
	@Transactional
	public void updateHistory(SyncHistory history) {
		historyDao.saveOrUpdate(history);
	}

	@Override
	@Transactional(readOnly = true)
	public int getDataToSynchronize(OutputStream os, String sessionId, String login) throws SynchronizationException {
		Date lastSynchro = computeLastDate();
		ImogActor actor = null;
		try {
			actor = genericDao.loadFromLogin(login);
		} catch (NoResultException e) {
			try {
				serializer.serialize(new ArrayList<ImogBean>(), os);
				os.close();
			} catch (Exception ex) {
			}
			return 0;
		}
		List<CardEntity> synchronizables = actor.getSynchronizables();
		List<ImogBean> entities = new Vector<ImogBean>();

		for (CardEntity classEntity : synchronizables) {
			ImogBeanHandler<?> dao = dataManager.getHandler(classEntity.getClassName());
			entities.addAll(dao.loadModified(lastSynchro, null));
		}

		logger.debug("Number of entities to send : " + entities.size());
		try {
			serializer.serialize(entities, os);
			os.close();
		} catch (ImogSerializationException e) {
			throw new SynchronizationException("Error preparing the data to synchronize: " + e.getLocalizedMessage(),
					e);
		} catch (IOException e) {
			throw new SynchronizationException("Error preparing the data to synchronize: " + e.getLocalizedMessage(),
					e);
		}
		return entities.size();
	}

	@Override
	@Transactional
	public int applyIncomingModifications(InputStream is) throws ImogSerializationException {
		int i = serializer.processMulti(is, null);
		logger.debug(i + " entitie(s) added or updated to the database");
		return i;
	}

	/**
	 * Compute the date of the last synchronization
	 * 
	 * @return the date of the last synchronization
	 */
	private Date computeLastDate() {
		SyncHistory history = historyDao.loadLastOk();
		if (history != null) {
			return history.getTime();
		}
		return new Date(-1);
	}

}
