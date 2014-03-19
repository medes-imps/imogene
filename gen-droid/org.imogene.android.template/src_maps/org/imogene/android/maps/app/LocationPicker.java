package org.imogene.android.maps.app;

import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.overlay.BubbleClickableOverlay;
import org.imogene.android.template.R;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class LocationPicker extends MapActivity {

	private static final int MENU_AUTOZOOM_ID = 100;
	private static final int MENU_SAVE_ID = 101;
	private static final int MENU_MYLOCATION_ID = 102;
	private static final int MENU_COMPASS_ID = 103;
	private static final int MENU_MAPMODE_ID = 104;
	private static final int MENU_OFFLINE_ID = 105;

	private BubbleClickableOverlay mOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (l == null) {
			l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		final GeoPoint point;
		if (l != null) {
			point = new GeoPoint(l.getLatitude(), l.getLongitude());
		} else {
			final Intent intent = getIntent();
			double lat = intent.getDoubleExtra(MapsConstants.EXTRA_LATITUDE, MapsConstants.DEFAULT_LATIUDE);
			double lon = intent.getDoubleExtra(MapsConstants.EXTRA_LONGITUDE, MapsConstants.DEFAULT_LONGITUDE);
			point = new GeoPoint(lat, lon);
		}

		mOverlay = new BubbleClickableOverlay(this, point);
		getMapView().getOverlays().add(mOverlay);

	}

	@Override
	public boolean isPrecachingEnabled() {
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		IGeoPoint point = mOverlay.getGeoPoint();
		outState.putInt(MapsConstants.EXTRA_LATITUDE, point.getLatitudeE6());
		outState.putInt(MapsConstants.EXTRA_LONGITUDE, point.getLongitudeE6());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int latitude = savedInstanceState.getInt(MapsConstants.EXTRA_LATITUDE);
		int longitude = savedInstanceState.getInt(MapsConstants.EXTRA_LONGITUDE);
		GeoPoint point = new GeoPoint(latitude, longitude);
		mOverlay.setGeoPoint(point);

		autozoom(point);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_SAVE_ID, Menu.NONE, R.string.ig_menu_save).setIcon(R.drawable.ig_ic_title_save)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, MENU_AUTOZOOM_ID, Menu.NONE, R.string.ig_menu_autozoom)
				.setIcon(R.drawable.ig_ic_title_autozoom).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, MENU_MYLOCATION_ID, Menu.NONE, R.string.maps_my_location)
				.setIcon(R.drawable.ig_ic_title_mylocation).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, MENU_COMPASS_ID, Menu.NONE, R.string.maps_compass).setIcon(R.drawable.ig_ic_title_compass)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, MENU_MAPMODE_ID, Menu.NONE, R.string.maps_map_mode).setIcon(R.drawable.ig_ic_title_mapmode)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(Menu.NONE, MENU_OFFLINE_ID, Menu.NONE, R.string.maps_offline_mode)
				.setIcon(R.drawable.ig_ic_title_offline).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_AUTOZOOM_ID:
			autozoom(mOverlay.getGeoPoint());
			return true;
		case MENU_COMPASS_ID:
			changeCompassState();
			return true;
		case MENU_MAPMODE_ID:
			showDialog(DIALOG_MAPMODE_ID);
			return true;
		case MENU_MYLOCATION_ID:
			changeMyLocationState();
			return true;
		case MENU_OFFLINE_ID:
			changeConnectionState();
			return true;
		case MENU_SAVE_ID:
			IGeoPoint point = mOverlay.getGeoPoint();
			Location location = new Location("medesmapprovider");
			location.setLatitude(point.getLatitudeE6() / (double) 1E6);
			location.setLongitude(point.getLongitudeE6() / (double) 1E6);
			setResult(RESULT_OK, new Intent().putExtra(MapsConstants.EXTRA_LOCATION, location));
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
