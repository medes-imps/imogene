package org.imogene.lib.sync.client.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.log4j.Logger;
import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.sync.client.OptimizedSyncClient;
import org.imogene.lib.sync.client.SyncClient;
import org.imogene.lib.sync.client.SyncHandler;
import org.imogene.lib.sync.client.SynchronizationException;
import org.imogene.lib.sync.client.Synchronizer;
import org.imogene.lib.sync.client.http.OptimizedSyncClientHttp;
import org.imogene.lib.sync.client.params.SyncParams;
import org.imogene.lib.sync.history.SyncHistory;
import org.imogene.lib.sync.serializer.ImogSerializationException;
import org.springframework.transaction.annotation.Transactional;

public class SynchronizerImpl implements Synchronizer {

	private static final Logger logger = Logger.getLogger(SynchronizerImpl.class.getName());

	// Injected by Spring
	private SyncHandler syncHandler;
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
	 * @param encryptionManager The {@link EncryptionManager} of the application.
	 */
	public void setEncryptionManager(EncryptionManager encryptionManager) {
		this.encryptionManager = encryptionManager;
	}

	/**
	 * Setter for bean injection.
	 * 
	 * @param syncHandler The {@link SyncHandler} of the application.
	 */
	public void setSyncHandler(SyncHandler syncHandler) {
		this.syncHandler = syncHandler;
	}

	@Override
	public int authenticate(String url, String login, String password) {
		OptimizedSyncClient client = new OptimizedSyncClientHttp(url, login, password, null);
		try {
			boolean authenticated = client.authenticate();
			if (authenticated) {
				return AUTH_SUCCESS;
			} else {
				return AUTH_FAILURE;
			}
		} catch (SynchronizationException e) {
			return AUTH_FAILURE;
		}
	}

	@Override
	public int synchronize() {
		try {
			// Load sync parameters
			SyncParams params = syncHandler.loadParams();
			if (params == null) {
				throw new SynchronizationException("Synchronization parameters not initialized, thread not started",
						SynchronizationException.ERROR_INIT);
			}
			String password = new String(
					encryptionManager.decrypt(Base64.decodeBase64(params.getPassword().getBytes())));
			return synchronize(params.getUrl(), params.getLogin(), password, params.getTerminal(), params.getOffset());
		} catch (Exception e) {
			return SYNC_FAILURE;
		}
	}

	@Override
	public int synchronize(String url, String login, String password, String terminal, Long offset) {
		try {
			/* 0 - Initialization */
			if (url == null || login == null || password == null || terminal == null) {
				throw new SynchronizationException("Invalid synchronization parameters, thread not started",
						SynchronizationException.ERROR_INIT);
			}
			syncClient = new OptimizedSyncClientHttp(url, login, password, terminal);

			// Look for synchronization ERROR.
			SyncHistory error = syncHandler.loadLastError();

			// We resume the synchronization process
			if (error != null) {
				resumeOnError(error);
			}

			// new synchronization process even we proceed to a resume on error before
			/* 1 - initialize the session */
			String sessionId = syncClient.initSession();
			if (sessionId == null || sessionId.startsWith(SyncClient.ERROR_PREFIX)) {
				return SYNC_FAILURE;
			}

			/* 2 - send client modification */
			File outFile = new File(directory, sessionId + ".lmodif");
			FileOutputStream fos = new FileOutputStream(outFile);

			// we take the date just before to access the database and to serialize
			Date tempDate = offset != null ? new Date(System.currentTimeMillis() + offset) : new Date();

			syncHandler.getDataToSynchronize(fos, sessionId, login);

			// update sync history
			SyncHistory history = new SyncHistory();
			history.setId(sessionId);
			history.setTime(tempDate);
			history.setStatus(SyncHistory.STATUS_ERROR);
			history.setLevel(SyncHistory.LEVEL_SEND);
			syncHandler.updateHistory(history);

			// send data
			FileInputStream fis = new FileInputStream(outFile);
			int res = syncClient.sendClientModification(sessionId, fis);
			fis.close();
			logger.debug("number of server modifications applied: " + res);

			if (res < 0) {
				throw new SynchronizationException("Error sending data to the server.",
						SynchronizationException.ERROR_SEND);
			}

			history.setLevel(SyncHistory.LEVEL_RECEIVE);
			syncHandler.updateHistory(history);

			/* 3 - get server modifications */
			requestServerModification(sessionId);

			// Update the sync history
			history.setStatus(SyncHistory.STATUS_OK);
			syncHandler.updateHistory(history);

			/* 4 - close the session */
			syncClient.closeSession(sessionId);
			// now we are sure that we never need this temp file
			outFile.delete();
			return SYNC_SUCCESS;
		} catch (Exception e) {
			return SYNC_FAILURE;
		}
	}

