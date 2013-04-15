package org.imogene.lib.sync.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.sync.entity.SynchronizableEntity;
import org.imogene.lib.sync.EntityHelper;
import org.imogene.lib.sync.client.http.OptimizedSyncClientHttp;
import org.imogene.lib.sync.client.parameter.SyncParameter;
import org.imogene.lib.sync.client.parameter.SyncParameterDao;
import org.imogene.lib.sync.handler.DataHandlerManager;
import org.imogene.lib.sync.handler.ImogBeanHandler;
import org.imogene.lib.sync.history.SyncHistory;
import org.imogene.lib.sync.history.SyncHistoryDao;
import org.imogene.lib.sync.serializer.ImogSerializationException;
import org.imogene.lib.sync.serializer.ImogSerializer;
import org.springframework.transaction.annotation.Transactional;

public class OptimizedSynchronizerImpl implements OptimizedSynchronizer {

	private static final Logger logger = Logger.getLogger("org.imogene.lib.sync.client.OptimizedSynchronizer");

	// Injected by Spring
	private SyncHistoryDao historyDao;
	private SyncParameterDao syncParameterDao;
	private DataHandlerManager dataManager;
	private ImogSerializer serializer;
	private EntityHelper entityHelper;
	private EncryptionManager encryptionManager;
	private File directory;

	// NOT injected by Spring
	private OptimizedSyncClient syncClient;
	private Set<SyncListener> listeners = Collections.synchronizedSet(new HashSet<SyncListener>());

