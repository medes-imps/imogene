package org.imogene.android.domain;

import java.util.List;

import android.content.Context;
import android.net.Uri;

/**
 * Application helper for {@link ImogBean} entities.
 * <p>
 * Note that the helper is a singleton which can be accessed using
 * {@link ImogHelper#getInstance()}.
 * 
 * @author Medes-IMPS
 * 
 */
public abstract class ImogHelper {

	protected static volatile ImogHelper sInstance;

	/**
	 * Returns the current instance of the {@link ImogHelper}
	 * 
	 * @return The {@code ImogHelper} current instance.
	 */
	public static ImogHelper getInstance() {
		if (sInstance == null) {
			throw new IllegalArgumentException("ImogHelper has not been instantiated");
		}
		return sInstance;
	}

	/**
	 * Callback interface invoked on each {@link ImogBean} class in the
	 * application.
	 * 
	 * @author Medes-IMPS
	 * 
	 */
	public static interface ImogBeanCallback {
		/**
		 * Perform an operation using the given Class and Uri.
		 * 
		 * @param clazz The Class to operate on.
		 * @param uri The uri to operate on.
		 */
		public void doWith(Class<? extends ImogBean> clazz, Uri uri);
	}

	/**
	 * Obtain a {@link List} of {@link ImogBean} uris of the whole application.
	 * 
	 * @return The list of uris.
	 */
	public abstract List<Uri> getAllUris();

	/**
	 * Obtain a {@link List} of {@link ImogBean} uris not directly accessibles
	 * by the current user.
	 * 
	 * @param context The current application context.
	 * @return The list of hidden uris.
	 */
	public abstract List<Uri> getHiddenUris(Context context);

	/**
	 * Obtain a {@link List} of the {@link ImogBean} tables in the database.
	 * 
	 * @return The list of tables.
	 */
	public abstract List<String> getAllTables();

	/**
	 * Invoke the given callback on all {@link ImogBean}s of the application.
	 * 
	 * @param callback The callback to invoke for each ImogBean.
	 */
	public abstract void doWithImogBeans(ImogBeanCallback callback);

}
