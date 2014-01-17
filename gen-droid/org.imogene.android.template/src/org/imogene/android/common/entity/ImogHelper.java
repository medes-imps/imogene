package org.imogene.android.common.entity;

import java.util.List;

import org.imogene.android.util.annotation.ReflectionUtils;

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

	/**
	 * Return {@link EntityInfo} for an {@link ImogBean} class. This class should contains an {@link ImogBean.Columns}
	 * class with the correct fields.
	 * 
	 * @param clazz the class to retrieve information
	 * @return The {@link EntityInfo} of the given class if it is representable, {@code null} otherwise
	 */
	public static EntityInfo getEntityInfo(Class<? extends ImogBean> clazz) {
		Class<?> columnsClass = ReflectionUtils.getClass(clazz, "Columns");
		if (columnsClass != null) {
			try {
				Drawable color = (Drawable) ReflectionUtils.getField(columnsClass, "COLOR").get(null);
				String table = (String) ReflectionUtils.getField(columnsClass, "TABLE_NAME").get(null);
				String type = (String) ReflectionUtils.getField(columnsClass, "BEAN_TYPE").get(null);
				Uri uri = (Uri) ReflectionUtils.getField(columnsClass, "CONTENT_URI").get(null);
				int desc_sg = (Integer) ReflectionUtils.getField(columnsClass, "DESC_SINGULAR").get(null);
				int desc_pl = (Integer) ReflectionUtils.getField(columnsClass, "DESC_PLURAL").get(null);
				int drawable = (Integer) ReflectionUtils.getField(columnsClass, "DRAWABLE").get(null);
				int notifId = (Integer) ReflectionUtils.getField(columnsClass, "NOTIFICATION_ID").get(null);
				return new EntityInfo(uri, table, type, desc_sg, desc_pl, drawable, color, notifId);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (NoSuchFieldException e) {
			}
		}
		// If we arrive here this means that the entity is not representable
		return null;
	}

	/**
	 * Resume the static informations about an {@link ImogBean} class that is representable to the user.
	 */
	public static class EntityInfo {

		/**
		 * The content {@link Uri}
		 */
		public final Uri contentUri;

		/**
		 * The table name
		 */
		public final String table;

		/**
		 * The {@link ImogBean} type
		 */
		public final String type;

		/**
		 * The singular description resource identifier
		 */
		public final int description_sg;

		/**
		 * The plural description resource identifier
		 */
		public final int description_pl;

		/**
		 * The icon drawable resource identifier
		 */
		public final int drawable;

		/**
		 * The {@link Drawable} color
		 */
		public final Drawable color;

		/**
		 * The notification identifier
		 */
		public final int notificationId;

		/**
		 * Default constructor.
		 * 
		 * @param contentUri the content {@link Uri}
		 * @param table the table name
		 * @param type the {@link ImogBean} type
		 * @param description_sg the singular description resource identifier
		 * @param description_pl the plural description resource identifier
		 * @param drawable the icon drawable resource identifier
		 * @param color the {@link Drawable} color
		 * @param notificationId the notification identifier
		 */
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
