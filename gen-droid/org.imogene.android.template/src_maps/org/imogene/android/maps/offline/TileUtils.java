package org.imogene.android.maps.offline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.util.StreamUtils;

public class TileUtils {
	
	public static int countTiles(final ITileSource source, final double north, final double west, final double south, final double east, final int zMin, final int zMax) {
		int zoomMin = Math.max(source.getMinimumZoomLevel(), zMin);
		int zoomMax = Math.min(source.getMaximumZoomLevel(), zMax);

		int result = 0;
		for (int z = zoomMin; z <= zoomMax; z++) {
            final MapTile upperLeft = getTileNumber(north, west, z);
            final MapTile lowerRight = getTileNumber(south, east, z);
            result += (lowerRight.getX() - upperLeft.getX() + 1) * (lowerRight.getY() - upperLeft.getY() + 1);
		}
		return result;
	}
	
	public static int countTiles(final ITileSource source, final double north, final double west, final double south, final double east) {
		return countTiles(source, north, west, south, east, source.getMinimumZoomLevel(), source.getMaximumZoomLevel());
	}
	
	/**
	 * For a description see:
	 * @see http://wiki.openstreetmap.org/index.php/Slippy_map_tilenames
	 * For a code-description see:
	 * @see http://wiki.openstreetmap.org/index.php/Slippy_map_tilenames#compute_bounding_box_for_tile_number
	 * @param lat latitude to get the {@link MapTile} for.
	 * @param lon longitude to get the {@link MaptTile} for.
	 * @param zoom the zoom level to get the {@link MaptTile} for.
	 * @return The {@link MapTile} providing 'x' 'y' and 'z'(oom) for the coordinates passed.
	 */
	public static MapTile getTileNumber(final double lat, final double lon, final int zoom) {
		int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		int ytile = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom));
		return new MapTile(zoom, xtile, ytile);
	}
	
	public static boolean contains(final ITileSource source, final MapTile mapTile) {
		final File file = new File(
				OpenStreetMapTileProviderConstants.TILE_PATH_BASE,
				source.getTileRelativeFilenameString(mapTile) + 
				OpenStreetMapTileProviderConstants.TILE_PATH_EXTENSION);
		return file.exists();
	}
	
	public static boolean saveFile(final ITileSource source, final MapTile tile, final InputStream is) {
		final File file = new File(
				OpenStreetMapTileProviderConstants.TILE_PATH_BASE,
				source.getTileRelativeFilenameString(tile) +
				OpenStreetMapTileProviderConstants.TILE_PATH_EXTENSION);

		final File parent = file.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			return false;
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			StreamUtils.copy(is, fos);
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			StreamUtils.closeStream(fos);
			StreamUtils.closeStream(is);
		}
		return true;
	}

}
