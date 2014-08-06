package org.imogene.android.database;

import java.util.HashMap;

import org.imogene.android.common.entity.ImogBean;

public class DatabaseCache {

	private static DatabaseCache sInstance;

	public static DatabaseCache getInstance() {
		if (sInstance == null) {
			sInstance = new DatabaseCache();
		}
		return sInstance;
	}

	private ThreadLocal<HashMap<String, ImogBean>> cachedBeans = new ThreadLocal<HashMap<String, ImogBean>>();
	private ThreadLocal<Counter> openTransactions = new ThreadLocal<Counter>();

	private DatabaseCache() {
	}

	public boolean contains(String id) {
		return cachedBeans.get() != null && cachedBeans.get().containsKey(id);
	}

	public <T extends ImogBean> T get(String id) {
		if (cachedBeans.get() == null) {
			return null;
		}
		return (T) cachedBeans.get().get(id);
	}

	public void put(ImogBean bean) {
		if (cachedBeans.get() == null) {
			HashMap<String, ImogBean> map = new HashMap<String, ImogBean>();
			cachedBeans.set(map);
		}
		cachedBeans.get().put(bean.getId(), bean);
	}

	public void beginTransaction() {
		Counter counter = openTransactions.get();
		if (counter == null) {
			counter = new Counter();
			openTransactions.set(counter);
		}
		counter.counter++;
	}

	public void endTransaction() {
		if (openTransactions.get() != null) {
			Counter counter = openTransactions.get();
			counter.counter--;
			if (counter.counter == 0) {
				clearCache();
			}
		}
	}

	public void clearCache() {
		if (cachedBeans.get() != null) {
			cachedBeans.get().clear();
		}
	}

	private static class Counter {
		int counter = 0;
	}

}
