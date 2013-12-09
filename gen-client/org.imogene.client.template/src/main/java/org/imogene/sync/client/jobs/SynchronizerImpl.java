package org.imogene.sync.client.jobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.common.sync.entity.SynchronizableEntity;
import org.imogene.lib.sync.EntityHelper;
import org.imogene.lib.sync.client.OptimizedSyncClient;
import org.imogene.lib.sync.client.SyncClient;
import org.imogene.lib.sync.client.SynchronizationException;
import org.imogene.lib.sync.client.http.OptimizedSyncClientHttp;
import org.imogene.lib.sync.client.parameter.SyncParameter;
import org.imogene.lib.sync.client.parameter.SyncParameterDao;
import org.imogene.lib.sync.handler.DataHandlerManager;
import org.imogene.lib.sync.handler.ImogBeanHandler;
import org.imogene.lib.sync.history.SyncHistory;
import org.imogene.lib.sync.history.SyncHistoryDao;
import org.imogene.lib.sync.serializer.ImogSerializationException;
import org.imogene.lib.sync.serializer.ImogSerializer;
import org.imogene.sync.client.SyncActivator;
import org.imogene.sync.client.i18n.Messages;
import org.springframework.transaction.annotation.Transactional;

public class SynchronizerImpl implements Synchronizer {

	private static final Logger logger = Logger.getLogger("org.imogene.sync.client.jobs.SyncJobImpl"); //$NON-NLS-1$

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
	 * @param client The {@link OptimizedSyncClient} implementation for the synchronization.
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

