package org.imogene.android.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.imogene.android.Constants;
import org.imogene.android.Constants.Paths;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.domain.ClientFilter;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.domain.ImogHelper;
import org.imogene.android.domain.SyncHistory;
import org.imogene.android.preference.Preferences;
import org.imogene.android.sync.http.OptimizedSyncClientHttp;
import org.imogene.android.util.database.DatabaseUtils;
import org.imogene.android.util.ntp.SntpException;
import org.imogene.android.util.ntp.SntpProvider;
import org.imogene.android.xml.ImogXmlConverter;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Process;
import android.util.Log;
import android.util.Xml;

public class SynchronizationController implements Runnable {

	private static final String TAG = SynchronizationController.class.getName();

	private static SynchronizationController sInstance;

	public synchronized static SynchronizationController getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new SynchronizationController(context);
		}
		return sInstance;
	}

	public enum Status {
		START, INITIALIZATION, SEND, SENT, RECEIVE, RECEIVED, CLOSE, RESUME, FINISH, FAILURE
	}

	private final BlockingQueue<Runnable> mCommands = new LinkedBlockingQueue<Runnable>();
	private final Thread mThread;
	private boolean mBusy;

	private final HashSet<SynchronizationObserver> mSynchronizationObservers = new HashSet<SynchronizationObserver>();
	private final Context mContext;
	private final Preferences mPreferences;
	private OptimizedSyncClient mSyncClient;
	private ImogXmlConverter mConverter;

	private String mLogin;
	private String mPassword;
	private String mTerminal;
	private String mServer;
	private boolean mDebug;
	private boolean mBidirectional;

	private SynchronizationController(Context context) {
		mContext = context.getApplicationContext();
		mPreferences = Preferences.getPreferences(context);
		mThread = new Thread(this);
		mThread.start();
	}

	// TODO: seems that this reading of mBusy isn't thread-safe
	public boolean isBusy() {
		return mBusy;
	}

	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		// TODO: add an end test to this infinite loop
		while (true) {
			Runnable runnable;
			try {
				runnable = mCommands.take();
			} catch (InterruptedException e) {
				continue; // re-test the condition on the eclosing while
			}
			mBusy = true;
			runnable.run();
			mBusy = false;
		}
	}

	private void put(Runnable runnable) {
		try {
			mCommands.offer(runnable);
		} catch (IllegalStateException ie) {
			throw new Error(ie);
		}
	}

	/**
	 * Any UI code that wishes for callback results (on async ops) should register their callback here (typically from
	 * onResume()). Unregistered callbacks will never be called, to prevent problems when the command completes and the
	 * activity has already paused or finished.
	 * 
	 * @param observer The callback that may be used in action methods
	 */
	public void registerSynchronizationObserver(SynchronizationObserver observer) {
		synchronized (mSynchronizationObservers) {
			observer.setRegistered(true);
			mSynchronizationObservers.add(observer);
		}
	}

	/**
	 * Any UI code that no longer wishes for callback results (on async ops) should unregister their callback here
	 * (typically from onPause()). Unregistered callbacks will never be called, to prevent problems when the command
	 * completes and the activity has already paused or finished.
	 * 
	 * @param observer The callback that may no longer be used
	 */
	public void unregisterSynchronizationObserver(SynchronizationObserver observer) {
		synchronized (mSynchronizationObservers) {
			observer.setRegistered(false);
			mSynchronizationObservers.remove(observer);
		}
	}

	public void synchronize() {
		put(new Runnable() {

			@Override
			public void run() {
				synchronizeSynchronous();
			}
		});
	}

	private void synchronizeSynchronous() {
		mTerminal = mPreferences.getSyncTerminal();
		mLogin = mPreferences.getSyncLogin();
		mPassword = mPreferences.getSyncPassword();
		mDebug = mPreferences.isDebugEnabled();

		mServer = mPreferences.getSyncServer();
		mBidirectional = mPreferences.isSyncBirdirectionnalEnabled();

		if (mPreferences.isHttpAuthenticationEnabled()) {
			mSyncClient = new OptimizedSyncClientHttp(mServer, mLogin, mPassword);
		} else {
			mSyncClient = new OptimizedSyncClientHttp(mServer);
		}

		mConverter = new ImogXmlConverter(mContext);
		int received = 0;
		try {
			notifyStart();

			Paths.PATH_SYNCHRO.mkdirs();

			updateTimeOffset();

			// look for synchronization ERROR.
			SyncHistory syncError = SyncHistory.getLastErrorSyncHistory(mContext);
			/*
			 * We resume the synchronization process
			 */
			if (syncError != null) {
				received += resumeOnError(syncError);
			}

			// 1 - initialize the session
			notifyInit();
			UUID sessionId = UUID.fromString(mSyncClient.initSession(mLogin, mPassword, mTerminal, "xml"));
			// 2 - send client modification
			notifySend();

			long syncTime = mPreferences.getRealTime().getTime();

			File outFile = new File(Paths.PATH_SYNCHRO, sessionId + ".lmodif");
			FileOutputStream fos = new FileOutputStream(outFile);
			getDataToSynchronize(fos, mTerminal);
			fos.close();

			SyncHistory his = new SyncHistory();
			his.id = sessionId;
			his.date = syncTime;
			his.status = SyncHistory.Columns.STATUS_ERROR;
			his.level = SyncHistory.Columns.LEVEL_SEND;
			his.saveOrUpdate(mContext);

			FileInputStream fis = new FileInputStream(outFile);
			int res = mSyncClient.sendClientModification(sessionId, fis);
			fis.close();
			if (!mDebug) {
				outFile.delete();
			}

			markAsSentForSession(syncTime);
			notifySent(res);

			if (res > -1) {
				his.level = SyncHistory.Columns.LEVEL_RECEIVE;
				his.saveOrUpdate(mContext);
			} else {
				throw new SynchronizationException("Error sending data to the server.",
						SynchronizationException.ERROR_SEND);
			}

			// 3 - get server modifications
			notifyReceive();
			received += requestServerModification(sessionId);

			his.level = SyncHistory.Columns.LEVEL_RECEIVE;
			his.status = SyncHistory.Columns.STATUS_OK;
			his.saveOrUpdate(mContext);

			notifyReceived(received);

			// 4 - close the session
			notifyClose();
			mSyncClient.closeSession(sessionId, mDebug);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "error during synchronization", e);
		} catch (IOException e) {
			Log.e(TAG, "error during synchronization", e);
		} catch (SynchronizationException e) {
			Log.e(TAG, "error during synchronization", e);
			notifyFailure(e.getCode());
		} catch (Exception e) {
			Log.e(TAG, "error during synchronization", e);
		} finally {
			markHiddenAsRead();
			notifyFinish();
		}
	}

	private void updateTimeOffset() {
		try {
			String url = mPreferences.getNtpHost();
			long offset = SntpProvider.getTimeOffsetFromNtp(url);
			mPreferences.setNtpOffset(offset);
		} catch (SntpException e) {
		}
	}

	private int resumeOnError(SyncHistory his) throws SynchronizationException {
		if (Constants.DEBUG) {
			Log.i(TAG, "resume on error : " + his.id + ", level : " + his.level + ", date : " + his.date);
		}
		/*
		 * we resume a sent, by re-sending local data an retrieving all the data from the server
		 */
		int received = 0;
		if (his.level == SyncHistory.Columns.LEVEL_SEND) {
			if (Constants.DEBUG) {
				Log.i(TAG, "Resuming the sent for the session " + his.id);
			}
			try {
				/* 1 - initialize the resumed session */
				notifyInit();
				String result = mSyncClient.resumeSend(mLogin, mPassword, mTerminal, "xml", his.id);
				/* 2 - sending local modifications */
				notifySend();
				if (result.equals("error")) {
					throw new SynchronizationException("Error resuming the session, the server return an error code",
							SynchronizationException.ERROR_SEND);
				} else {
					long bytesReceived = Long.parseLong(result);
					File outFile = new File(Paths.PATH_SYNCHRO, his.id + ".lmodif");
					FileInputStream fis = new FileInputStream(outFile);
					long skipped = fis.skip(bytesReceived);
					if (skipped != bytesReceived) {
						fis.close();
						// TODO somehow monitor this error to see if it happens once in a while
						throw new SynchronizationException("Error skipping bytes: " + bytesReceived + " bytes to skip,"
								+ skipped + " bytes skipped", SynchronizationException.ERROR_SEND);
					}
					if (Constants.DEBUG) {
						Log.i(TAG, "Re-sending data from the file " + outFile.getAbsolutePath() + " skipping "
								+ bytesReceived + " bytes");
					}
					int res = mSyncClient.resumeSendModification(his.id, fis);
					fis.close();
					if (!mDebug) {
						outFile.delete();
					}

					markAsSentForSession(his.date);
					notifySent(res);
				}
				his.level = SyncHistory.Columns.LEVEL_RECEIVE;
				his.saveOrUpdate(mContext);

				/* 3 - receiving the server modifications */
				notifyReceive();
				received = requestServerModification(his.id);

				his.status = SyncHistory.Columns.STATUS_OK;
				his.saveOrUpdate(mContext);

				notifyReceived(received);

				/* 4 - closing the session */
				notifyClose();
				mSyncClient.closeSession(his.id, mDebug);
			} catch (Exception ex) {
				SynchronizationException syx = new SynchronizationException("Error resuming a sent", ex,
						SynchronizationException.DEFAULT_ERROR);
				if (ex instanceof SynchronizationException)
					syx.setCode(((SynchronizationException) ex).getCode());
				throw syx;
			}
		}
		/*
		 * we resume a reception, by re-receiving the server data
		 */
		if (his.level == SyncHistory.Columns.LEVEL_RECEIVE) {
			if (Constants.DEBUG) {
				Log.i(TAG, "Resuming the receive operation for the session " + his.id);
			}
			try {
				/* clear the sent file */
				if (!mDebug) {
					File tmp = new File(Paths.PATH_SYNCHRO, his.id + ".lmodif");
					if (tmp.exists())
						tmp.delete();
				}
				/* 1 - initialize the resumed session */
				notifyInit();
				File inFile = new File(Paths.PATH_SYNCHRO, his.id + ".smodif");
				String result = mSyncClient.resumeReceive(mLogin, mPassword, mTerminal, "xml", his.id, inFile.length());
				/* 2 - receiving data */
				notifyReceive();
				if (!result.equals("error")) {
					received = resumeRequestModification(his.id);
					his.status = SyncHistory.Columns.STATUS_OK;
					his.saveOrUpdate(mContext);

					/* 3 - closing the session */
					notifyClose();
					mSyncClient.closeSession(his.id, mDebug);
				} else {
					throw new SynchronizationException("The server return an error code",
							SynchronizationException.ERROR_RECEIVE);
				}
			} catch (Exception ex) {
				SynchronizationException syx = new SynchronizationException("Error resuming a receive operation", ex,
						SynchronizationException.ERROR_RECEIVE);
				if (ex instanceof SynchronizationException)
					syx.setCode(((SynchronizationException) ex).getCode());
				throw syx;
			}
		}
		return received;
	}

	/**
	 * Request the server modification in normal mode
	 * 
	 * @param sessionId the session Id
	 * @throws SynchronizationException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private int requestServerModification(UUID sessionId) throws SynchronizationException, IOException,
			XmlPullParserException {
		if (!mBidirectional)
			return 0;
		File inFile = new File(Paths.PATH_SYNCHRO, sessionId + ".smodif");
		FileOutputStream sFos = new FileOutputStream(inFile);
		mSyncClient.requestServerModifications(sessionId, sFos);
		sFos.close();

		FileInputStream sFis = new FileInputStream(inFile);
		int result = applyIncomingModifications(sFis);
		sFis.close();
		if (!mDebug) {
			inFile.delete();
		}
		return result;
	}

	private int resumeRequestModification(UUID errorId) throws SynchronizationException, IOException,
			XmlPullParserException {
		if (!mBidirectional)
			return 0;
		File inputFile = new File(Paths.PATH_SYNCHRO, errorId + ".smodif");
		FileOutputStream fos = new FileOutputStream(inputFile, true);
		mSyncClient.resumeRequestModification(errorId, fos, inputFile.length());
		fos.close();

		FileInputStream fis = new FileInputStream(inputFile);
		int result = applyIncomingModifications(fis);
		fis.close();
		if (!mDebug) {
			inputFile.delete();
		}
		return result;
	}

	private int applyIncomingModifications(InputStream is) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(is, null);
		return mConverter.parse(parser);
	}

	private int getDataToSynchronize(FileOutputStream fos, String hardwareId) throws IllegalArgumentException,
			IllegalStateException, IOException {
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(SyncHistory.Columns.TABLE_NAME);
		builder.setMaxColumn(SyncHistory.Columns.DATE);
		builder.where().eq(SyncHistory.Columns.STATUS, SyncHistory.Columns.STATUS_OK);
		long date = builder.queryForLong();

		int count = 0;

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(fos, null);
		serializer.startDocument(null, Boolean.valueOf(true));
		serializer.startTag(null, "entities");

		for (Uri uri : ImogHelper.getInstance().getAllUris()) {
			QueryBuilder b = ImogOpenHelper.getHelper().queryBuilder(uri);
			b.where().gt(ImogBean.Columns.MODIFIED, date).and().eq(ImogBean.Columns.MODIFIEDFROM, hardwareId);
			b.orderBy(ImogBean.Columns.MODIFIED, true);
			ImogBeanCursor cursor = (ImogBeanCursor) b.query();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				mConverter.serialize(serializer, cursor.newBean());
				count++;
			}
			cursor.close();
		}

		serializer.endTag(null, "entities");

		serializer.endDocument();

		return count;
	}

	private void markAsSentForSession(long time) {
		if (Constants.DEBUG) {
			Log.i(TAG, "mark send entities as synchronized");
		}

		ContentResolver res = mContext.getContentResolver();
		DatabaseUtils.markSent(res, ClientFilter.Columns.CONTENT_URI, time, true);
		for (Uri uri : ImogHelper.getInstance().getAllUris()) {
			DatabaseUtils.markSent(res, uri, time, true);
		}
	}

	private void markHiddenAsRead() {
		if (Constants.DEBUG) {
			Log.i(TAG, "mark hidden received entities as read");
		}
		for (Uri uri : ImogHelper.getInstance().getHiddenUris(mContext)) {
			DatabaseUtils.markRead(mContext.getContentResolver(), uri, true);
		}
	}

	private void notifyStart() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Synchronization starting: " + mServer);
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.START, null);
			}
		}
	}

	private void notifyInit() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Initializing the synchronization");
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.INITIALIZATION, null);
			}
		}
	}

	private void notifySend() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Sending client modifications");
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.SEND, null);
			}
		}
	}

	private void notifySent(int sent) {
		if (Constants.DEBUG) {
			Log.i(TAG, "Number of sent modification: " + sent);
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.SENT, sent);
			}
		}
	}

	private void notifyReceive() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Receiving server modifications");
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.RECEIVE, null);
			}
		}
	}

	private void notifyReceived(int received) {
		if (Constants.DEBUG) {
			Log.i(TAG, "Number of modifications applied: " + received);
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.RECEIVED, received);
			}
		}
	}

	private void notifyClose() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Synchronization session closed");
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.CLOSE, null);
			}
		}
	}

	private void notifyFinish() {
		if (Constants.DEBUG) {
			Log.i(TAG, "Synchronization process finished");
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.FINISH, null);
			}
		}
	}

	private void notifyFailure(int code) {
		if (Constants.DEBUG) {
			Log.i(TAG, "Synchronization failure. code: " + code);
		}
		synchronized (mSynchronizationObservers) {
			for (SynchronizationObserver callback : mSynchronizationObservers) {
				callback.dispatchChange(Status.FAILURE, code);
			}
		}
	}

}
