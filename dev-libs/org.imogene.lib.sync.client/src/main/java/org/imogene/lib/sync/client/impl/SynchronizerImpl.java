package org.imogene.lib.sync.client.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.NoResultException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.lib.common.dao.GenericDao;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.model.CardEntity;
import org.imogene.lib.sync.EntityHelper;
import org.imogene.lib.sync.client.OptimizedSyncClient;
import org.imogene.lib.sync.client.SyncClient;
import org.imogene.lib.sync.client.SynchronizationException;
import org.imogene.lib.sync.client.Synchronizer;
import org.imogene.lib.sync.client.http.OptimizedSyncClientHttp;
import org.imogene.lib.sync.client.params.SyncParams;
import org.imogene.lib.sync.handler.DataHandlerManager;
import org.imogene.lib.sync.handler.ImogBeanHandler;
import org.imogene.lib.sync.history.SyncHistory;
import org.imogene.lib.sync.history.SyncHistoryDao;
import org.imogene.lib.sync.serializer.ImogSerializationException;
import org.imogene.lib.sync.serializer.ImogSerializer;
import org.springframework.transaction.annotation.Transactional;

public class SynchronizerImpl implements Synchronizer {

	private static final Logger logger = Logger.getLogger(SynchronizerImpl.class.getName());

	// Injected by Spring
	private SyncHistoryDao historyDao;
	private GenericDao genericDao;
	private DataHandlerManager dataManager;
	private ImogSerializer serializer;
	private EntityHelper entityHelper;
	private EncryptionManager encryptionManager;
	private File directory;