	/**
	 * Setter for bean injection.
	 * 
	 * @param syncParameterDao The {@link SyncParameterDao} implementation of the application.
	 */
	public void setSyncParameterDao(SyncParameterDao syncParameterDao) {
		this.syncParameterDao = syncParameterDao;
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
	@Transactional
	public void setOffset(long offset) {
		SyncParameter params = syncParameterDao.load();
		if (params == null) {
			params = new SyncParameter();
		}
		params.setOffset(offset);
		syncParameterDao.store(params);
	}

	@Override
	@Transactional
	public void setParameters(String url, String terminal) {
		SyncParameter params = syncParameterDao.load();
		if (params == null) {
			params = new SyncParameter();
		}
		params.setUrl(url);
		params.setTerminalId(terminal);
		syncParameterDao.store(params);
	}

	@Override
	@Transactional
	public SyncParameter getParameters() {
		return syncParameterDao.load();
	}

	@Override
	@Transactional
	public synchronized IStatus synchronize(IProgressMonitor monitor) {
		try {
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			monitor.beginTask(Messages.sync_title, 5);

			/* 0 - Initialization */
			monitor.subTask(Messages.sync_init);
			// Load sync parameters
			SyncParameter param = syncParameterDao.load();
			if (param == null || param.getLogin() == null || param.getPassword() == null) {
				throw new SynchronizationException("Synchronization parameters not initialized, thread not started", //$NON-NLS-1$
						SynchronizationException.ERROR_INIT);
			}
			byte[] encpwd = Base64.decodeBase64(param.getPassword().getBytes());
			String password = new String(encryptionManager.decrypt(encpwd));
			syncClient = new OptimizedSyncClientHttp(param.getUrl(), param.getLogin(), password, param.getTerminalId(),
					param.getType());

			// Look for synchronization ERROR.
			SyncHistory error = historyDao.loadLastError();

			// We resume the synchronization process
			if (error != null) {
				resumeOnError(monitor, error);
			}
			monitor.worked(1);

			// new synchronization process even we proceed to a resume on error before
			/* 1 - initialize the session */
			monitor.subTask(Messages.sync_open);
			String sessionId = syncClient.initSession();
			if (sessionId == null || sessionId.startsWith(SyncClient.ERROR_PREFIX)) {
				return new Status(Status.ERROR, SyncActivator.PLUGIN_ID, Messages.sync_open_error);
			}
			monitor.worked(1);

			/* 2 - send client modification */
			monitor.subTask(Messages.sync_send);
			File outFile = new File(directory, sessionId + ".lmodif"); //$NON-NLS-1$
			FileOutputStream fos = new FileOutputStream(outFile);
			// we take the date just before to access the database and to serialize
			long offset = param.getOffset() != null ? param.getOffset() : 0;
			Date tempDate = new Date(System.currentTimeMillis() + offset);
			getDataToSynchronize(fos);
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
			logger.debug("number of server modifications applied: " + res); //$NON-NLS-1$
			if (res > -1) {
				history.setLevel(SyncHistory.LEVEL_RECEIVE);
				historyDao.saveOrUpdate(history);
			} else {
				throw new SynchronizationException(
						"Error sending data to the server.", SynchronizationException.ERROR_SEND); //$NON-NLS-1$
			}
			monitor.worked(1);

			/* 3 - get server modifications */
			monitor.subTask(Messages.sync_receive);
			requestServerModification(sessionId);
			// Update the sync history
			history.setLevel(SyncHistory.LEVEL_RECEIVE);
			history.setStatus(SyncHistory.STATUS_OK);
			historyDao.saveOrUpdate(history);
			monitor.worked(1);

			/* 4 - close the session */
			monitor.subTask(Messages.sync_close);
			syncClient.closeSession(sessionId);
			// now we are sure that we never need this temp file
			outFile.delete();
			monitor.worked(1);

			return Status.OK_STATUS;
		} catch (Exception e) {
			return new Status(Status.ERROR, SyncActivator.PLUGIN_ID, Messages.sync_error, e);
		}
	}

	/**
	 * Resume a synchronization process that terminated with error.
	 * 
	 * @param error the synchronization history
	 * @throws SynchronizationException if an error occurred
	 */
	private void resumeOnError(IProgressMonitor monitor, SyncHistory error) throws SynchronizationException {
		SubMonitor sub = SubMonitor.convert(monitor);

		// we resume a sent, by re-sending local data an retrieving all the data from the server
		if (error.getLevel() == SyncHistory.LEVEL_SEND) {
			logger.debug("Resuming the sent for the session " + error.getId()); //$NON-NLS-1$
			sub.beginTask(Messages.sync_resume_send, 4);
			try {
				/* 1 - initialize the resumed session */
				monitor.subTask(Messages.sync_resume_open);
				String result = syncClient.resumeSend(error.getId());
				if (result.equals("error")) { //$NON-NLS-1$
					throw new SynchronizationException("Error resuming the session, the server return an error code", //$NON-NLS-1$
							SynchronizationException.ERROR_SEND);
				}
				monitor.worked(1);

				/* 2 - sending local modifications */
				monitor.subTask(Messages.sync_resume_sending);
				long bytesReceived = Long.parseLong(result);
				File outFile = new File(directory, error.getId() + ".lmodif"); //$NON-NLS-1$
				FileInputStream fis = new FileInputStream(outFile);
				long skipped = fis.skip(bytesReceived);
				if (skipped != bytesReceived) {
					throw new SynchronizationException(
							"Error resuming the session: " + bytesReceived //$NON-NLS-1$
									+ " bytes already received, " + skipped + " bytes skipped", SynchronizationException.ERROR_SEND); //$NON-NLS-1$ //$NON-NLS-2$
				}

				logger.debug("Re-sending data from the file " + outFile.getAbsolutePath() + " skipping " + bytesReceived //$NON-NLS-1$ //$NON-NLS-2$
						+ " bytes"); //$NON-NLS-1$
				syncClient.resumeSendModification(error.getId(), fis);
				fis.close();
				outFile.delete();
				error.setLevel(SyncHistory.LEVEL_RECEIVE);
				historyDao.saveOrUpdate(error);
				monitor.worked(1);

				/* 3 - receiving the server modifications */
				monitor.subTask(Messages.sync_receive);
				requestServerModification(error.getId());
				error.setStatus(SyncHistory.STATUS_OK);
				historyDao.saveOrUpdate(error);
				monitor.worked(1);

				/* 4 - closing the session */
				monitor.subTask(Messages.sync_resume_close);
				syncClient.closeSession(error.getId());
				monitor.worked(1);
			} catch (Exception ex) {
				SynchronizationException syx = new SynchronizationException(
						"Error resuming a sent: " + ex.getLocalizedMessage(), //$NON-NLS-1$
						ex, SynchronizationException.DEFAULT_ERROR);
				if (ex instanceof SynchronizationException)
					syx.setCode(((SynchronizationException) ex).getCode());
				throw syx;
			}
		}
		// We resume a reception, by re-receiving the server data
		if (error.getLevel() == SyncHistory.LEVEL_RECEIVE) {
			logger.debug("Resuming the receive operation for the session " + error.getId()); //$NON-NLS-1$
			sub.beginTask(Messages.sync_resume_receive, 3);
			try {
				/* 1 - initialize the resumed session */
				monitor.subTask(Messages.sync_resume_open);
				// clear the sent file
				File tmp = new File(directory, error.getId() + ".lmodif"); //$NON-NLS-1$
				if (tmp.exists()) {
					tmp.delete();
				}
				File inFile = new File(directory, error.getId() + ".smodif"); //$NON-NLS-1$
				String result = syncClient.resumeReceive(error.getId(), inFile.length());
				if (result.equals("error")) { //$NON-NLS-1$
					throw new SynchronizationException(
							"The server return an error code", SynchronizationException.ERROR_RECEIVE); //$NON-NLS-1$
				}
				monitor.worked(1);

				/* 2 - receiving data */
				monitor.subTask(Messages.sync_resume_receiving);
				FileOutputStream fos = new FileOutputStream(inFile, true);
				syncClient.resumeRequestModification(error.getId(), fos, inFile.length());
				FileInputStream sFis = new FileInputStream(inFile);
				applyIncomingModifications(sFis);
				error.setStatus(SyncHistory.STATUS_OK);
				historyDao.saveOrUpdate(error);
				monitor.worked(1);

				/* 3 - closing the session */
				monitor.subTask(Messages.sync_resume_close);
				syncClient.closeSession(error.getId());
				inFile.delete();
				monitor.worked(1);
			} catch (Exception ex) {
				SynchronizationException syx = new SynchronizationException("Error resuming a receive operation: " //$NON-NLS-1$
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
		File inFile = new File(directory, sessionId + ".smodif"); //$NON-NLS-1$
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

		logger.debug("Number of entities to send : " + entities.size()); //$NON-NLS-1$
		try {
			serializer.serialize(entities, os);
			os.close();
		} catch (ImogSerializationException se) {
			throw new SynchronizationException(
					"Error preparing the data to synchronize: " + se.getLocalizedMessage(), se); //$NON-NLS-1$
		} catch (IOException ioe) {
			throw new SynchronizationException(
					"Error preparing the data to synchronize: " + ioe.getLocalizedMessage(), ioe); //$NON-NLS-1$
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
			logger.debug(i + " entitie(s) added or updated to the database"); //$NON-NLS-1$
		} catch (ImogSerializationException ex) {
			logger.error(ex.getMessage());
		}
	}

}
