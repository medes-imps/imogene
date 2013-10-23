package org.imogene.android.maps.preference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.maps.database.PreCache;
import org.imogene.android.template.R;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceManager.OnActivityDestroyListener;
import android.util.AttributeSet;

public class PrecachedMapAreasPreference extends Preference implements OnActivityDestroyListener {

	public PrecachedMapAreasPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PrecachedMapAreasPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
		super.onAttachedToHierarchy(preferenceManager);

		try {
			Method method = PreferenceManager.class.getDeclaredMethod("registerOnActivityDestroyListener",
					OnActivityDestroyListener.class);
			method.setAccessible(true);
			method.invoke(preferenceManager, this);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onAttachedToActivity() {
		super.onAttachedToActivity();
		getContext().getContentResolver().registerContentObserver(PreCache.Columns.CONTENT_URI, true, mContentObserver);
	}

	@Override
	public void onActivityDestroy() {
		getContext().getContentResolver().unregisterContentObserver(mContentObserver);
	}

	@Override
	public CharSequence getSummary() {
		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(PreCache.Columns.TABLE_NAME);
		builder.setCountOf(true);
		int count = (int) builder.queryForLong();
		return getContext().getResources().getQuantityString(R.plurals.ig_precache_area_summary, count, count);
	}

	private final Handler mHandler = new Handler();

	private final ContentObserver mContentObserver = new ContentObserver(mHandler) {

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			notifyChanged();
		}
	};
}
