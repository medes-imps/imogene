package org.imogene.android.sync;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.imogene.android.sync.SynchronizationController.Status;

public class GroupSynchronizationListener extends SynchronizationListener {

	private ConcurrentHashMap<SynchronizationListener, Object> mListenersMap = new ConcurrentHashMap<SynchronizationListener, Object>();
	private Set<SynchronizationListener> mListeners = mListenersMap.keySet();

	public GroupSynchronizationListener() {
		super(null);
	}

	public void addListener(SynchronizationListener listener) {
		// we use "this" as a dummy non-null value
		mListenersMap.put(listener, this);
		listener.setRegistered(true);
	}

	public void removeListener(SynchronizationListener listener) {
		mListenersMap.remove(listener);
		listener.setRegistered(false);
	}

	public boolean isActiveListener(SynchronizationListener listener) {
		return mListenersMap.containsKey(listener);
	}

	@Override
	public void onChange(Status status, Object object) {
		for (SynchronizationListener l : mListeners) {
			l.onChange(status, object);
		}
	}

}
