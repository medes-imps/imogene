package org.imogene.android.sync;

import org.imogene.android.sync.SynchronizationController.Status;

import android.os.Handler;

public abstract class SynchronizationObserver {

	private volatile boolean mRegistered;
	private Handler mHandler;

	private final class NotificationRunnable implements Runnable {

		private Status mStatus;
		private Object mObject;

		public NotificationRunnable(Status status, Object object) {
			mStatus = status;
		}

		@Override
		public void run() {
			SynchronizationObserver.this.onChange(mStatus, mObject);
		}
	}

	public SynchronizationObserver(Handler handler) {
		mHandler = handler;
	}

	public final void setRegistered(boolean registered) {
		mRegistered = registered;
	}

	public final boolean isRegistered() {
		return mRegistered;
	}

	/**
	 * This method is called when the {@link Status} of the synchronization changed.
	 * 
	 * @param status The new {@link Status} of the synchronization.
	 */
	public void onChange(Status status, Object object) {
	}

	public final void dispatchChange(Status status, Object object) {
		if (mHandler == null) {
			onChange(status, object);
		} else {
			mHandler.post(new NotificationRunnable(status, object));
		}
	}

}