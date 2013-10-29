package org.imogene.android.notification;

import java.util.HashSet;
import java.util.List;

import org.imogene.android.domain.ImogHelper;

import android.accounts.Account;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

public class NotificationController {

	private static final String TAG = NotificationController.class.getName();

	private static NotificationController sInstance;
	private static NotificationThread sNotificationThread;
	private static Handler sNotificationHandler;
	private final NotificationManager mNotificationManager;
	private final AudioManager mAudioManager;
	private final Context mContext;
	private final HashSet<ContentObserver> mNotificationSet;

	/** Singleton access */
	public static synchronized NotificationController getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new NotificationController(context);
		}
		return sInstance;
	}

	private NotificationController(Context context) {
		mContext = context.getApplicationContext();
		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		mNotificationSet = new HashSet<ContentObserver>();
	}

	/**
	 * Tells the notification controller if it should be watching for changes to the message table. This is the main
	 * life cycle method for message notifications. When we stop observing database changes, we save the state [e.g.
	 * message ID and count] of the most recent notification shown to the user. And, when we start observing database
	 * changes, we restore the saved state.
	 * 
	 * @param watch If {@code true}, we register observers for all accounts whose settings have notifications enabled.
	 *            Otherwise, all observers are unregistered.
	 */
	public void watchForMessages(final boolean watch) {
		Log.i(TAG, "Notifications being toggled: " + watch);
		// Don't create the thread if we're only going to stop watching
		if (!watch && sNotificationThread == null)
			return;

		ensureHandlerExists();
		// Run this on the message notification handler
		sNotificationHandler.post(new Runnable() {
			@Override
			public void run() {
				if (watch) {
					// otherwise, start new observers for all notified accounts
					registerMessageNotification();
				} else {
					unregisterMessageNotification();

					// tear down the event loop
					sNotificationThread.quit();
					sNotificationThread = null;
				}
			}
		});
	}

	/**
	 * Registers an observer for changes to the INBOX for the given account. Since accounts may only have a single
	 * INBOX, we will never have more than one observer for an account. NOTE: This must be called on the notification
	 * handler thread.
	 * 
	 * @param accountId The ID of the account to register the observer for. May be
	 *            {@link Account#ACCOUNT_ID_COMBINED_VIEW} to register observers for all accounts that allow for user
	 *            notification.
	 */
	private void registerMessageNotification() {
		ContentResolver resolver = mContext.getContentResolver();
		ImogHelper helper = ImogHelper.getInstance();
		List<Uri> uris = helper.getAllUris();
		uris.removeAll(helper.getHiddenUris(mContext));

		for (Uri uri : uris) {
			EntityContentObserver observer = new EntityContentObserver(sNotificationHandler, uri);
			resolver.registerContentObserver(uri, true, observer);
			mNotificationSet.add(observer);
			observer.onChange(true);
		}
	}

	/**
	 * Unregisters the observer for the given account. If the specified account does not have a registered observer, no
	 * action is performed. This will not clear any existing notification for the specified account. Use
	 * {@link NotificationManager#cancel(int)}. NOTE: This must be called on the notification handler thread.
	 * 
	 * @param accountId The ID of the account to unregister from. To unregister all accounts that have observers,
	 *            specify an ID of {@link Account#ACCOUNT_ID_COMBINED_VIEW}.
	 */
	private void unregisterMessageNotification() {
		ContentResolver resolver = mContext.getContentResolver();
		for (ContentObserver observer : mNotificationSet) {
			resolver.unregisterContentObserver(observer);
		}
		mNotificationSet.clear();
	}

	/**
	 * Ensures the notification handler exists and is ready to handle requests.
	 */
	private static synchronized void ensureHandlerExists() {
		if (sNotificationThread == null) {
			sNotificationThread = new NotificationThread();
			sNotificationHandler = new Handler(sNotificationThread.getLooper());
		}
	}

	private static class EntityContentObserver extends ContentObserver {

		private final Uri mUri;

		public EntityContentObserver(Handler handler, Uri uri) {
			super(handler);
			mUri = uri;
		}

		@Override
		public void onChange(boolean selfChange) {
			MessagingNotification.blockingUpdateNewMessageIndicator(sInstance.mContext);
		}

	}

	/**
	 * Thread to handle all notification actions through its own {@link Looper}.
	 */
	private static class NotificationThread implements Runnable {
		/** Lock to ensure proper initialization */
		private final Object mLock = new Object();
		/** The {@link Looper} that handles messages for this thread */
		private Looper mLooper;

		NotificationThread() {
			new Thread(null, this, "EmailNotification").start();
			synchronized (mLock) {
				while (mLooper == null) {
					try {
						mLock.wait();
					} catch (InterruptedException ex) {
					}
				}
			}
		}

		@Override
		public void run() {
			synchronized (mLock) {
				Looper.prepare();
				mLooper = Looper.myLooper();
				mLock.notifyAll();
			}
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
			Looper.loop();
		}

		void quit() {
			mLooper.quit();
		}

		Looper getLooper() {
			return mLooper;
		}
	}
}
