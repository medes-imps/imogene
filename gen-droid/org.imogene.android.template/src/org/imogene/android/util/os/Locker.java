package org.imogene.android.util.os;

import android.os.Handler;

/**
 * Simple locker that is locked for a given amount of time.
 * 
 * @author MEDES-IMPS
 */
public class Locker implements Runnable {

	private final Handler mHandler = new Handler();
	private final long mDelayMillis;

	private boolean mLocked = false;

	public Locker(long delayMillis) {
		mDelayMillis = delayMillis;
	}

	public Locker() {
		this(1000);
	}

	/**
	 * This will lock the locker for one more period. If the locker was already
	 * locked the locker will be locked for a new period of time.
	 */
	public synchronized void lock() {
		mLocked = true;
		mHandler.postDelayed(this, mDelayMillis);
	}

	/** Indicates whether the locker is locked or not.
	 * 
	 * @return The state of the locker.
	 */
	public synchronized boolean isLocked() {
		return mLocked;
	}

	@Override
	public synchronized void run() {
		mLocked = false;
	}

	/**
	 * Unlock the locker.
	 */
	public synchronized void cancel() {
		mHandler.removeCallbacks(this);
		mLocked = false;
	}

}