	// NOT injected by Spring
	private OptimizedSyncClient syncClient;

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
	 * @param dao The {@link SyncHistoryDao} implementation of the application.
	 */
	public void setSyncHistoryDao(SyncHistoryDao dao) {
		this.historyDao = dao;
	}

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
	 * @param entityHelper The {@link EntityHelper} implementation of the application.
	 */
	public void setEntityHelper(EntityHelper entityHelper) {
		this.entityHelper = entityHelper;
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param encryptionManager The {@link EncryptionManager} of the application.
	 */
	public void setEncryptionManager(EncryptionManager encryptionManager) {
		this.encryptionManager = encryptionManager;
	}

	@Override
	public int authenticate(String url, String login, String password) {
		OptimizedSyncClient client = new OptimizedSyncClientHttp(url, login, password, null);
		try {
			boolean authenticated = client.authenticate();
			if (authenticated) {
				return 0;
			} else {
				return -1;
			}
		} catch (SynchronizationException e) {
			return -1;
		}
	}

	@Override
	@Transactional
	public int synchronize() {
		try {
			// Load sync parameters
			SyncParams params = genericDao.load(SyncParams.class, SyncParams.ID);
			if (params == null) {
				throw new SynchronizationException("Synchronization parameters not initialized, thread not started",
						SynchronizationException.ERROR_INIT);
			}
			String password = new String(
					encryptionManager.decrypt(Base64.decodeBase64(params.getPassword().getBytes())));
			return synchronize(params.getUrl(), params.getLogin(), password, params.getTerminal(), params.getOffset());
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	@Transactional
	public int synchronize(String url, String login, String password, String terminal, Long offset) {
		try {
			/* 0 - Initialization */
			if (url == null || login == null || password == null || terminal == null) {
				throw new SynchronizationException("Invalid synchronization parameters, thread not started",
						SynchronizationException.ERROR_INIT);
			}
			syncClient = new OptimizedSyncClientHttp(url, login, password, terminal);

			// Look for synchronization ERROR.
			SyncHistory error = historyDao.loadLastError();

			// We resume the synchronization process
			if (error != null) {
				resumeOnError(error);
			}

			// new synchronization process even we proceed to a resume on error before
			/* 1 - initialize the session */
			String sessionId = syncClient.initSession();
			if (sessionId == null || sessionId.startsWith(SyncClient.ERROR_PREFIX)) {
				return -1;
			}

			/* 2 - send client modification */
			File outFile = new File(directory, sessionId + ".lmodif");
			FileOutputStream fos = new FileOutputStream(outFile);
			// we take the date just before to access the database and to serialize
			Date tempDate = offset != null ? new Date(System.currentTimeMillis() + offset) : new Date();
			getDataToSynchronize(login, fos);
			fos.close();
			// update sync history
			SyncHistory history = new SyncHistory();
			history.setId(sessionId);
			history.setTime(tempDate);
			history.setStatus(SyncHistory.STATUS_ERROR);
			history.setLevel(SyncHistory.LEVEL_SEND);
			historyDao.saveOrUpdate(history);
			// send data
			FileInputStream fis = new FileInputStream(outFile);
			int res = syncClient.sendClientModification(sessionId, fis);
			fis.close();
			logger.debug("number of server modifications applied: " + res);
			if (res > -1) {
				history.setLevel(SyncHistory.LEVEL_RECEIVE);
				historyDao.saveOrUpdate(history);
			} else {
				throw new SynchronizationException("Error sending data to the server.",
						SynchronizationException.ERROR_SEND);
			}

			/* 3 - get server modifications */
			requestServerModification(sessionId);
			// Update the sync history
			history.setLevel(SyncHistory.LEVEL_RECEIVE);
			history.setStatus(SyncHistory.STATUS_OK);
			historyDao.saveOrUpdate(history);

			/* 4 - close the session */
			syncClient.closeSession(sessionId);
			// now we are sure that we never need this temp file
			outFile.delete();
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Resume a synchronization process that terminated with error.
	 * 
	 * @param error the synchronization history
	 * @throws SynchronizationException if an error occurred
	 */
	private void resumeOnError(SyncHistory error) throws SynchronizationException {
		// we resume a sent, by re-sending local data an retrieving all the data from the server
		if (error.getLevel() == SyncHistory.LEVEL_SEND) {
			logger.debug("Resuming the sent for the session " + error.getId());
			try {
				/* 1 - initialize the resumed session */
				String result = syncClient.resumeSend(error.getId());
				if (result.equals("error")) {
					throw new SynchronizationException("Error resuming the session, the server return an error code",
							SynchronizationException.ERROR_SEND);
				}

				/* 2 - sending local modifications */
				long bytesReceived = Long.parseLong(result);
				File outFile = new File(directory, error.getId() + ".lmodif");
				FileInputStream fis = new FileInputStream(outFile);
				long skipped = fis.skip(bytesReceived);
				if (skipped != bytesReceived) {
					fis.close();
					throw new SynchronizationException("Error resuming the session: " + bytesReceived
							+ " bytes already received, " + skipped + " bytes skipped",
							SynchronizationException.ERROR_SEND);
				}

				logger.debug("Re-sending data from the file " + outFile.getAbsolutePath() + " skipping "
						+ bytesReceived + " bytes");
				syncClient.resumeSendModification(error.getId(), fis);
				fis.close();
				outFile.delete();
				error.setLevel(SyncHistory.LEVEL_RECEIVE);
				historyDao.saveOrUpdate(error);

				/* 3 - receiving the server modifications */
				requestServerModification(error.getId());
				error.setStatus(SyncHistory.STATUS_OK);
				historyDao.saveOrUpdate(error);

				/* 4 - closing the session */
				syncClient.closeSession(error.getId());
			} catch (Exception ex) {
				SynchronizationException syx = new SynchronizationException("Error resuming a sent: "
						+ ex.getLocalizedMessage(), ex, SynchronizationException.DEFAULT_ERROR);
				if (ex instanceof SynchronizationException)
					syx.setCode(((SynchronizationException) ex).getCode());
				throw syx;
			}
		}
		// We resume a reception, by re-receiving the server data
		if (error.getLevel() == SyncHistory.LEVEL_RECEIVE) {
			logger.debug("Resuming the receive operation for the session " + error.getId());
			try {
				/* 1 - initialize the resumed session */
				// clear the sent file
				File tmp = new File(directory, error.getId() + ".lmodif");
				if (tmp.exists()) {
					tmp.delete();
				}
				File inFile = new File(directory, error.getId() + ".smodif");
				String result = syncClient.resumeReceive(error.getId(), inFile.length());
				if (result.equals("error")) {
					throw new SynchronizationException("The server return an error code",
							SynchronizationException.ERROR_RECEIVE);
				}

				/* 2 - receiving data */
				FileOutputStream fos = new FileOutputStream(inFile, true);
				syncClient.resumeRequestModification(error.getId(), fos, inFile.length());
				FileInputStream sFis = new FileInputStream(inFile);
				applyIncomingModifications(sFis);
				error.setStatus(SyncHistory.STATUS_OK);
				historyDao.saveOrUpdate(error);

				/* 3 - closing the session */
				syncClient.closeSession(error.getId());
				inFile.delete();
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
		File inFile = new File(directory, sessionId + ".smodif");
		FileOutputStream sFos = new FileOutputStream(inFile);
		syncClient.requestServerModifications(sessionId, sFos);
		sFos.close();
		FileInputStream sFis = new FileInputStream(inFile);
		applyIncomingModifications(sFis);
		sFis.close();
		inFile.delete();
	}

	/**
	 * Get the entities to be sent to the server for synchronization
	 * 
	 * @param os output stream where to write data
	 * @return number of entities to be synchronized
	 */
	private Integer getDataToSynchronize(String login, OutputStream os) throws SynchronizationException {
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

		boolean allowBinaries = false;

		for (CardEntity classEntity : synchronizables) {
			if (BinaryFile.class.getName().equals(classEntity.getClassName())) {
				allowBinaries = true;
			} else {
				ImogBeanHandler<?> dao = dataManager.getHandler(classEntity.getClassName());
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
			throw new SynchronizationException("Error preparing the data to synchronize: " + se.getLocalizedMessage(),
					se);
		} catch (IOException ioe) {
			throw new SynchronizationException("Error preparing the data to synchronize: " + ioe.getLocalizedMessage(),
					ioe);
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
