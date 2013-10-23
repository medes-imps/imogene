package org.imogene.android.maps.app;

import java.util.ArrayList;

import org.imogene.android.Constants.Extras;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.database.sqlite.stmt.Where;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.maps.os.OverlayLoader;
import org.imogene.android.maps.os.OverlayLoader.OnOverlaysLoadedListener;
import org.imogene.android.maps.widget.BalloonView;
import org.imogene.android.template.R;
import org.imogene.android.util.content.ContentUrisUtils;
import org.osmdroid.views.MapView.LayoutParams;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MapActivityWithoutActionBar extends MapActivity {
	
	private static final int MENU_MAPMODE_ID = 1;
	private static final int MENU_OFFLINE_ID = 2;
	private static final int MENU_MYLOCATION_ID = 3;
	private static final int MENU_COMPASS_ID = 4;

	private String mWhereClause = null;
	private Object[] mWhereArgs = null;
	private Uri mContentUri;
	
	private BalloonView mBalloonView;
	
	private OverlayLoader mLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContentUri = getIntent().getData();
		
		if (getIntent().hasExtra(Extras.EXTRA_WHERE)) {
			mWhereClause = getIntent().getStringExtra(Extras.EXTRA_WHERE);
			mWhereArgs = getIntent().getStringArrayExtra(Extras.EXTRA_ARGS);
		}
		
		mLoader = (OverlayLoader) getLastNonConfigurationInstance();
		if (mLoader == null) {
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(mContentUri);
			Where where = builder.where();
			if (mWhereClause != null) {
				where.raw(mWhereClause, mWhereArgs).and();
			}
			where.ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
			mLoader = new OverlayLoader(getApplicationContext());
			mLoader.execute(builder);
		}
		
		mLoader.setOnOverlaysLoadedListener(mLoaderListener);
		
		// hide the action bar
		getGDActionBar().setVisibility(View.GONE);
		findViewById(R.id.gd_action_bar_colorstrip).setVisibility(View.GONE);
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		mLoader.setOnOverlaysLoadedListener(null);
		return mLoader;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_MYLOCATION_ID, Menu.NONE, R.string.maps_my_location)
		.setIcon(R.drawable.ig_ic_menu_mylocation);
		menu.add(Menu.NONE, MENU_COMPASS_ID, Menu.NONE, R.string.maps_compass)
		.setIcon(R.drawable.ig_ic_menu_compass);
		menu.add(Menu.NONE, MENU_MAPMODE_ID, Menu.NONE, R.string.maps_map_mode)
		.setIcon(R.drawable.ig_ic_menu_mapmode);
		menu.add(Menu.NONE, MENU_OFFLINE_ID, Menu.NONE, R.string.maps_offline_mode)
		.setIcon(R.drawable.ig_ic_menu_offline);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private final OnItemGestureListener<OverlayItem> mGestureListener = new OnItemGestureListener<OverlayItem>() {
		
		@Override
		public boolean onItemLongPress(int index, OverlayItem item) {
			return false;
		}
		
		@Override
		public boolean onItemSingleTapUp(int index, OverlayItem item) {
			boolean isRecycled = true;

			if (mBalloonView == null) {
				mBalloonView = new BalloonView(MapActivityWithoutActionBar.this);
				mBalloonView.setOnClickListener(mBalloonClickListener);
				isRecycled = false;
			}

			mBalloonView.setTag(ContentUrisUtils.withAppendedId(mContentUri, item.mUid));
			mBalloonView.setData(item.mTitle, item.mDescription);

			LayoutParams params = new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT,
					item.mGeoPoint,
					LayoutParams.BOTTOM_CENTER,
					0, -34);

			if (isRecycled) {
				mBalloonView.setLayoutParams(params);
			} else {
				mMapView.addView(mBalloonView, params);
			}

			autozoom(item.mGeoPoint);
			return true;
		}
	};
	
	private final OnClickListener mBalloonClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Uri uri = (Uri) v.getTag();
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
			mBalloonView.setVisibility(View.GONE);
		}
	};
	
	private final OnOverlaysLoadedListener mLoaderListener = new OnOverlaysLoadedListener() {
		
		@Override
		public void onOverlaysLoaded(ArrayList<OverlayItem> items) {
			ItemizedIconOverlay<OverlayItem> ov = new ItemizedIconOverlay<OverlayItem>(
					items,
					getResources().getDrawable(R.drawable.maps_bubble),
					mGestureListener,
					mResourceProxy);
			
			mMapView.getOverlayManager().add(ov);
			mMapView.postInvalidate();
		}
	};
	
}
