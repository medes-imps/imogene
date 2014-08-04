package org.imogene.android.common.entity;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import fr.medes.android.util.annotation.ReflectionUtils;

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
	 * Return the class of the home Activity. The home Activity is the main entrance point of your application. This is
	 * usually where the dashboard/general menu is displayed.
	 * 
	 * @return The Class of the home Activity
	 */
	public abstract Class<?> getHomeActivityClass();

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
			Drawable color = (Drawable) ReflectionUtils.getFieldValue(columnsClass, "COLOR", null);
			String table = (String) ReflectionUtils.getFieldValue(columnsClass, "TABLE_NAME", null);
			String type = (String) ReflectionUtils.getFieldValue(columnsClass, "BEAN_TYPE", null);
			Uri uri = (Uri) ReflectionUtils.getFieldValue(columnsClass, "CONTENT_URI", null);
			Integer desc_sg = (Integer) ReflectionUtils.getFieldValue(columnsClass, "DESC_SINGULAR", null);
			Integer desc_pl = (Integer) ReflectionUtils.getFieldValue(columnsClass, "DESC_PLURAL", null);
			Integer drawable = (Integer) ReflectionUtils.getFieldValue(columnsClass, "DRAWABLE", null);
			Integer notifId = (Integer) ReflectionUtils.getFieldValue(columnsClass, "NOTIFICATION_ID", null);
			return new EntityInfo(uri, table, type, desc_sg != null ? desc_sg : -1, desc_pl != null ? desc_pl : -1,
					drawable != null ? drawable : -1, color, notifId != null ? notifId : -1);
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
