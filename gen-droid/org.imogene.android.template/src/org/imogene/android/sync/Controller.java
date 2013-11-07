package org.imogene.android.sync;

import android.content.Context;
import android.os.AsyncTask;

public class Controller {

	private static Controller sInstance;

	public synchronized static Controller getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new Controller(context);
		}
		return sInstance;
	}

	private final SynchronizationController mLegacyController;

	private Controller(Context context) {
		mLegacyController = SynchronizationController.getInstance(context);
	}

	public void serviceSynchronize() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				mLegacyController.synchronize();
				return null;
			}
		}.execute();
	}

}