	/**
	 * Setter for bean injection.
	 * 
	 * @param directory The directory for the synchronization files.
	 */
	public void setDirectory(File directory) {
		this.directory = directory;
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param path The directory for the synchronization files.
	 */
	public void setDirectoryPath(String path) {
		setDirectory(new File(path));
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param client The {@link OptimizedSyncClient} implementation for the
	 *            synchronization.
	 */
	public void setSyncClient(OptimizedSyncClient client) {
		this.syncClient = client;
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
	 * @param manager The {@link DataHandlerManager} implementation of the
	 *            application.
	 */
	public void setDataManager(DataHandlerManager manager) {
		this.dataManager = manager;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param serializer The {@link ImogSerializer} implementation of the
	 *            application.
	 */
	public void setSerializer(ImogSerializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param entityHelper The {@link EntityHelper} implementation of the
	 *            application.
	 */
	public void setEntityHelper(EntityHelper entityHelper) {
		this.entityHelper = entityHelper;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param syncParameterDao The {@link SyncParameterDao} implementation of
	 *            the application.
	 */
	public void setSyncParameterDao(SyncParameterDao syncParameterDao) {
		this.syncParameterDao = syncParameterDao;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param encryptionManager The {@link EncryptionManager} of the
	 *            application.
	 */
	public void setEncryptionManager(EncryptionManager encryptionManager) {
		this.encryptionManager = encryptionManager;
	}

	@Override
	public void addSyncListener(SyncListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(SyncListener listener) {
		listeners.remove(listener);
	}

	@Override
	@Transactional
	public void synchronize() {
		try {
			oneProcess();
		} catch (SynchronizationException e) {
			synchronized (listeners) {
				for (SyncListener listener : listeners) {
					listener.syncError(e.getCode(), e);
				}
			}
			logger.error(e);
			e.printStackTrace();
		}
	}

	private void oneProcess() throws SynchronizationException {
		try {
			SyncParameter param = syncParameterDao.load();
			if (syncClient == null) {
				byte[] encpwd = Base64.decodeBase64(param.getPassword().getBytes());
				byte[] pwd = encryptionManager.decrypt(encpwd);
				String password = new String(pwd);
				syncClient = new OptimizedSyncClientHttp(param.getUrl(), param.getLogin(), password, param.getTerminalId(),
						param.getType());
			}
			syncClient.setUrl(param.getUrl());

			// TODO look for synchronization ERROR.
			SyncHistory error = historyDao.loadLastError();

			/*
			 * We resume the synchronization process
			 */
			if (error != null) {
				resumeOnError(error);
			}

			/*
			 * new synchronization process even we proceed to a resume on error
			 * before
			 */
			/* 1 - initialize the session */
			logger.debug("starting a synchronization session");
			String sessionId = syncClient.initSession();
			synchronized (listeners) {
				for (SyncListener listener : listeners)
					listener.initSession(sessionId);
			}
			if (sessionId != null && !sessionId.startsWith(SyncClient.ERROR_PREFIX)) {
				logger.debug("Session id " + sessionId);

				/* 2 - send client modification */
				logger.debug("Starting sending local modification.");
				File outFile = new File(directory, sessionId + ".lmodif");
				FileOutputStream fos = new FileOutputStream(outFile);
				/*
				 * we take the date just before to access the database and to
				 * serialize
				 */
				Date tempDate = new Date(System.currentTimeMillis());
				getDataToSynchronize(fos);
				fos.close();

				SyncHistory history = new SyncHistory();
				history.setId(sessionId);
				history.setTime(tempDate);
				history.setStatus(SyncHistory.STATUS_ERROR);
				history.setLevel(SyncHistory.LEVEL_SEND);
				historyDao.saveOrUpdate(history);
				synchronized (listeners) {
					for (SyncListener listener : listeners)
						listener.sending(0);
				}
				/* send data */
				FileInputStream fis = new FileInputStream(outFile);
				int res = syncClient.sendClientModification(sessionId, fis);
				fis.close();
				logger.debug("number of server modifications applied: " + res);
				if (res > -1) {
					history.setLevel(SyncHistory.LEVEL_RECEIVE);
					historyDao.saveOrUpdate(history);
				} else {
					throw new SynchronizationException("Error sending data to the server.", SynchronizationException.ERROR_SEND);
				}

				/* 3 - get server modifications */
				logger.debug("Starting receiving server modifications");
				File inFile = new File(directory, sessionId + ".smodif");
				FileOutputStream sFos = new FileOutputStream(inFile);

				synchronized (listeners) {
					for (SyncListener listener : listeners)
						listener.receiving(0);
				}

				syncClient.requestServerModifications(sessionId, sFos);
				sFos.close();
				FileInputStream sFis = new FileInputStream(inFile);
				applyIncomingModifications(sFis);
				sFis.close();

				/* Update the sync history */
				history.setLevel(SyncHistory.LEVEL_RECEIVE);
				history.setStatus(SyncHistory.STATUS_OK);
				historyDao.saveOrUpdate(history);

				/* 4 - close the session */
				logger.debug("Closing the session");
				syncClient.closeSession(sessionId);

				synchronized (listeners) {
					for (SyncListener listener : listeners)
						listener.finish();
				}

				/* now we are sure that we never need this temp file */
				inFile.delete();
				outFile.delete();
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
			SynchronizationException syx = new SynchronizationException("Error during the synchronization caused by : "
					+ ioe.getLocalizedMessage(), ioe, SynchronizationException.DEFAULT_ERROR);
			if (ioe instanceof SynchronizationException)
				syx.setCode(((SynchronizationException) ioe).getCode());
			throw syx;
		}
	}

	/**
	 * Resume a synchronization process that terminated with error.
	 * 
	 * @param error the synchronization history
	 * @throws SynchronizationException if an error occurred
	 */
	private void resumeOnError(SyncHistory error) throws SynchronizationException {
		/*
		 * we resume a sent, by re-sending local data an retrieving all the data
		 * from the server
		 */
		if (error.getLevel() == SyncHistory.LEVEL_SEND) {
			logger.debug("Resuming the sent for the session " + error.getId());
			try {

				synchronized (listeners) {
					for (SyncListener listener : listeners)
						listener.resumeSend(0, 0);
				}

				/* 1 - initialize the resumed session */
				String result = syncClient.resumeSend(error.getId());
				/* 2 - sending local modifications */
				if (result.equals("error")) {
					throw new SynchronizationException("Error resuming the session, the server return an error code",
							SynchronizationException.ERROR_SEND);
				} else {
					long bytesReceived = Long.parseLong(result);
					File outFile = new File(directory, error.getId() + ".lmodif");
					FileInputStream fis = new FileInputStream(outFile);
					long skipped = fis.skip(bytesReceived);
					if (skipped != bytesReceived) {
						throw new SynchronizationException("Error resuming the session: " + bytesReceived
								+ " bytes already received, " + skipped + " bytes skipped", SynchronizationException.ERROR_SEND);
					}

					logger.debug("Re-sending data from the file " + outFile.getAbsolutePath() + " skipping " + bytesReceived
							+ " bytes");
					syncClient.resumeSendModification(error.getId(), fis);
					fis.close();
					outFile.delete();
				}
				error.setLevel(SyncHistory.LEVEL_RECEIVE);
				historyDao.saveOrUpdate(error);
				/* 3 - receiving the server modifications */
				requestServerModification(error.getId());
				error.setStatus(SyncHistory.STATUS_OK);
				historyDao.saveOrUpdate(error);
				/* 4 - closing the session */
				logger.debug("Closing the session");
				syncClient.closeSession(error.getId());

				synchronized (listeners) {
					for (SyncListener listener : listeners)
						listener.finish();
				}

			} catch (Exception ex) {
				SynchronizationException syx = new SynchronizationException("Error resuming a sent:" + ex.getLocalizedMessage(),
						ex, SynchronizationException.DEFAULT_ERROR);
				if (ex instanceof SynchronizationException)
					syx.setCode(((SynchronizationException) ex).getCode());
				throw syx;
			}
		}
		/*
		 * we resume a reception, by re-receiving the server data
		 */
		if (error.getLevel() == SyncHistory.LEVEL_RECEIVE) {
			logger.debug("Resuming the receive operation for the session " + error.getId());
			try {
				/* clear the sent file */
				File tmp = new File(directory, error.getId() + ".lmodif");
				if (tmp.exists())
					tmp.delete();
				/* 1 - initialize the resumed session */
				File inFile = new File(directory, error.getId() + ".smodif");
				String result = syncClient.resumeReceive(error.getId(), inFile.length());
				/* 2 - receiving data */
				if (!result.equals("error")) {
					FileOutputStream fos = new FileOutputStream(inFile, true);

					synchronized (listeners) {
						for (SyncListener listener : listeners)
							listener.resumeReceive(0, 0);
					}

					syncClient.resumeRequestModification(error.getId(), fos, inFile.length());
					FileInputStream sFis = new FileInputStream(inFile);
					applyIncomingModifications(sFis);
					error.setStatus(SyncHistory.STATUS_OK);
					historyDao.saveOrUpdate(error);

					/* 3 - closing the session */
					logger.debug("Closing the session");
					syncClient.closeSession(error.getId());
					inFile.delete();

					synchronized (listeners) {
						for (SyncListener listener : listeners)
							listener.finish();
					}

				} else {
					throw new SynchronizationException("The server return an error code", SynchronizationException.ERROR_RECEIVE);
				}
			} catch (Exception ex) {
				SynchronizationException syx = new SynchronizationException("Error resuming a receive operation: "
						+ ex.getLocalizedMessage(), ex, SynchronizationException.ERROR_RECEIVE);
				if (ex instanceof SynchronizationException)
					syx.setCode(((SynchronizationException) ex).getCode());
				throw syx;
			}
		}
	}

	/**
	 * Request the server modification in normal mode
	 * 
	 * @param sessionId the session Id
	 * @throws Exception if an error occurred.
	 */
	private void requestServerModification(String sessionId) throws Exception {
		logger.debug("Starting receiving server modifications");
		File inFile = new File(directory, sessionId + ".smodif");
		FileOutputStream sFos = new FileOutputStream(inFile);

		synchronized (listeners) {
			for (SyncListener listener : listeners)
				listener.receiving(0);
		}

		syncClient.requestServerModifications(sessionId, sFos);
		sFos.close();
		FileInputStream sFis = new FileInputStream(inFile);
		applyIncomingModifications(sFis);
		sFis.close();
		// inFile.delete();
	}

	/**
	 * Get the entities to be sent to the server for synchronization
	 * 
	 * @param os output stream where to write data
	 * @return number of entities to be synchronized
	 */
	private Integer getDataToSynchronize(OutputStream os) throws SynchronizationException {

		Date lastSynchro = computeLastDate();
		SyncParameter param = syncParameterDao.load();
		Set<SynchronizableEntity> synchronizables = param.getSynchronizables();
		List<ImogBean> entities = new Vector<ImogBean>();

		boolean allowBinaries = false;

		for (SynchronizableEntity classEntity : synchronizables) {
			if (BinaryFile.class.getName().equals(classEntity.getName())) {
				allowBinaries = true;
			} else {
				ImogBeanHandler<?> dao = dataManager.getHandler(classEntity.getName());
				entities.addAll(dao.loadModified(lastSynchro, null));
			}
		}

		if (allowBinaries) {
			List<Binary> binaries = null;
			if (lastSynchro == null) {
				binaries = entityHelper.getAssociatedBinaries(entities);
			} else {
				binaries = entityHelper.getAssociatedBinariesModified(entities, lastSynchro);
			}
			if (binaries != null && binaries.size() > 0) {
				entities.addAll(binaries);
			}
		}

		logger.debug("Number of entities to send : " + entities.size());
		try {
			serializer.serialize(entities, os);
			os.close();
		} catch (ImogSerializationException se) {
			throw new SynchronizationException("Error preparing the data to synchronize: " + se.getLocalizedMessage(), se);
		} catch (IOException ioe) {
			throw new SynchronizationException("Error preparing the data to synchronize: " + ioe.getLocalizedMessage(), ioe);
		}
		return entities.size();
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

	/**
	 * Apply the incoming server modification locally.
	 * 
	 * @param data incoming bytes
	 */
	private <T extends ImogBean> void applyIncomingModifications(InputStream is) throws SynchronizationException {
		try {
			int i = serializer.processMulti(is, null);
			logger.debug(i + " entitie(s) added or updated to the database");
		} catch (ImogSerializationException ex) {
			logger.error(ex.getMessage());
		}
	}

}
