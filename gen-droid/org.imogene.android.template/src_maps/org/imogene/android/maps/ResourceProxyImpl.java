package org.imogene.android.maps;

import org.imogene.android.template.R;
import org.osmdroid.ResourceProxy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class ResourceProxyImpl implements ResourceProxy {
	
	private final Resources mResources;
	
	public ResourceProxyImpl(Context context) {
		mResources = context.getResources();
	}
	
	@Override
	public Bitmap getBitmap(bitmap resId) {
		switch (resId) {
		case center:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_center);
		case direction_arrow:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_direction_arrow);
		case ic_menu_compass:
			return BitmapFactory.decodeResource(mResources, R.drawable.ig_ic_menu_compass);
		case ic_menu_mapmode:
			return BitmapFactory.decodeResource(mResources, R.drawable.ig_ic_menu_mapmode);
		case ic_menu_mylocation:
			return BitmapFactory.decodeResource(mResources, R.drawable.ig_ic_menu_mylocation);
		case ic_menu_offline:
			return BitmapFactory.decodeResource(mResources, R.drawable.ig_ic_menu_offline);
		case marker_default:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_marker_default);
		case marker_default_focused_base:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_marker_default_focused_base);
		case navto_small:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_navto_small);
		case next:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_next);
		case person:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_person);
		case previous:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_previous);
		case unknown:
			return BitmapFactory.decodeResource(mResources, R.drawable.maps_center);
		default:
			return null;
		}
	}
	
	@Override
	public float getDisplayMetricsDensity() {
		return mResources.getDisplayMetrics().density;
	}
	
	@Override
	public Drawable getDrawable(bitmap resId) {
		switch (resId) {
		case center:
			return mResources.getDrawable(R.drawable.maps_center);
		case direction_arrow:
			return mResources.getDrawable(R.drawable.maps_direction_arrow);
		case ic_menu_compass:
			return mResources.getDrawable(R.drawable.ig_ic_menu_compass);
		case ic_menu_mapmode:
			return mResources.getDrawable(R.drawable.ig_ic_menu_mapmode);
		case ic_menu_mylocation:
			return mResources.getDrawable(R.drawable.ig_ic_menu_mylocation);
		case ic_menu_offline:
			return mResources.getDrawable(R.drawable.ig_ic_menu_offline);
		case marker_default:
			return mResources.getDrawable(R.drawable.maps_marker_default);
		case marker_default_focused_base:
			return mResources.getDrawable(R.drawable.maps_marker_default_focused_base);
		case navto_small:
			return mResources.getDrawable(R.drawable.maps_navto_small);
		case next:
			return mResources.getDrawable(R.drawable.maps_next);
		case person:
			return mResources.getDrawable(R.drawable.maps_person);
		case previous:
			return mResources.getDrawable(R.drawable.maps_previous);
		case unknown:
			return mResources.getDrawable(R.drawable.maps_center);
		default:
			return null;
		}
	}
	
	@Override
	public String getString(string resId) {
		switch (resId) {
		case base:
			return mResources.getString(R.string.maps_base);
		case base_nl:
			return mResources.getString(R.string.maps_base_nl);
		case bing:
			return mResources.getString(R.string.maps_bing);
		case cloudmade_small:
			return mResources.getString(R.string.maps_cloudmade_small);
		case cloudmade_standard:
			return mResources.getString(R.string.maps_cloudmade_standard);
		case compass:
			return mResources.getString(R.string.maps_compass);
		case cyclemap:
			return mResources.getString(R.string.maps_cyclemap);
		case fiets_nl:
			return mResources.getString(R.string.maps_fiets_nl);
		case format_distance_feet:
			return mResources.getString(R.string.maps_format_distance_feet);
		case format_distance_kilometers:
			return mResources.getString(R.string.maps_format_distance_kilometers);
		case format_distance_meters:
			return mResources.getString(R.string.maps_format_distance_meters);
		case format_distance_miles:
			return mResources.getString(R.string.maps_format_distance_miles);
		case format_distance_nautical_miles:
			return mResources.getString(R.string.maps_format_distance_nautical_miles);
		case hills:
			return mResources.getString(R.string.maps_hills);
		case map_mode:
			return mResources.getString(R.string.maps_map_mode);
		case mapnik:
			return mResources.getString(R.string.maps_mapnik);
		case mapquest_aerial:
			return mResources.getString(R.string.maps_mapquest_aerial);
		case mapquest_osm:
			return mResources.getString(R.string.maps_mapquest_osm);
		case my_location:
			return mResources.getString(R.string.maps_my_location);
		case offline_mode:
			return mResources.getString(R.string.maps_offline_mode);
		case online_mode:
			return mResources.getString(R.string.maps_online_mode);
		case osmarender:
			return mResources.getString(R.string.maps_osmarender);
		case public_transport:
			return mResources.getString(R.string.maps_public_transport);
		case roads_nl:
			return mResources.getString(R.string.maps_roads_nl);
		case topo:
			return mResources.getString(R.string.maps_topo);
		case unknown:
			return mResources.getString(R.string.maps_unknown);
		default:
			return null;
		}
	};
	
	@Override
	public String getString(string resId, Object... formatArgs) {
		switch (resId) {
		case base:
			return mResources.getString(R.string.maps_base, formatArgs);
		case base_nl:
			return mResources.getString(R.string.maps_base_nl, formatArgs);
		case bing:
			return mResources.getString(R.string.maps_bing, formatArgs);
		case cloudmade_small:
			return mResources.getString(R.string.maps_cloudmade_small, formatArgs);
		case cloudmade_standard:
			return mResources.getString(R.string.maps_cloudmade_standard, formatArgs);
		case compass:
			return mResources.getString(R.string.maps_compass, formatArgs);
		case cyclemap:
			return mResources.getString(R.string.maps_cyclemap, formatArgs);
		case fiets_nl:
			return mResources.getString(R.string.maps_fiets_nl, formatArgs);
		case format_distance_feet:
			return mResources.getString(R.string.maps_format_distance_feet, formatArgs);
		case format_distance_kilometers:
			return mResources.getString(R.string.maps_format_distance_kilometers, formatArgs);
		case format_distance_meters:
			return mResources.getString(R.string.maps_format_distance_meters, formatArgs);
		case format_distance_miles:
			return mResources.getString(R.string.maps_format_distance_miles, formatArgs);
		case format_distance_nautical_miles:
			return mResources.getString(R.string.maps_format_distance_nautical_miles, formatArgs);
		case hills:
			return mResources.getString(R.string.maps_hills, formatArgs);
		case map_mode:
			return mResources.getString(R.string.maps_map_mode, formatArgs);
		case mapnik:
			return mResources.getString(R.string.maps_mapnik, formatArgs);
		case mapquest_aerial:
			return mResources.getString(R.string.maps_mapquest_aerial, formatArgs);
		case mapquest_osm:
			return mResources.getString(R.string.maps_mapquest_osm, formatArgs);
		case my_location:
			return mResources.getString(R.string.maps_my_location, formatArgs);
		case offline_mode:
			return mResources.getString(R.string.maps_offline_mode, formatArgs);
		case online_mode:
			return mResources.getString(R.string.maps_online_mode, formatArgs);
		case osmarender:
			return mResources.getString(R.string.maps_osmarender, formatArgs);
		case public_transport:
			return mResources.getString(R.string.maps_public_transport, formatArgs);
		case roads_nl:
			return mResources.getString(R.string.maps_roads_nl, formatArgs);
		case topo:
			return mResources.getString(R.string.maps_topo, formatArgs);
		case unknown:
			return mResources.getString(R.string.maps_unknown, formatArgs);
		default:
			return null;
		}
	}

}
