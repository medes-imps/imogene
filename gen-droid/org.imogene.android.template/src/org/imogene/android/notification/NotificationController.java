package org.imogene.android.notification;

import java.util.HashSet;
import java.util.List;

import org.imogene.android.Constants.Extras;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.domain.ImogHelper;
import org.imogene.android.domain.ImogHelper.EntityInfo;
import org.imogene.android.domain.ImogHelper.ImogBeanCallback;
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

public class NotificationController {

	private static final String TAG = NotificationController.class.getName();

	/**
	 * Minimum interval between notification sounds. Since a long sync (either on account setup or after a long period
	 * of being offline) can cause several notifications consecutively, it can be pretty overwhelming to get a barrage
	 * of notification sounds. Throttle them using this value.
	 */
	private static final long MIN_SOUND_INTERVAL_MS = 15 * 1000; // 15 seconds

	private static NotificationController sInstance;
	private static NotificationThread sNotificationThread;
	private static Handler sNotificationHandler;
	private final NotificationManager mNotificationManager;
	private final AudioManager mAudioManager;
	private final Context mContext;
	private final HashSet<ContentObserver> mNotificationSet;

	/**
	 * Timestamp indicating when the last message notification sound was played. Used for throttling.
	 */
	private long mLastMessageNotifyTime;

	/** Singleton access */
	public static synchronized NotificationController getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new NotificationController(context);
		}
		return sInstance;
	}
	
	public static void cancelNotification(int notificationId) {
		sInstance.mNotificationManager.cancel(notificationId);
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
		final ContentResolver resolver = mContext.getContentResolver();
		final List<Uri> hidden = ImogHelper.getInstance().getHiddenUris(mContext);
		ImogHelper.getInstance().doWithImogBeans(new ImogBeanCallback() {

			@Override
			public void doWith(Class<? extends ImogBean> clazz, Uri uri) {
				if (!hidden.contains(uri)) {
					EntityContentObserver observer = new EntityContentObserver(sNotificationHandler, ImogHelper
							.getEntityInfo(clazz));
					resolver.registerContentObserver(uri, true, observer);
					mNotificationSet.add(observer);
					observer.onChange(true);
				}

			}
		});
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

		// TODO set up delete intent
		// Intent intent = new Intent(context, OnDeletedReceiver.class);
		// notification.deleteIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		mLastMessageNotifyTime = now;
		return notification;
	}

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

	private static class EntityContentObserver extends ContentObserver {

		private final EntityInfo mInfo;

		public EntityContentObserver(Handler handler, EntityInfo info) {
			super(handler);
			mInfo = info;
		}

		@Override
		public void onChange(boolean selfChange) {
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(mInfo.contentUri);
			builder.where().eq(ImogBean.Columns.UNREAD, 1);
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
					intent.putExtra(Extras.EXTRA_SORT_KEY, ImogBean.Columns.UNREAD);
					intent.putExtra(Extras.EXTRA_ASCENDING, false);
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
