package org.imogene.lib.sync.server;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.dynamicfields.DynamicFieldInstance;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogActorImpl;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.entity.ImogBeanImpl;
import org.imogene.lib.common.entity.ImogEntity;
import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.sync.SyncConstants;
import org.imogene.lib.sync.handler.BeanKeyGenerator;
import org.imogene.lib.sync.handler.DataHandlerManager;
import org.imogene.lib.sync.handler.ImogBeanHandler;
import org.imogene.lib.sync.history.SyncHistory;
import org.imogene.lib.sync.history.SyncHistoryDao;
import org.imogene.lib.sync.serializer.ImogSerializationException;
import org.imogene.lib.sync.serializer.ImogSerializer;
import org.imogene.lib.sync.server.util.HttpSessionUtil;
import org.imogene.lib.sync.session.SyncSession;
import org.imogene.lib.sync.session.SyncSessionDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of a synchronization server.
 * 
 * @author MEDES-IMPS
 */
@Transactional
public class OptimizedSyncServerImpl implements OptimizedSyncServer {

	private Logger logger = Logger.getLogger("org.imogene.sync.server");

	private GenericDao genericDao;
	private SyncHistoryDao historyDao;
	private SyncSessionDao sessionDao;

	private DataHandlerManager dataHandlerManager;

	private ImogSerializer serializer;

	private EntityHelper entityHelper;

	@Override
	public int applyClientModifications(String sessionId, InputStream data) throws ImogSerializationException {
		if (checkSession(sessionId)) {
			SyncSession session = sessionDao.load(sessionId);
			ImogActor currentUser = genericDao.load(ImogActorImpl.class, session.getUserId());
			return serializer.processMulti(data, currentUser);
		}
		return -1;
	}

