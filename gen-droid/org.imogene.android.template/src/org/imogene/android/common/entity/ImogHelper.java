package org.imogene.android.common.entity;

import java.util.List;

import org.imogene.android.common.model.EntityInfo;

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
	 * Return the class of the home Activity. The home Activity is the main entrance point of your application. This is
	 * usually where the dashboard/general menu is displayed.
	 * 
	 * @return The Class of the home Activity
	 */
	public abstract Class<?> getHomeActivityClass();

	public abstract List<EntityInfo> getEntityInfos();

	public final Uri getUriFromClass(Class<? extends ImogBean> clazz) {
		for (EntityInfo info : getEntityInfos()) {
			if (info.clazz.equals(clazz)) {
				return info.contentUri;
			}
		}
		return null;
	}

}