	/**
	 * Resume a synchronization process that terminated with error.
	 * 
	 * @param error the synchronization history
	 * @throws ImogSerializationException
	 * @throws Exception
	 */
	private void resumeOnError(SyncHistory error)
			throws AuthenticationException, SynchronizationException, IOException, ImogSerializationException {
		// we resume a sent, by re-sending local data an retrieving all the data from the server
		if (error.getLevel() == SyncHistory.LEVEL_SEND) {
			logger.debug("Resuming the sent for the session " + error.getId());
			/* 1 - initialize the resumed session */
			long bytesReceived = syncClient.resumeSend(error.getId());

			/* 2 - sending local modifications */
			File outFile = new File(directory, error.getId() + ".lmodif");
			FileInputStream fis = new FileInputStream(outFile);
			long skipped = fis.skip(bytesReceived);
			if (skipped != bytesReceived) {
				fis.close();
				throw new SynchronizationException("Error resuming the session: " + bytesReceived
						+ " bytes already received, " + skipped + " bytes skipped",
						SynchronizationException.ERROR_SEND);
			}

			logger.debug("Re-sending data from the file " + outFile.getAbsolutePath() + " skipping " + bytesReceived
					+ " bytes");
			syncClient.resumeSendModification(error.getId(), fis);
			fis.close();
			error.setLevel(SyncHistory.LEVEL_RECEIVE);
			syncHandler.updateHistory(error);

			/* 3 - receiving the server modifications */
			requestServerModification(error.getId());

			error.setStatus(SyncHistory.STATUS_OK);
			syncHandler.updateHistory(error);

			/* 4 - closing the session */
			syncClient.closeSession(error.getId());

			// now we are sure that we never need this temp file
			outFile.delete();
		}
		// We resume a reception, by re-receiving the server data
		else if (error.getLevel() == SyncHistory.LEVEL_RECEIVE) {
			logger.debug("Resuming the receive operation for the session " + error.getId());
			/* 1 - initialize the resumed session */
			// clear the sent file
			File tmp = new File(directory, error.getId() + ".lmodif");
			if (tmp.exists()) {
				tmp.delete();
			}
			File inFile = new File(directory, error.getId() + ".smodif");
			syncClient.resumeReceive(error.getId(), inFile.length());

			/* 2 - receiving data */
			FileOutputStream fos = new FileOutputStream(inFile, true);
			syncClient.resumeRequestModification(error.getId(), fos, inFile.length());
			FileInputStream sFis = new FileInputStream(inFile);
			syncHandler.applyIncomingModifications(sFis);
			error.setStatus(SyncHistory.STATUS_OK);
			syncHandler.updateHistory(error);

			/* 3 - closing the session */
			syncClient.closeSession(error.getId());
			inFile.delete();
		}
	}

	private void requestServerModification(String sessionId)
			throws SynchronizationException, IOException, ImogSerializationException {
		File inFile = new File(directory, sessionId + ".smodif");
		FileOutputStream sFos = new FileOutputStream(inFile);
		syncClient.requestServerModifications(sessionId, sFos);
		sFos.close();
		FileInputStream sFis = new FileInputStream(inFile);
		syncHandler.applyIncomingModifications(sFis);
		sFis.close();
		inFile.delete();
	}

}