	@Override
	public boolean checkSession(String sessionId) {
		try {
			Thread.sleep(1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sessionDao.isValid(sessionId);
	}

	@Override
	public int closeSession(String sessionId, int status) {
		/* store the history of the session */
		SyncHistory history = new SyncHistory();
		history.setId(BeanKeyGenerator.getNewId("HIS"));
		SyncSession session = sessionDao.load(sessionId);
		history.setTerminalId(session.getTerminalId());
		history.setTime(session.getSendDate());
		history.setStatus(status);
		historyDao.saveOrUpdate(history);
		historyDao.deleteOld(session.getTerminalId());
		return 0;
	}

	@Override
	public void searchEntity(String entityId, OutputStream out) throws ImogSerializationException {
		ImogBean bean = genericDao.load(ImogBeanImpl.class, entityId);
		if (bean != null) {
			List<ImogBean> entities = entityHelper.getAssociatedEntitiesIds(bean);
			entities.add(bean);
			serializer.serialize(entities, out);
		}
	}

	@Override
	public void getServerModifications(String sessionId, OutputStream out) throws ImogSerializationException {
		if (checkSession(sessionId)) {
			SyncSession session = sessionDao.load(sessionId);
			ImogActor currentUser = genericDao.load(ImogActorImpl.class, session.getUserId());

			List<ImogBean> allEntities = new Vector<ImogBean>();
			List<CardEntity> synchronizables = currentUser.getSynchronizables();
			Date lastDate = computeLastDate(session.getTerminalId());

			boolean allowBinaries = false;

			// serialize entities
			for (CardEntity synchronizable : synchronizables) {
				if (BinaryFile.class.getName().equals(synchronizable.getClassName())) {
					allowBinaries = true;
					continue;
				} else if (DynamicFieldInstance.class.getName().equals(synchronizable.getClassName())) {
					continue;
				} else {
					ImogBeanHandler<? extends ImogBean> handler = dataHandlerManager.getHandler(synchronizable
							.getClassName());
					if (handler != null) {
						List<? extends ImogBean> modified = null;
						if (lastDate == null) {
							modified = handler.loadEntities(currentUser, session.getTerminalId());
						} else {
							modified = handler.loadUploaded(lastDate, currentUser, session.getTerminalId());
						}

						if (modified != null && modified.size() > 0) {
							allEntities.addAll(modified);
						}
					}
				}
			}

			logger.debug("SeMo: " + allEntities.size() + " entities loaded, before filtering with the terminal id");

			// removes entities that have just been sent by the client terminal
			List<ImogBean> toBeRemoved = new Vector<ImogBean>();
			for (ImogBean entity : allEntities) {
				if (entity.getModifiedFrom().equals(SyncConstants.SYNC_ID_SYS)
						|| entity.getModifiedFrom().equals(session.getTerminalId())) {
					toBeRemoved.add(entity);
				}
			}
			allEntities.removeAll(toBeRemoved);

			// Add dynamic field instances
			List<DynamicFieldInstance> instances = new Vector<DynamicFieldInstance>();
			for (ImogBean bean : allEntities) {
				if (bean instanceof ImogEntity) {
					if (lastDate == null) {
						List<DynamicFieldInstance> toAdd = ((ImogEntity) bean).getDynamicFieldValues();
						if (toAdd != null && !toAdd.isEmpty()) {
							instances.addAll(toAdd);
						}
					} else {
						List<DynamicFieldInstance> internalInstances = ((ImogEntity) bean).getDynamicFieldValues();
						if (internalInstances != null && internalInstances.size() > 0) {
							for (DynamicFieldInstance instance : internalInstances) {
								if (instance.getUploadDate().after(lastDate)) {
									instances.add(instance);
								}
							}
						}
					}
				}
			}
			if (!instances.isEmpty()) {
				allEntities.addAll(instances);
			}

			if (allowBinaries) {
				List<Binary> binaries = null;
				if (lastDate == null) {
					binaries = entityHelper.getAssociatedBinaries(allEntities);
				} else {
					binaries = entityHelper.getAssociatedBinariesUploaded(allEntities, lastDate);
				}
				if (binaries != null && binaries.size() > 0) {
					allEntities.addAll(binaries);
				}
			}

			// TODO serialize current user
			/*
			 * if (! (user instanceof DefaultUser)) { Synchronizable modifiedUser; EntityHandler handler =
			 * dataHandlerManager.getHandler (user.getClass().getSuperclass().getName()); if (lastDate!=null)
			 * modifiedUser = handler.getDao().loadModified(lastDate, user.getId()); else modifiedUser =
			 * handler.getDao().loadEntity(user.getId());
			 * 
			 * if (modifiedUser!=null) allEntities.add(modifiedUser); }
			 */
			logger.debug("SeMo: " + allEntities.size() + " entities kept, before the serialization");
			serializer.serialize(allEntities, out);
		}
	}

	@Override
	public String initSession(String termId) {
		ImogActor actor = HttpSessionUtil.getCurrentUser();
		if (actor == null) {
			return null;
		}
		SyncSession session = new SyncSession();
		session.setId(UUID.randomUUID().toString());
		session.setTerminalId(termId);
		session.setUserId(actor.getId());
		session.setInitDate(new Date(System.currentTimeMillis()));
		session.setSendDate(new Date(System.currentTimeMillis()));
		sessionDao.saveOrUpdate(session);
		return session.getId().toString();
	}

	@Override
	public String initResumeSendSession(String termiId, String type, ImogActor user, String sessionId) {
		return null;
	}

	@Override
	public String initResumeRequestSession(String termiId, String type, ImogActor user, String sessionId,
			long bytesReceived) {
		return "OK";
	}

	@Override
	public File getFileDirectory() {
		throw new RuntimeException("Not impelmented.");
	}

	@Override
	public int resumeApplyClientModifications(String sessionId, InputStream data) throws ImogSerializationException {
		return 0;
	}

	@Override
	public void resumeGetServerModifications(String sessionId, OutputStream out, long bytesToSkip)
			throws ImogSerializationException {
	}

	/**
	 * Compute the date of the last synchronization of the specified terminal;
	 * 
	 * @param terminalId the terminal id
	 * @return the date or null if it is the first synchronization.
	 */
	private Date computeLastDate(String terminalId) {
		SyncHistory last = historyDao.loadLastOk(terminalId);
		if (last != null) {
			return last.getTime();
		}
		return null;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param historyHandler
	 */
	public void setHistoryDao(SyncHistoryDao pHistoryDao) {
		this.historyDao = pHistoryDao;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param historyHandler
	 */
	public void setSessionDao(SyncSessionDao pSessionDao) {
		this.sessionDao = pSessionDao;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param genericDao
	 */
	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param dataHandlerManager
	 */
	public void setDataHandlerManager(DataHandlerManager dataHandlerManager) {
		this.dataHandlerManager = dataHandlerManager;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param serializer
	 */
	public void setSerializer(ImogSerializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param helper
	 */
	public void setEntityHelper(EntityHelper helper) {
		this.entityHelper = helper;
	}

}
