package org.imogene.android.maps.app;

import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.overlay.BubbleOverlay;
import org.imogene.android.template.R;
import org.osmdroid.util.GeoPoint;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class LocationViewer extends MapActivity {

	private static final int MENU_AUTOZOOM_ID = 100;
	private static final int MENU_MAPMODE_ID = 101;
	private static final int MENU_OFFLINE_ID = 102;

	private BubbleOverlay mOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = getIntent();
		double lat = intent.getDoubleExtra(MapsConstants.EXTRA_LATITUDE, MapsConstants.DEFAULT_LATIUDE);
		double lon = intent.getDoubleExtra(MapsConstants.EXTRA_LONGITUDE, MapsConstants.DEFAULT_LONGITUDE);

		mOverlay = new BubbleOverlay(this, new GeoPoint(lat, lon));

		getMapView().getOverlays().add(mOverlay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_AUTOZOOM_ID, Menu.NONE, R.string.ig_menu_autozoom)
				.setIcon(R.drawable.ig_ic_title_autozoom).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
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
		case MENU_MAPMODE_ID:
			showDialog(DIALOG_MAPMODE_ID);
			return true;
		case MENU_OFFLINE_ID:
			changeConnectionState();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
