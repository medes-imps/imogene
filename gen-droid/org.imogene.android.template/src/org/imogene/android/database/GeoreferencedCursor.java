package org.imogene.android.database;

import org.imogene.android.common.entity.ImogBean;

import android.location.Location;

public interface GeoreferencedCursor<T extends ImogBean> extends ImogBeanCursor<T> {

	public Location getGeoreference();

}
