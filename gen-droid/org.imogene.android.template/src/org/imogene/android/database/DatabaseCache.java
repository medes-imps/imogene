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

	private HashMap<String, ImogBean> cachedBeans = new HashMap<String, ImogBean>();

	private DatabaseCache() {
	}

	public boolean contains(String id) {
		return cachedBeans.containsKey(id);
	}

	public <T extends ImogBean> T get(String id) {
		return (T) cachedBeans.get(id);
	}

	public void put(ImogBean bean) {
		cachedBeans.put(bean.getId(), bean);
	}

}
