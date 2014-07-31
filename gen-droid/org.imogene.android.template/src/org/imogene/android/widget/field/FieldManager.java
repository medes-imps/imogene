package org.imogene.android.widget.field;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;

/**
 * Field manager that dispatches activity events to the field views.
 */
public class FieldManager {

	private final Activity mActivity;

	private int mNextId = 0;

	private ArrayList<OnActivityDestroyListener> mActivityDestroyListeners;

	private ArrayList<OnActivityResultListener> mActivityResultListeners;

	/**
	 * Constructor for this manager.
	 * 
	 * @param activity The parent activity.
	 * @param firstRequestCode The first value of the activity launcher request code.
	 */
	public FieldManager(Activity activity, int firstRequestCode) {
		mActivity = activity;
		mNextId = firstRequestCode;
	}

	/**
	 * Returns the activity containing the {@link FieldContainer}.
	 * 
	 * @return The Activity containing the {@link FieldContainer}.
	 */
	public Activity getActivity() {
		return mActivity;
	}

	/**
	 * Obtain a new unique request code to start an activity and receive a result using
	 * {@link Activity#startActivityForResult(Intent, int)}.
	 * 
	 * @return The next request code identifier.
	 */
	public int getNextId() {
		return mNextId++;
	}

	/**
	 * Register a @link {@link OnActivityDestroyListener} to be called when the {@link Activity} using this
	 * {@link FieldManager} is destroyed.
	 * 
	 * @param listener The {@link OnActivityDestroyListener} to register.
	 */
	public void registerOnActivityDestroyListener(OnActivityDestroyListener listener) {
		if (mActivityDestroyListeners == null) {
			mActivityDestroyListeners = new ArrayList<OnActivityDestroyListener>();
		}
		if (!mActivityDestroyListeners.contains(listener)) {
			mActivityDestroyListeners.add(listener);
		}
	}

	/**
	 * Unregister a previously registered {@link OnActivityDestroyListener}.
	 * 
	 * @param listener The {@link OnActivityDestroyListener} to unregister.
	 */
	public void unregisterOnActivityDestroyListener(OnActivityDestroyListener listener) {
		if (mActivityDestroyListeners != null) {
			mActivityDestroyListeners.remove(listener);
		}
	}

	/**
	 * Method to be called when the {@link Activity} using this {@link FieldManager} is destroyed.
	 */
	public void dispatchActivityDestroy() {
		ArrayList<OnActivityDestroyListener> list = null;
		if (mActivityDestroyListeners != null) {
			list = new ArrayList<OnActivityDestroyListener>(mActivityDestroyListeners);
		}

		if (list != null) {
			final int size = list.size();
			for (int i = 0; i < size; i++) {
				list.get(i).onActivityDestroy();
			}
		}
	}

	/**
	 * Register a @link {@link OnActivityResultListener} to be called when an {@link Activity} launched with the method
	 * {@link Activity#startActivityForResult(Intent, int)} returns.
	 * 
	 * @param listener The {@link OnActivityResultListener} to register.
	 */
	public void registerOnActivityResultListener(OnActivityResultListener listener) {
		if (mActivityResultListeners == null) {
			mActivityResultListeners = new ArrayList<OnActivityResultListener>();
		}
		if (!mActivityResultListeners.contains(listener)) {
			mActivityResultListeners.add(listener);
		}
	}

	/**
	 * Unregister a previously registered {@link OnActivityResultListener}.
	 * 
	 * @param listener The {@link OnActivityResultListener} to unregister.
	 */
	public void unregisterOnActivityResultListener(OnActivityResultListener listener) {
		if (mActivityResultListeners != null) {
			mActivityResultListeners.remove(listener);
		}
	}

	/**
	 * Method to be called when an {@link Activity} launched using {@link Activity#startActivityForResult(Intent, int)}
	 * returns a result.
	 * 
	 * @param requestCode The integer request code originally supplied to
	 *            {@link Activity#startActivityForResult(Intent, int)}, allowing you to identify who this result came
	 *            from.
	 * @param resultCode The integer result code returned by the child activity through its setResult().
	 * @param data An {@link Intent}, which can return result data to the caller (various data can be attached to
	 *            {@link Intent} "extras").
	 */
	public void dispatchActivityResult(int requestCode, int resultCode, Intent data) {
		ArrayList<OnActivityResultListener> list = null;
		if (mActivityResultListeners != null) {
			list = new ArrayList<OnActivityResultListener>(mActivityResultListeners);
		}

		if (list != null) {
			final int size = list.size();
			for (int i = 0; i < size; i++) {
				if (list.get(i).onActivityResult(requestCode, resultCode, data)) {
					break;
				}
			}
		}
	}

	/**
	 * Interface to define an {@link Activity} result listener to be invoked when an {@link Activity} launched using
	 * {@link Activity#startActivityForResult(Intent, int)} returns.
	 */
	public static interface OnActivityResultListener {
		/**
		 * This method is called when an {@link Activity} launched using
		 * {@link Activity#startActivityForResult(Intent, int)} is returning. Once a listener has consumed the result
		 * the other listeners won't receive the event.
		 * 
		 * @param requestCode The integer request code originally supplied to
		 *            {@link Activity#startActivityForResult(Intent, int)}, allowing you to identify who this result
		 *            came from.
		 * @param resultCode The integer result code returned by the child activity through its setResult().
		 * @param data An {@link Intent}, which can return result data to the caller (various data can be attached to
		 *            {@link Intent} "extras").
		 * @return Whether the result has been consumed or not.
		 */
		public boolean onActivityResult(int requestCode, int resultCode, Intent data);
	}

	/**
	 * Interface to define an {@link Activity} destroy listener to be invoked when an {@link Activity} is destroyed.
	 */
	public static interface OnActivityDestroyListener {
		/**
		 * This method is called when an {@link Activity} is destroyed.
		 */
		public void onActivityDestroy();
	}

}
