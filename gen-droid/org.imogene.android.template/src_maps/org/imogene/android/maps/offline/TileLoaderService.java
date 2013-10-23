package org.imogene.android.maps.offline;

import org.imogene.android.app.WakefulIntentService;
import org.imogene.android.maps.MapsConstants;
import org.imogene.android.maps.ResourceProxyImpl;
import org.imogene.android.maps.app.PreCacheMap;
import org.imogene.android.maps.database.PreCache;
import org.imogene.android.maps.offline.TileLoaderManager.OnTileLoadedListener;
import org.imogene.android.template.R;
import org.imogene.android.util.os.Locker;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.RemoteViews;

public class TileLoaderService extends WakefulIntentService implements OnTileLoadedListener {
	
	public interface TileLoaderServiceListener {
		public void updateLoadedTilesCount(int loaded);
		
		public void finished(Intent intent);
	}
	
	private static final int NOTIFICATION_DOWNLOAD_ID = 654875;
	private static final int NOTIFICATION_FINISHED_ID = 654876;
	
	private final TileLoaderBinder mBinder = new TileLoaderBinder();
	private final Locker mLocker = new Locker();

	private NotificationManager mManager;
	private Notification mNotification;
	
	private TileLoaderManager mLoaderManager;
	private int mTilesToDownload;
	
	private TileLoaderServiceListener mListener;
	
