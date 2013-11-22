package org.imogene.android.sync;

import org.imogene.android.sync.SynchronizationController.Status;

import android.os.Handler;

/**
 * Receives call backs for synchronization {@link Status}. May be added using
 * {@link SynchronizationController#registerSynchronizationObserver(SynchronizationObserver).}
 */
public abstract class SynchronizationListener {

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
			SynchronizationListener.this.onChange(mStatus, mObject);
		}
	}

	public SynchronizationListener(Handler handler) {
		mHandler = handler;
	}

	/**
	 * Set the registered flag of this observer.
	 * 
	 * @param registered whether it registered or not.
	 */
	public final synchronized void setRegistered(boolean registered) {
		mRegistered = registered;
	}

	/**
	 * Indicate whether this observer has already been registered.
	 * 
	 * @return {@code true} if it has been registered, {@code false} otherwise.
	 */
	public final synchronized boolean isRegistered() {
		return mRegistered;
	}

	/**
	 * This method is called when the {@link Status} of the synchronization changed.
	 * 
	 * @param status The new {@link Status} of the synchronization.
	 * @param object An object that can be passed to the observer.
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