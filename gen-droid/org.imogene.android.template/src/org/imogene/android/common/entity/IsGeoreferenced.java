package org.imogene.android.common.entity;

import android.location.Location;

public interface IsGeoreferenced {

	public Location getGeoreference();

	public void setGeoreference(Location location);

}
