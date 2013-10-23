package org.imogene.android.maps.preference;

import org.imogene.android.app.WakefulIntentService;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.database.PreCache;
import org.imogene.android.maps.database.sqlite.PreCacheCursor;
import org.imogene.android.maps.offline.TileLoaderService;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;

public class AutomaticCachingPreference extends Preference {
	
	public AutomaticCachingPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public AutomaticCachingPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onClick() {
		PreCacheCursor c = (PreCacheCursor) ImogOpenHelper.getHelper().query(PreCache.Columns.CONTENT_URI);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Intent intent = new Intent(getContext(), TileLoaderService.class);
			intent.putExtra(MapsConstants.EXTRA_REFRESH_PRECACHE, true);
			intent.putExtra(MapsConstants.EXTRA_TILE_SOURCE, c.getProvider());
			intent.putExtra(MapsConstants.EXTRA_LAT_NORTH, c.getNorth());
			intent.putExtra(MapsConstants.EXTRA_LAT_SOUTH, c.getSouth());
			intent.putExtra(MapsConstants.EXTRA_LON_EAST, c.getEast());
			intent.putExtra(MapsConstants.EXTRA_LON_WEST, c.getWest());
			intent.putExtra(MapsConstants.EXTRA_ZOOM_MIN, c.getZoomMin());
			intent.putExtra(MapsConstants.EXTRA_ZOOM_MAX, c.getZoomMax());
			
			WakefulIntentService.sendWakefulWork(getContext(), intent);
		}
	}

}
