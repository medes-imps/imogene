package org.imogene.android.common.model;

import org.imogene.android.common.entity.ImogBean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Resume the static informations about an {@link ImogBean} class that is representable to the user.
 */
public class EntityInfo {

	/**
	 * Entity access policy
	 */
	public static interface Policy {
		/**
		 * Whether the user can direct access to the entity.
		 * 
		 * @param context The application context
		 * @return {@code true} if the user can direct access the entity, {@code false} otherwise.
		 */
		public boolean canDirectAccess(Context context);
	}

	/**
	 * The {@link ImogBean} class
	 */
	public final Class<? extends ImogBean> clazz;

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
	 * The description resource identifier (plural)
	 */
	public final int description;

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
	 * The entity access policy
	 */
	private final Policy policy;

	/**
	 * Default constructor.
	 * 
	 * @param clazz the {@link ImogBean} class
	 * @param contentUri the content {@link Uri}
	 * @param table the table name
	 * @param type the {@link ImogBean} type
	 * @param description the description resource identifier (plural)
	 * @param drawable the icon drawable resource identifier
	 * @param color the {@link Drawable} color
	 * @param notificationId the notification identifier
	 * @param policy the access policy for this entity
	 */
	public EntityInfo(Class<? extends ImogBean> clazz, Uri contentUri, String table, String type, int description,
			int drawable, Drawable color, int notificationId, Policy policy) {
		this.clazz = clazz;
		this.contentUri = contentUri;
		this.table = table;
		this.type = type;
		this.description = description;
		this.drawable = drawable;
		this.color = color;
		this.notificationId = notificationId;
		this.policy = policy;
	}

	/**
	 * Default constructor.
	 * 
	 * @param clazz the {@link ImogBean} class
	 * @param contentUri the content {@link Uri}
	 * @param table the table name
	 * @param type the {@link ImogBean} type
	 */
	public EntityInfo(Class<? extends ImogBean> clazz, Uri contentUri, String table, String type) {
		this.clazz = clazz;
		this.contentUri = contentUri;
		this.table = table;
		this.type = type;
		this.description = -1;
		this.drawable = -1;
		this.color = null;
		this.notificationId = -1;
		this.policy = null;
	}

	/**
	 * Whether the user can direct access to this entity.
	 * 
	 * @param context The application context
	 * @return {@code true} if the user can direct access this entity, {@code false} otherwise.
	 */
	public boolean canDirectAccess(Context context) {
		if (policy == null) {
			return false;
		}
		return policy.canDirectAccess(context);
	}
}