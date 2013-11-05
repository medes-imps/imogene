package org.imogene.android.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

public class ImogBeanObserver extends ContentObserver {

	private final ContentResolver mContentResolver;

	private boolean mRegistered;

	public ImogBeanObserver(Handler handler, Context context) {
		super(handler);
		mContentResolver = context.getContentResolver();
	}

	public void unregister() {
		if (!mRegistered) {
			return;
		}
		mContentResolver.unregisterContentObserver(this);
		mRegistered = true;
	}

	public void register(Uri notifyUri) {
		unregister();
		mContentResolver.registerContentObserver(notifyUri, true, this);
	}

	@Override
	public boolean deliverSelfNotifications() {
		return true;
	}

}