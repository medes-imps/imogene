package org.imogene.android.app;

import java.util.ArrayList;

import org.imogene.android.Constants.Extras;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.template.R;
import org.imogene.android.util.OverlayLoader;
import org.imogene.android.util.OverlayLoader.OnOverlaysLoadedListener;
import org.osmdroid.views.MapView.LayoutParams;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.database.sqlite.stmt.Where;
import fr.medes.android.maps.app.MapActivity;
import fr.medes.android.maps.widget.BalloonView;
import fr.medes.android.util.content.ContentUrisUtils;

public class MapActivityWithoutActionBar extends MapActivity {

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
		getSupportActionBar().hide();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		mLoader.setOnOverlaysLoadedListener(null);
		return mLoader;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.maps__menu_viewer, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.maps__menu_map:
			showDialog(DIALOG_MAPMODE_ID);
			return true;
		case R.id.maps__menu_place:
			changeMyLocationState();
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

			mBalloonView.setTag(ContentUrisUtils.withAppendedId(mContentUri, item.getUid()));
			mBalloonView.setData(item.getTitle(), item.getSnippet());

			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, item.getPoint(),
					LayoutParams.BOTTOM_CENTER, 0, -34);

			if (isRecycled) {
				mBalloonView.setLayoutParams(params);
			} else {
				mMapView.addView(mBalloonView, params);
			}

			autozoom(item.getPoint());
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
			ItemizedIconOverlay<OverlayItem> ov = new ItemizedIconOverlay<OverlayItem>(items, getResources().getDrawable(
					R.drawable.maps__bubble), mGestureListener, mResourceProxy);

			mMapView.getOverlayManager().add(ov);
			mMapView.postInvalidate();
		}
	};

}
