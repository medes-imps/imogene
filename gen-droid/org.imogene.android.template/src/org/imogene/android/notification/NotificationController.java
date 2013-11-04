package org.imogene.android.notification;

import java.util.List;

import org.imogene.android.Constants;
import org.imogene.android.Constants.Extras;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.domain.ImogHelper;
import org.imogene.android.domain.ImogHelper.EntityInfo;
import org.imogene.android.domain.ImogHelper.ImogBeanCallback;
import org.imogene.android.sync.SynchronizationController;
import org.imogene.android.sync.SynchronizationController.Status;
import org.imogene.android.sync.SynchronizationObserver;
import org.imogene.android.template.R;
import org.imogene.android.util.content.ContentUrisUtils;

import android.accounts.Account;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.SparseArray;

public class NotificationController {

	private static final String TAG = NotificationController.class.getName();

	/**
	 * Pseudo notification ID to represent "no notification". This may be used any time the notification ID may not be
	 * known or when we want to specifically select "no" account.
	 */
	public static final int NO_NOTIFICATION = -1;

	/**
	 * Minimum interval between notification sounds. Since a long sync (after a long period of being offline) can cause
	 * several notifications consecutively, it can be pretty overwhelming to get a barrage of notification sounds.
	 * Throttle them using this value.
	 */
	private static final long MIN_SOUND_INTERVAL_MS = 15 * 1000; // 15 seconds

	private static NotificationController sInstance;
	private static NotificationThread sNotificationThread;
	private static Handler sNotificationHandler;
	private final NotificationManager mNotificationManager;
	private final AudioManager mAudioManager;
	private final Context mContext;
	private final SparseArray<ContentObserver> mNotificationMap;
	private MySynchronizationObserver mSynchronizationObserver;
	/**
	 * Suspend notifications for . If {@link Account#NO_ACCOUNT}, no account notifications are suspended. If
	 * {@link Account#ACCOUNT_ID_COMBINED_VIEW}, notifications for all accounts are suspended.
	 */
	private long mSuspendNotificationId = NO_NOTIFICATION;

