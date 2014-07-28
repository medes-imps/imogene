package org.imogene.android.widget.field;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Field manager that dispatches activity events to the field views.
 */
public class FieldManager {

	private final FieldContainer mContainer;

	private int mNextId = 0;

	private ArrayList<OnActivityDestroyListener> mActivityDestroyListeners;

	private ArrayList<OnActivityResultListener> mActivityResultListeners;

	/**
	 * Constructor for this manager.
	 * 
	 * @param container The field container.
	 * @param firstRequestCode The first value of the activity launcher request code.
	 */
	public FieldManager(FieldContainer container, int firstRequestCode) {
		mContainer = container;
		mNextId = firstRequestCode;
	}

	/**
	 * Returns the activity containing the {@link FieldContainer}.
	 * 
	 * @return The Activity containing the {@link FieldContainer}.
	 */
	public Activity getActivity() {
		return mContainer.getActivity();
	}

	/**
	 * Return the content URI of the entity managed by the {@link FieldContainer}. This method temporally saves the
	 * entity if there is no content URI.
	 * 
	 * @return The content URI of the entity.
	 */
	public Uri getUri() {
		return mContainer.getUri();
	}

	/**
	 * Returns the entity identifier managed by the {@link FieldContainer}. This method temporally saves the entity if
	 * there is no identifier.
	 * 
	 * @return The entity identifier.
	 */
	public String getId() {
		return mContainer.getId();
	}

	/**
	 * The basic content URI representing the entity.
	 * 
	 * @return The content URI.
	 */
	public Uri getContentUri() {
		return mContainer.getContentUri();
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

	/**
	 * Interface to define a field container that will make use of a field manager.
	 */
	public static interface FieldContainer extends RelationManager {

		/**
		 * Returns the {@link Activity} using the {@link FieldManager}.
		 * 
		 * @return The {@link Activity}.
		 */
		public Activity getActivity();

	}

	/**
	 * Interface to define a container able to manipulate relation fields of an entity.
	 */
	public static interface RelationManager {

		/**
		 * Return the content URI of the entity managed by the {@link FieldContainer}. This method temporally saves the
		 * entity if there is no content URI.
		 * 
		 * @return The content URI of the entity.
		 */
		public Uri getUri();

		/**
		 * Returns the entity identifier managed by the {@link FieldContainer}. This method temporally saves the entity
		 * if there is no identifier.
		 * 
		 * @return The entity identifier.
		 */
		public String getId();

		/**
		 * The basic content URI representing the entity.
		 * 
		 * @return The content URI.
		 */
		public Uri getContentUri();
	}

}
