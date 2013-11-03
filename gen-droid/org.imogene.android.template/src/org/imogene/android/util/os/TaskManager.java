package org.imogene.android.util.os;

import android.util.SparseArray;

public class TaskManager<Callback> {

	private SparseArray<BaseAsyncTask<Callback, ?, ?, ?>> mTasks;
	private Callback mCallback;

	public <Params> void execute(int key, BaseAsyncTask<Callback, Params, ?, ?> task, Params... params) {
		ensureTaskMap();
		BaseAsyncTask<Callback, ?, ?, ?> oldTask = mTasks.get(key);
		if (oldTask != null && !oldTask.isFinished()) {
			return;
		}
		mTasks.put(key, task);
		task.setCallback(mCallback);
		task.execute(params);
	}

	public void attach(Callback callback) {
		mCallback = callback;
		if (mTasks != null) {
			for (int i = 0; i < mTasks.size(); i++) {
				mTasks.valueAt(i).setCallback(callback);
				
			}
		}
	}

	public void detach() {
		attach(null);
	}

	private void ensureTaskMap() {
		if (mTasks == null) {
			mTasks = new SparseArray<BaseAsyncTask<Callback,?,?,?>>();
		}
	}

}
