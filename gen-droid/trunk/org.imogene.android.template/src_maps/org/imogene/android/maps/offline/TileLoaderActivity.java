package org.imogene.android.maps.offline;

import org.imogene.android.maps.offline.TileLoaderService.TileLoaderServiceListener;
import org.imogene.android.template.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TileLoaderActivity extends Activity implements OnClickListener {
	
	private View mCancelButton;
	private View mCloseButton;
	
	private ProgressBar mProgressBar;
	private TextView mPercentView;
	private TextView mSizeView;
	
	private TileLoaderService mBoundService;
	private boolean mIsBound = false;
	
	private int mTilesToLoad = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_tile_loader_content);
		
		mCancelButton = findViewById(R.id.maps_offline_cancel);
		mCloseButton = findViewById(R.id.maps_offline_close);
		
		mProgressBar = (ProgressBar) findViewById(R.id.maps_offline_progress);
		mPercentView = (TextView) findViewById(R.id.maps_offline_percent);
		mSizeView = (TextView) findViewById(R.id.maps_offline_size);
		
		mCancelButton.setOnClickListener(this);
		mCloseButton.setOnClickListener(this);
		
		doBindService();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}
	
	private void doBindService() {
		if (!mIsBound) {
			mIsBound = bindService(new Intent(this, TileLoaderService.class), mConnection, 0);
		}
	}

	private void doUnbindService() {
		if (mIsBound) {
			unbindService(mConnection);
			mIsBound = false;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.maps_offline_cancel:
			if (mBoundService != null) {
				mBoundService.stop();
			}
			break;
		case R.id.maps_offline_close:
			finish();
			break;	
		}
	}
	
	private final ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			mBoundService = ((TileLoaderService.TileLoaderBinder) service).getService();
			if (mBoundService != null) {
				mTilesToLoad = mBoundService.getTilesToDownloadCount();
				int loaded = mBoundService.getTilesDownloadedCount();
				
				mProgressBar.setMax(mTilesToLoad);
				mProgressBar.setProgress(loaded);
				
				mPercentView.setText(getString(R.string.maps_offline_percent, loaded * 100 / mTilesToLoad));
				mSizeView.setText(getString(R.string.maps_offline_size, loaded, mTilesToLoad));
				
				mBoundService.setTileLoaderServiceListener(mListener);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			mBoundService.setTileLoaderServiceListener(null);
			mBoundService = null;
			finish();
		}
	};
	
	private final TileLoaderServiceListener mListener = new TileLoaderServiceListener() {
		
		@Override
		public void updateLoadedTilesCount(final int loaded) {
			mProgressBar.setProgress(loaded);
			mPercentView.post(new Runnable() {
				@Override
				public void run() {
					mPercentView.setText(getString(R.string.maps_offline_percent, loaded * 100 / mTilesToLoad));
				}
			});
			mSizeView.post(new Runnable() {
				@Override
				public void run() {
					mSizeView.setText(getString(R.string.maps_offline_size, loaded, mTilesToLoad));
				}
			});
		}
		
		@Override
		public void finished(Intent intent) {
			startActivity(intent);
		};
	};
	
}

