package org.imogene.android.util;

import java.util.Stack;

public class IamLost {

	private static final Stack<String> mLocations = new Stack<String>();

	private static IamLost mInstance = null;

	protected IamLost() {
		// Exists only to defeat instantiation.
	}

	public static IamLost getInstance() {
		if (mInstance == null) {
			mInstance = new IamLost();
		}
		return mInstance;
	}

	public void add(String location) {
		mLocations.push(location);
	}

	public void remove() {
		mLocations.pop();
	}

	public String[] get() {
		String[] result = new String[mLocations.size()];
		return mLocations.toArray(result);
	}
}
