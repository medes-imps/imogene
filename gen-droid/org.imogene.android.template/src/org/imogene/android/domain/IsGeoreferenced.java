package org.imogene.android.domain;

import android.location.Location;

public interface IsGeoreferenced {
	
	public Location getGeoreference();
	
	public void setGeoreference(Location location);

}
