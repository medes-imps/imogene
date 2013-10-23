package org.imogene.android.preference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.imogene.android.app.OffsetActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceManager.OnActivityDestroyListener;
import android.util.AttributeSet;

public class SntpOffsetPreference extends Preference implements OnActivityDestroyListener {

	public SntpOffsetPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
		super.onAttachedToHierarchy(preferenceManager);
		
		try {
			Method method = PreferenceManager.class.getDeclaredMethod("registerOnActivityDestroyListener", OnActivityDestroyListener.class);
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
		getSharedPreferences().registerOnSharedPreferenceChangeListener(mListener);
	}
	
	@Override
	public void onActivityDestroy() {
		getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mListener);
	}
	
	@Override
	public CharSequence getSummary() {
		return Long.toString(getPersistedLong(0));
	}
	
	@Override
	protected void onClick() {
		Context context = getContext();
		context.startActivity(new Intent(context, OffsetActivity.class));
	}
	
	private final OnSharedPreferenceChangeListener mListener = new OnSharedPreferenceChangeListener() {
		
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			if (key.equals(getKey())) {
				notifyChanged();
			}
		}
	};
	
}
