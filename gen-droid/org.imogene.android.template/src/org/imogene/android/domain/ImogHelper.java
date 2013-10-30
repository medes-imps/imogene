package org.imogene.android.domain;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Application helper for {@link ImogBean} entities.
 * <p>
 * Note that the helper is a singleton which can be accessed using {@link ImogHelper#getInstance()}.
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
	 * Callback interface invoked on each {@link ImogBean} class in the application.
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
	 * Obtain a {@link List} of {@link ImogBean} uris not directly accessibles by the current user.
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

	public static EntityInfo getEntityInfo(Class<? extends ImogBean> clazz) {
		Class<?>[] classes = clazz.getDeclaredClasses();
		if (classes != null && classes.length > 0) {
			for (Class<?> c : classes) {
				if (c.getName().equals("Columns")) {
					try {
						Drawable color = (Drawable) c.getDeclaredField("COLOR").get(null);
						String table = (String) c.getDeclaredField("TABLE_NAME").get(null);
						String type = (String) c.getDeclaredField("BEAN_TYPE").get(null);
						Uri uri = (Uri) c.getDeclaredField("CONTENT_URI").get(null);
						int desc_sg = (Integer) c.getDeclaredField("DESC_SINGULAR").get(null);
						int desc_pl = (Integer) c.getDeclaredField("DESC_PLURAL").get(null);
						int drawable = (Integer) c.getDeclaredField("DRAWABLE").get(null);
						int notifId = (Integer) c.getDeclaredField("NOTIFICATION_ID").get(null);
						return new EntityInfo(uri, table, type, desc_sg, desc_pl, drawable, color, notifId);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public static class EntityInfo {

		public final Uri contentUri;
		public final String table;
		public final String type;
		public final int description_sg;
		public final int description_pl;
		public final int drawable;
		public final Drawable color;
		public final int notificationId;

		public EntityInfo(Uri contentUri, String table, String type, int description_sg, int description_pl,
				int drawable, Drawable color, int notificationId) {
			this.contentUri = contentUri;
			this.table = table;
			this.type = type;
			this.description_sg = description_sg;
			this.description_pl = description_pl;
			this.drawable = drawable;
			this.color = color;
			this.notificationId = notificationId;
		}
	}

}