	public TileLoaderService() {
		super(TileLoaderService.class.getName());
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	protected void doWakefulWork(Intent intent) {
		final boolean refresh = intent.getBooleanExtra(MapsConstants.EXTRA_REFRESH_PRECACHE, false);
		final double north = intent.getDoubleExtra(MapsConstants.EXTRA_LAT_NORTH, MapsConstants.DEFAULT_NORTH);
		final double south = intent.getDoubleExtra(MapsConstants.EXTRA_LAT_SOUTH, MapsConstants.DEFAULT_SOUTH);
		final double east = intent.getDoubleExtra(MapsConstants.EXTRA_LON_EAST, MapsConstants.DEFAULT_EAST);
		final double west = intent.getDoubleExtra(MapsConstants.EXTRA_LON_WEST, MapsConstants.DEFAULT_WEST);
		final int zoomMin = intent.getIntExtra(MapsConstants.EXTRA_ZOOM_MIN, 0);
		final int zoomMax = intent.getIntExtra(MapsConstants.EXTRA_ZOOM_MAX, 18);
		
		OnlineTileSourceBase source = TileSourceFactory.MAPNIK;
		if (intent.hasExtra(MapsConstants.EXTRA_TILE_SOURCE)) {
			final String name = intent.getStringExtra(MapsConstants.EXTRA_TILE_SOURCE);
			ITileSource s = TileSourceFactory.getTileSource(name);
			if (s instanceof OnlineTileSourceBase) {
				source = (OnlineTileSourceBase) s;
			}
		}
		
		mLoaderManager = new TileLoaderManager(source, east, north, south, west, zoomMin, zoomMax);
		mTilesToDownload = mLoaderManager.getTilesToDownloadCount();

		showDownloadNotification(source.localizedName(new ResourceProxyImpl(this)));
		publishProgress(0);

		mLoaderManager.setOnTileLoadedListener(this);
		mLoaderManager.requestTiles();
		mLocker.cancel();
		
		if (!refresh && !mLoaderManager.hasBeenStopped()) {
			PreCache precache = new PreCache();
			precache.provider = source.name();
			precache.north = north;
			precache.east = east;
			precache.south = south;
			precache.west = west;
			precache.zoomMin = zoomMin;
			precache.zoomMax = zoomMax;
			precache.saveOrUpdate(this);
			
			Intent clickIntent = new Intent(this, PreCacheMap.class);
			clickIntent.putExtra(MapsConstants.EXTRA_SHOW_PRECACHE, true);
			clickIntent.putExtra(MapsConstants.EXTRA_LAT_NORTH, north);
			clickIntent.putExtra(MapsConstants.EXTRA_LON_EAST, east);
			clickIntent.putExtra(MapsConstants.EXTRA_LAT_SOUTH, south);
			clickIntent.putExtra(MapsConstants.EXTRA_LON_WEST, west);
			clickIntent.putExtra(MapsConstants.EXTRA_TILE_SOURCE, source.name());
			
			String title = getString(R.string.maps_offline_text1, source.name(), zoomMin, zoomMax);
			String text = getString(R.string.maps_offline_text2, north, east, south, west);
			showDownloadFinishedNotification(title, text, clickIntent);
			
			if (mListener != null) {
				mListener.finished(clickIntent);
			}
		}
		
		publishProgress(mLoaderManager.getTilesDownloadedCount());
		mManager.cancel(NOTIFICATION_DOWNLOAD_ID);
	}
	
	public void stop() {
		if (mLoaderManager != null) {
			mLoaderManager.stop();
		}
	}
	
	public int getTilesToDownloadCount() {
		if (mLoaderManager != null) {
			return mLoaderManager.getTilesToDownloadCount();
		}
		return 100;
	}
	
	public int getTilesDownloadedCount() {
		if (mLoaderManager != null) {
			return mLoaderManager.getTilesDownloadedCount();
		}
		return 0;
	}
	
	public void setTileLoaderServiceListener(TileLoaderServiceListener listener) {
		mListener = listener;
	}
	
	@Override
	public void onTileLoaded(MapTile tile) {
		if (!mLocker.isLocked()) {
			if (mListener != null) {
				mListener.updateLoadedTilesCount(mLoaderManager.getTilesDownloadedCount());
			}
			publishProgress(mLoaderManager.getTilesDownloadedCount());
			mLocker.lock();
		}
	}
	
	private void publishProgress(int progress) {
		if (mNotification != null) {
			mNotification.contentView.setProgressBar(R.id.maps_offline_progress, mTilesToDownload, progress, false);
			mNotification.contentView.setTextViewText(R.id.maps_offline_percent, getString(R.string.maps_offline_percent, progress * 100 / mTilesToDownload));
			mNotification.contentView.setTextViewText(R.id.maps_offline_size, getString(R.string.maps_offline_size, progress, mTilesToDownload));
			mManager.notify(NOTIFICATION_DOWNLOAD_ID, mNotification);
		}
	}
	
	private void showDownloadNotification(String label) {
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.maps_offline_download_notification);
		contentView.setTextViewText(R.id.maps_offline_title, label);
		contentView.setTextViewText(R.id.maps_offline_percent, getString(R.string.maps_offline_percent, 0));
		contentView.setTextViewText(R.id.maps_offline_size, getString(R.string.maps_offline_size, 0, mTilesToDownload));
		
		Intent contentIntent = new Intent(this, TileLoaderActivity.class);
		mNotification = new Notification(
				R.drawable.maps_map,
				getString(R.string.maps_offline_title),
				System.currentTimeMillis());
		mNotification.flags |= Notification.FLAG_NO_CLEAR;
		mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
		mNotification.contentView = contentView;
		mNotification.contentIntent = PendingIntent.getActivity(this, 0, contentIntent, 0);

		mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mManager.notify(NOTIFICATION_DOWNLOAD_ID, mNotification);
	}
	
	private void showDownloadFinishedNotification(String title, String text, Intent clickIntent) {
		Notification notification = new Notification(
				R.drawable.maps_map,
				getString(R.string.maps_offline_title),
				System.currentTimeMillis());
		
		// Make a startActivity() PendingIntent for the notification.
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Update the notification.
		notification.setLatestEventInfo(this, title, text, pendingIntent);

		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_LIGHTS;

		mManager.notify(NOTIFICATION_FINISHED_ID, notification);
	}
	
	public class TileLoaderBinder extends Binder {
		
		public TileLoaderService getService() {
			return TileLoaderService.this;
		}
		
	}
	
}