	/**
	 * Timestamp indicating when the last notification sound was played. Used for throttling.
	 */
	private long mLastMessageNotifyTime;

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
		mNotificationMap = new SparseArray<ContentObserver>();
	}

	/**
	 * Tells the notification controller if it should be watching for changes to the entities table. This is the main
	 * life cycle method for entities notifications. When we start observing database changes, we check the entities
	 * state.
	 * 
	 * @param watch If {@code true}, we register observers. Otherwise, all observers are unregistered.
	 */
	public void watchForMessages(final boolean watch) {
		if (Constants.DEBUG) {
			Log.i(TAG, "Notifications being toggled: " + watch);
		}
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
	 * Registers the observers for changes. NOTE: This must be called on the notification handler thread.
	 */
	private void registerMessageNotification() {
		final ContentResolver resolver = mContext.getContentResolver();
		final List<Uri> hidden = ImogHelper.getInstance().getHiddenUris(mContext);
		ImogHelper.getInstance().doWithImogBeans(new ImogBeanCallback() {

			@Override
			public void doWith(Class<? extends ImogBean> clazz, Uri uri) {
				EntityInfo info = ImogHelper.getEntityInfo(clazz);
				if (info == null) {
					// Not an observable entity
					return;
				}
				ContentObserver obs = mNotificationMap.get(info.notificationId);
				if (hidden.contains(uri)) {
					if (obs != null) {
						resolver.unregisterContentObserver(obs);
						mNotificationMap.delete(info.notificationId);
					}
				} else {
					if (obs != null) {
						// we're already observing; nothing to do
						return;
					}
					EntityContentObserver observer = new EntityContentObserver(sNotificationHandler, info);
					resolver.registerContentObserver(uri, true, observer);
					mNotificationMap.put(info.notificationId, observer);
					observer.onChange(true);
				}

			}
		});

		if (mSynchronizationObserver == null) {
			mSynchronizationObserver = new MySynchronizationObserver(sNotificationHandler);
		}
		if (!mSynchronizationObserver.isRegistered()) {
			SynchronizationController.getInstance(mContext).registerSynchronizationObserver(mSynchronizationObserver);
		}
	}

	/**
	 * Temporarily suspend from receiving notifications. NOTE: only a single notification may ever be suspended at a
	 * time. So, if this method is invoked a second time, notifications for the previously suspended account will
	 * automatically be re-activated.
	 * 
	 * @param suspend If {@code true}, suspend notifications for the given entity notification identifier. Otherwise,
	 *        re-activate notifications for the previously suspended entity notifications.
	 * @param notificationId The ID of the notification. If this is the special notification ID
	 *        {@link NotificationController#NO_NOTIFICATION}, notifications for are re-activated. If {@code suspend} is
	 *        {@code false}, the notification ID is ignored.
	 */
	public void suspendMessageNotification(boolean suspend, int notificationId) {
		if (mSuspendNotificationId != NO_NOTIFICATION) {
			// we're already suspending an account; un-suspend it
			mSuspendNotificationId = NO_NOTIFICATION;
		}
		if (suspend && notificationId != NO_NOTIFICATION && notificationId > 0) {
			mSuspendNotificationId = notificationId;
			mNotificationManager.cancel(notificationId);
		}
	}

	/**
	 * Unregisters the observers. NOTE: This must be called on the notification handler thread.
	 */
	private void unregisterMessageNotification() {
		ContentResolver resolver = mContext.getContentResolver();
		for (int i = 0; i < mNotificationMap.size(); i++) {
			resolver.unregisterContentObserver(mNotificationMap.valueAt(i));
		}
		mNotificationMap.clear();
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

	/**
	 * Returns a {@link Notification} that resumes the informations about the entity and ready to be post in the status
	 * bar.
	 * 
	 * @param title the notification title
	 * @param contentText the notification main text
	 * @param ticker the ticker text displayed in the status bar
	 * @param intent the intent when notification is clicked
	 * @param iconRes the icon resource identifier
	 * @return The built notification
	 */
	private Notification createNotification(CharSequence title, CharSequence contentText, CharSequence ticker,
			Intent intent, int iconRes) {
		Notification notification = new Notification(iconRes, ticker, System.currentTimeMillis());

		// Make a startActivity() PendingIntent for the notification.
		PendingIntent pendingIntent = null;
		if (intent != null) {
			pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		}

		// Update the notification.
		notification.setLatestEventInfo(mContext, title, contentText, pendingIntent);

		long now = System.currentTimeMillis();
		boolean enableAudio = (now - mLastMessageNotifyTime) > MIN_SOUND_INTERVAL_MS;
		if (enableAudio) {
			setupSoundAndVibration(notification);
		}

		mLastMessageNotifyTime = now;
		return notification;
	}

	/**
	 * Sets up the notification's sound and vibration.
	 * 
	 * @param notification the notification to update
	 */
	private void setupSoundAndVibration(Notification notification) {
		boolean vibrateAlways = true;
		boolean vibrateSilent = false;

		boolean nowSilent = mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE;

		if (vibrateAlways || vibrateSilent && nowSilent) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}

		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
	}

	/**
	 * Convenient method to create a ticker message from the title and the content text.
	 * 
	 * @param header the title text
	 * @param body the content text
	 * @return a resume of both parameters
	 */
	private static CharSequence buildTickerMessage(String header, String body) {
		StringBuilder buf = new StringBuilder(header.replace('\n', ' ').replace('\r', ' '));
		buf.append(':').append(' ');

		int offset = buf.length();
		if (!TextUtils.isEmpty(body)) {
			body = body.replace('\n', ' ').replace('\r', ' ');
			buf.append(body);
		}

		SpannableString spanText = new SpannableString(buf.toString());
		spanText.setSpan(new StyleSpan(Typeface.BOLD), 0, offset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		return spanText;
	}

	/**
	 * Observer invoked whenever an entity we're notifying the user about changes.
	 */
	private static class EntityContentObserver extends ContentObserver {

		private final EntityInfo mInfo;

		public EntityContentObserver(Handler handler, EntityInfo info) {
			super(handler);
			mInfo = info;
		}

		@Override
		public void onChange(boolean selfChange) {
			if (mInfo.notificationId == sInstance.mSuspendNotificationId) {
				return;
			}
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(mInfo.contentUri);
			builder.where().eq(ImogBean.Columns.FLAG_READ, 0);
			builder.orderBy(ImogBean.Columns.MODIFIED, false);
			ImogBeanCursor c = (ImogBeanCursor) builder.query();
			if (c == null) {
				return;
			}
			try {
				if (!c.moveToFirst()) {
					sInstance.mNotificationManager.cancel(mInfo.notificationId);
					return;
				}

				int count = c.getCount();

				Intent intent = new Intent(Intent.ACTION_VIEW);
				if (count > 1) {
					intent.setData(mInfo.contentUri);
					intent.putExtra(Extras.EXTRA_SORT_KEY, ImogBean.Columns.FLAG_READ);
					intent.putExtra(Extras.EXTRA_ASCENDING, true);
				} else {
					intent.setData(ContentUrisUtils.withAppendedId(mInfo.contentUri, c.getId()));
				}

				String title = sInstance.mContext.getString(mInfo.description_sg);
				String description = sInstance.mContext.getResources().getQuantityString(R.plurals.ig_numberOfEntities,
						count, count);
				CharSequence ticker = buildTickerMessage(title, description);

				Notification n = sInstance.createNotification(title, description, ticker, intent, mInfo.drawable);

				if (n != null) {
					sInstance.mNotificationManager.notify(mInfo.notificationId, n);
				}
			} finally {
				c.close();
			}
		}

	}

	private static class MySynchronizationObserver extends SynchronizationObserver {

		private static final int NOTIFICATION_STATUS_ID = 1111;

		public MySynchronizationObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(Status status, Object object) {
			if (status == Status.START) {
				String ticker = sInstance.mContext.getString(R.string.ig_notification_sync_ticker);
				String title = sInstance.mContext.getString(R.string.ig_notification_sync_title);
				Notification n = sInstance.createNotification(title, ticker, ticker, new Intent(),
						R.drawable.ig_logo_android_s);
				n.flags |= Notification.FLAG_NO_CLEAR;
				n.defaults &= ~Notification.DEFAULT_VIBRATE;

				sInstance.mNotificationManager.notify(NOTIFICATION_STATUS_ID, n);
			} else if (status == Status.FINISH) {
				sInstance.mNotificationManager.cancel(NOTIFICATION_STATUS_ID);
			}
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
			new Thread(null, this, "SyncNotification").start();
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
