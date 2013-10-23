package org.imogene.android.maps.offline;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;

public class TileLoaderManager {
	
	public interface OnTileLoadedListener {
		public void onTileLoaded(MapTile tile);
	}
	
	private static final int MAX_FAILED = 100;
	
	private final OnlineTileSourceBase mTileSource;
	
	private final double mEast;
	private final double mNorth;
	private final double mSouth;
	private final double mWest;
	
	private final int mZoomMax;
	private final int mZoomMin;
	
	private final LinkedList<MapTile> mFailedTiles = new LinkedList<MapTile>();
	
	private final int mTilesToDownload;
	
	private OnTileLoadedListener mListener;
	
	private boolean mStopRequested = false;
	
	private int mTilesDownloaded = 0;
	
	public TileLoaderManager(
			final OnlineTileSourceBase source,
			final double east,
			final double north,
			final double south,
			final double west,
			final int zoomMin,
			final int zoomMax) {
		mTileSource = source;
		
		mNorth = north;
		mEast = east;
		mWest = west;
		mSouth = south;
		
		mZoomMax = Math.min(source.getMaximumZoomLevel(), zoomMax);
		mZoomMin = Math.max(source.getMinimumZoomLevel(), zoomMin);
		
		mTilesToDownload = TileUtils.countTiles(source, north, west, south, east, zoomMin, zoomMax);
	}
	
	public TileLoaderManager(
			final OnlineTileSourceBase source,
			final double east,
			final double north,
			final double south,
			final double west) {
		this(source, east, north, south, west, source.getMinimumZoomLevel(), source.getMaximumZoomLevel());
	}
	
	public int getTilesToDownloadCount() {
		return mTilesToDownload;
	}
	
	public int getTilesDownloadedCount() {
		return mTilesDownloaded;
	}
	
	public void setOnTileLoadedListener(OnTileLoadedListener listener) {
		mListener = listener;
	}
	
	public void stop() {
		mStopRequested = true;
	}
	
	public boolean hasBeenStopped() {
		return mStopRequested;
	}
	
	public void requestTiles() {
		for (int z = mZoomMin; z <= mZoomMax; z++) {
            final MapTile upperLeft = TileUtils.getTileNumber(mNorth, mWest, z);
            final MapTile lowerRight = TileUtils.getTileNumber(mSouth, mEast, z);
			
            for(int x = upperLeft.getX(); x <= lowerRight.getX(); x++){
                for(int y = upperLeft.getY(); y <= lowerRight.getY(); y++) {
                	if (mStopRequested) {
                		return;
                	}
                	testForFailedTiles();
                	
                	final MapTile tile = new MapTile(z, x, y);
                	if (TileUtils.contains(mTileSource, tile)) {
                		tileDownloadSucceed(tile);
                	} else {
                		tryDownloadTile(tile);
                	}
                }
            }
		}
	}
	
	private boolean downloadTile(final MapTile tile) {
		try {
			URL url = new URL(mTileSource.getTileURLString(tile));
			TileUtils.saveFile(mTileSource, tile, (InputStream) url.getContent());
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	private void tryDownloadTile(final MapTile tile) {
    	if (downloadTile(tile)) {
    		tileDownloadSucceed(tile);
    	} else {
    		tileDownloadFailed(tile);
    	}
	}
	
	private void testForFailedTiles() {
		if (mFailedTiles.size() < MAX_FAILED) {
			return;
		}

		final int count = mFailedTiles.size();
		for (int i = 0; i < count; i++) {
			MapTile tile = mFailedTiles.poll();
			tryDownloadTile(tile);
		}
	}
	
	private void tileDownloadSucceed(MapTile tile) {
		mTilesDownloaded++;
		if (mListener != null) {
			mListener.onTileLoaded(tile);
		}
	}
	
	private void tileDownloadFailed(MapTile tile) {
		mFailedTiles.offer(tile);
	}

}
