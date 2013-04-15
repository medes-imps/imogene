package org.imogene.android.maps.app;

import greendroid.widget.ActionBarItem;
import greendroid.widget.NormalActionBarItem;

import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.database.PreCache;
import org.imogene.android.maps.database.sqlite.PreCacheCursor;
import org.imogene.android.maps.overlay.BoundingBoxOverlay;
import org.imogene.android.template.R;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;

import android.content.Intent;
import android.os.Bundle;

public class PreCacheMap extends MapActivity {

	private static final int ZOOM_FOR_PRECACHE = 11;

	private static final int ACTIONBAR_MAPMODE_ID = 100;

	private String mTileSource = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getBooleanExtra(MapsConstants.EXTRA_SHOW_PRECACHE, false)) {
			showPreCache(getIntent());
		}

		addActionBarItem(getGDActionBar().newActionBarItem(NormalActionBarItem.class)
				.setDrawable(R.drawable.ig_ic_title_mapmode), ACTIONBAR_MAPMODE_ID);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		if (intent.getBooleanExtra(MapsConstants.EXTRA_SHOW_PRECACHE, false)) {
			showPreCache(intent);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mTileSource != null) {
			mMapView.setTileSource(TileSourceFactory.getTileSource(mTileSource));
			mTileSource = null;
		}
	}

	private void showPreCache(final Intent intent) {
		PreCacheCursor c = (PreCacheCursor) ImogOpenHelper.getHelper().query(PreCache.Columns.CONTENT_URI);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			BoundingBoxOverlay bbo = new BoundingBoxOverlay(this);
			bbo.setBounds(c.getNorth(), c.getEast(), c.getSouth(), c.getWest());
			mMapView.getOverlayManager().add(bbo);
		}
		c.close();

		mTileSource = intent.getStringExtra(MapsConstants.EXTRA_TILE_SOURCE);

		final double north = intent.getDoubleExtra(MapsConstants.EXTRA_LAT_NORTH, MapsConstants.DEFAULT_NORTH);
		final double east = intent.getDoubleExtra(MapsConstants.EXTRA_LON_EAST, MapsConstants.DEFAULT_EAST);
		final double south = intent.getDoubleExtra(MapsConstants.EXTRA_LAT_SOUTH, MapsConstants.DEFAULT_SOUTH);
		final double west = intent.getDoubleExtra(MapsConstants.EXTRA_LON_WEST, MapsConstants.DEFAULT_WEST);

		final GeoPoint center = new GeoPoint((north + south) / 2,	(east + west) / 2);

		mMapView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mMapView.getController().setZoom(ZOOM_FOR_PRECACHE);
				mMapView.getController().animateTo(center);
			}
		}, 500);
	}

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case ACTIONBAR_MAPMODE_ID:
			showDialog(DIALOG_MAPMODE_ID);
			return true;
		default:
			return super.onHandleActionBarItemClick(item, position);
		}
	}

}