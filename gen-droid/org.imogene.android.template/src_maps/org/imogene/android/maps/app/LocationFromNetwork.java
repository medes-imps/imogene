package org.imogene.android.maps.app;

import android.location.LocationManager;

public class LocationFromNetwork extends LocationFromSensor {
	
	@Override
	protected String obtainProvider() {
		return LocationManager.NETWORK_PROVIDER;
	}

}
