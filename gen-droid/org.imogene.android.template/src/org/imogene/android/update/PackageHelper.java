package org.imogene.android.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageHelper {
	
	public enum State {
		NOT_INSTALLED,
		INSTALLED,
		UPDATE_AVAILABLE;
	}

	public static State getPackageState(Context context, String packageName, int versionCode) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(packageName, 0);
			if (info.versionCode < versionCode) {
				return State.UPDATE_AVAILABLE;
			} else {
				return State.INSTALLED;
			}
		} catch (NameNotFoundException e) {
			return State.NOT_INSTALLED;
		}
	}
}
